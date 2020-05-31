package com.cbd.cbdcompany.service;

import com.cbd.cbdcommoninterface.cbd_interface.company.CompanyService;
import com.cbd.cbdcommoninterface.pojo.company.CompanyInfo;
import com.cbd.cbdcommoninterface.response.CpyLevelAndLocResponse;
import com.cbd.cbdcommoninterface.response.LocListResponse;
import com.cbd.cbdcompany.dao.CompanyDao;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Slf4j
public class CompanyServiceImpl implements CompanyService {
    @Autowired
    CompanyDao companyDao;

    @Override
    public CpyLevelAndLocResponse findCompanyLevelAndLoc(String companyID) {
        CompanyInfo cpyInfo = findCompanyInfoByCompanyID(companyID);
        Integer lft = cpyInfo.getLft();
        Integer rgt = cpyInfo.getRgt();
        //获取所有子公司ID
        List<String> cpyIDList = companyDao.findAllCompanyIDByCompanyID(lft, rgt);

        CpyLevelAndLocResponse cpyLevelAndLocResponse = new CpyLevelAndLocResponse();
        cpyLevelAndLocResponse.setCompanyLevel(cpyInfo.getCompanylevel());

        List<LocListResponse> locListResponseList = new ArrayList<>();

        //包装地区
        for (String cpyID : cpyIDList){
            CompanyInfo temp = findCompanyInfoByCompanyID(cpyID);
            //获取省
            String province = temp.getCompanyProvince();
            LocListResponse provinceList = findLoclist(locListResponseList, province);
            //0是因为所有省的pid都为中国，即0
            //查询省代码
//            String provinceValue = companyDao.getLocValue(province, "0");
            if (provinceList == null){
                provinceList = new LocListResponse();
                provinceList.setValue(province);
                provinceList.setLabel(province);
                provinceList.setChildren(new ArrayList<>());
//                添加省列表
                locListResponseList.add(provinceList);
            }
            //获取市
            String city = temp.getCompanyCity();
            LocListResponse cityList = findLoclist(provinceList.getChildren(), city);
//            String cityValue = companyDao.getLocValue(city, provinceValue);
            if (cityList == null){
                cityList = new LocListResponse();
                cityList.setValue(city);
                cityList.setLabel(city);
                cityList.setChildren(new ArrayList<>());
//                给省级的子列表新增市
                provinceList.getChildren().add(cityList);
            }

//            获取区县
            String district = temp.getCompanyDistrict();
            LocListResponse districtList = findLoclist(cityList.getChildren(), district);
            //这边注意下有的市区的pid为市辖区，所以进行一次判断，即获取到value为空即为市辖区
//            String districtValue = companyDao.getLocValue(district, cityValue);
//            if (districtValue == null){
//                districtValue = companyDao.getLocValue(district, companyDao.getLocValue("市辖区",cityValue));
//            }
            if (districtList == null){
                districtList = new LocListResponse();
                districtList.setValue(district);
                districtList.setLabel(district);
//                给市级的子列表新增区县
                cityList.getChildren().add(districtList);
            }
        }

        cpyLevelAndLocResponse.setLocListResponseList(locListResponseList);

        return cpyLevelAndLocResponse;
    }


    private LocListResponse findLoclist(List<LocListResponse> list, String name){
        for (LocListResponse locListResponse : list){
            if (locListResponse.getLabel().equals(name)){
                return locListResponse;
            }
        }

        return null;
    }

    @Override
    public List<CompanyInfo> getAllCompanyListByCompanyName(String companyName) {
        CompanyInfo cpyInfo = findCompanyInfoByCompanyName(companyName);
        Integer lft = cpyInfo.getLft();
        Integer rgt = cpyInfo.getRgt();
        //获取所有子公司ID
        List<String> cpyIDList = companyDao.findAllCompanyIDByCompanyID(lft, rgt);
        List<CompanyInfo> companyInfoList = new ArrayList<>();
        for (String id : cpyIDList){
            companyInfoList.add(findCompanyInfoByCompanyID(id));
        }

        return companyInfoList;
    }

    @Override
    public List<String> getUpCompanyIDByCompanyID(String companyID) {
        CompanyInfo cpyInfo = findCompanyInfoByCompanyID(companyID);
        Integer lft = cpyInfo.getLft();
        Integer rgt = cpyInfo.getRgt();
        List<String> parentIDList = companyDao.getUpCompanyIDByCompanyID(lft, rgt);
        return parentIDList;
    }

    @Override
    public CompanyInfo findCompanyInfoByCompanyID(String companyID) {
        return companyDao.findCompanyInfoByCompanyID(companyID);
    }

    @Override
    public CompanyInfo findCompanyInfoByCompanyName(String companyName) {
        return companyDao.findCompanyInfoByCompanyName(companyName);
    }
}

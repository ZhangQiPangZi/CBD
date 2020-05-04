package com.cbd.cbdcompany.service;

import com.cbd.cbdcommoninterface.cbd_interface.company.CompanyService;
import com.cbd.cbdcommoninterface.pojo.company.CompanyInfo;
import com.cbd.cbdcommoninterface.response.CpyLevelAndLocResponse;
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

        Map<String, Map<String, List<String>>> loc = new HashMap<>();

        //包装地区
        for (String cpyID : cpyIDList){
            CompanyInfo temp = findCompanyInfoByCompanyID(cpyID);
            //先放省级
            String companyProvince = temp.getCompanyProvince();
            if (!loc.containsKey(companyProvince)){
                loc.put(companyProvince, new HashMap<String, List<String>>());
            }
            //再放市级
            String companyCity = temp.getCompanyCity();
            Map<String, List<String>> cityMap = loc.get(companyProvince);
            if(!cityMap.containsKey(companyCity)){
                cityMap.put(companyCity, new ArrayList<String>());
            }
            //最后是区县级
            String district = temp.getCompanyDistrict();
            List<String> disList = cityMap.get(companyCity);
            if (!disList.contains(district)){
                disList.add(district);
            }
        }

        cpyLevelAndLocResponse.setLoc(loc);

        return cpyLevelAndLocResponse;
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

package com.cbd.cbdcompany.service;

import com.cbd.cbdcommoninterface.cbd_interface.company.CompanyService;
import com.cbd.cbdcommoninterface.dto.AddCpyDto;
import com.cbd.cbdcommoninterface.pojo.company.CompanyInfo;
import com.cbd.cbdcommoninterface.request.AddCpyRequest;
import com.cbd.cbdcommoninterface.response.CompanyListResponse;
import com.cbd.cbdcommoninterface.response.CpyLevelAndLocResponse;
import com.cbd.cbdcommoninterface.response.LocListResponse;
import com.cbd.cbdcommoninterface.result.CodeMsg;
import com.cbd.cbdcommoninterface.result.GlobalException;
import com.cbd.cbdcommoninterface.utils.UUIDUtils;
import com.cbd.cbdcompany.dao.CompanyDao;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

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
    public CompanyListResponse getGradeCompanyList(String companyID) {
        //先查出所有未分级的子公司列表，按左值排序
        CompanyInfo cpyInfo = findCompanyInfoByCompanyID(companyID);
        Integer lft = cpyInfo.getLft();
        Integer rgt = cpyInfo.getRgt();
        List<CompanyInfo> allCompanyList = companyDao.getGradeCompanyList(lft, rgt);

        /**
         * 再入栈，做分级处理，之后放入response中
         */
        //按左值排序，所以list第一个肯定是根公司
        CompanyListResponse companyListResponse = new CompanyListResponse();
        companyListResponse.setId(allCompanyList.get(0).getCompanyID());
        companyListResponse.setLabel(allCompanyList.get(0).getCompanyName());
        companyListResponse.setChildren(new ArrayList<>());
        //入栈
        LinkedList<CompanyInfo> cpyStack = new LinkedList<>();
        cpyStack.push(allCompanyList.get(0));
        //辅助map，作用是快速取出父级对应的对象
        Map<String, CompanyListResponse> helpMap = new HashMap<>();
        helpMap.put(companyListResponse.getLabel(), companyListResponse);

        log.info("cpy name:{}",allCompanyList.get(0).getCompanyName());
        allCompanyList.remove(0);

        for (CompanyInfo companyInfo : allCompanyList){
            CompanyListResponse curCpyListResponse = new CompanyListResponse();
            curCpyListResponse.setId(companyInfo.getCompanyID());
            curCpyListResponse.setLabel(companyInfo.getCompanyName());
            curCpyListResponse.setChildren(new ArrayList<>());

            //找到父公司
            while (!(cpyStack.peek().getLft() < companyInfo.getLft() && cpyStack.peek().getRgt() > companyInfo.getRgt())){
                cpyStack.pop();
            }
            CompanyInfo parentCpy = cpyStack.peek();
            CompanyListResponse parentCpyListResponse = helpMap.get(parentCpy.getCompanyName());
            //给父公司的子节点赋值
            parentCpyListResponse.getChildren().add(curCpyListResponse);
            //自身入栈
            cpyStack.push(companyInfo);
            helpMap.put(curCpyListResponse.getLabel(), curCpyListResponse);
        }

        return companyListResponse;
    }

    @Override
    public List<String> getHeadCpyList(String companyID) {
        List<String> headCpyList = companyDao.getHeadCpyList(companyID);
        return headCpyList;
    }

    @Override
    public List<String> getCompanyTypeList() {
        List<String> typeList = companyDao.getCompanyTypeList();
        return typeList;
    }

    @Override
    @Transactional(rollbackFor=Exception.class)
    public Boolean addCompanyByCpyName(AddCpyRequest addCpyRequest) {
        AddCpyDto addCpyDto = new AddCpyDto();
        addCpyDto.setCompanyID(UUIDUtils.getUUID());
        addCpyDto.setCompanyName(addCpyRequest.getCurCompanyName());
        addCpyDto.setCompanyTypeID(addCpyRequest.getCompanyTypeID());
        addCpyDto.setCompanyCode(addCpyRequest.getCompanyCode());
        addCpyDto.setCompanyMail(addCpyRequest.getCompanyMail());
        addCpyDto.setCompanyPhone(addCpyRequest.getCompanyPhone());
        addCpyDto.setCompanyProvince(addCpyRequest.getCompanyProvince());
        addCpyDto.setCompanyCity(addCpyRequest.getCompanyCity());
        addCpyDto.setCompanyDistrict(addCpyRequest.getCompanyDistrict());

        //先判断是添加哪种类型的公司
        //先添加子公司
        if (addCpyRequest.getParentCompanyName() != null){
            CompanyInfo parentCpyInfo = findCompanyInfoByCompanyName(addCpyRequest.getParentCompanyName());
            Integer parentLft = parentCpyInfo.getLft();
            Integer parentRgt = parentCpyInfo.getRgt();
            addCpyDto.setLft(parentRgt);
            addCpyDto.setRgt(parentRgt+1);

            //子公司
            Integer curLevel = parentCpyInfo.getCompanylevel()-1;
            if (curLevel < CompanyInfo.Companylevel.DISCPY.ordinal()){
                throw new GlobalException(CodeMsg.CPY_LEVEL_ERROR);
            }
            addCpyDto.setCompanylevel(curLevel);

            //更新已有公司的左右值
            try{
                companyDao.updateAddLft(parentRgt);
                companyDao.updateAddRgt(parentRgt);
                companyDao.insertCompany(addCpyDto);
            }catch (Exception e){
                throw new GlobalException(CodeMsg.SERVER_ERROR);
            }
        }else {
        // 添加根公司
            //先查出最大的rgt，之后新的lft+1
            Integer maxRgt = companyDao.getMaxRgt();
            addCpyDto.setLft(maxRgt+1);
            addCpyDto.setRgt(maxRgt+2);
            addCpyDto.setCompanylevel(CompanyInfo.Companylevel.HEADCPY.ordinal());
            try{
                companyDao.insertCompany(addCpyDto);
            }catch (Exception e){
                throw new GlobalException(CodeMsg.SERVER_ERROR);
            }
        }

        return true;
    }

    @Override
    @Transactional(rollbackFor=Exception.class)
    public Boolean delAllCompanyByCpyName(String companyName) {
        CompanyInfo cpyInfo = findCompanyInfoByCompanyName(companyName);
        Integer lft = cpyInfo.getLft();
        Integer rgt = cpyInfo.getRgt();
        //删除指定公司及所有子公司
        try{
            companyDao.delAllCompanyName(lft, rgt);
            companyDao.updateDelLft(lft, rgt);
            companyDao.updateDelRgt(lft, rgt);
        }catch (Exception e){
            throw new GlobalException(CodeMsg.SERVER_ERROR);
        }

        return true;
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
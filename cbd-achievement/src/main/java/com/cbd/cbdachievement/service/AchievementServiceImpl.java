package com.cbd.cbdachievement.service;

import com.cbd.cbdachievement.dao.AchievementDao;
import com.cbd.cbdcommoninterface.cbd_interface.achievement.AchievementService;
import com.cbd.cbdcommoninterface.cbd_interface.company.CompanyService;
import com.cbd.cbdcommoninterface.cbd_interface.contract.ContractService;
import com.cbd.cbdcommoninterface.cbd_interface.device.DeviceService;
import com.cbd.cbdcommoninterface.dto.CpyAchConditionDto;
import com.cbd.cbdcommoninterface.dto.CpyConditionDto;
import com.cbd.cbdcommoninterface.dto.DevAchConditionDto;
import com.cbd.cbdcommoninterface.dto.PersonConditionDto;
import com.cbd.cbdcommoninterface.pojo.achievement.AchievementCompanyInfo;
import com.cbd.cbdcommoninterface.pojo.achievement.AchievementDeviceInfo;
import com.cbd.cbdcommoninterface.pojo.achievement.AchievementInfo;
import com.cbd.cbdcommoninterface.pojo.company.CompanyInfo;
import com.cbd.cbdcommoninterface.request.*;
import com.cbd.cbdcommoninterface.response.*;
import com.cbd.cbdcommoninterface.result.CodeMsg;
import com.cbd.cbdcommoninterface.result.GlobalException;
import com.cbd.cbdcommoninterface.utils.PageUtils;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
@Slf4j
public class AchievementServiceImpl implements AchievementService {
    @Autowired
    CompanyService companyService;
    @Autowired
    ContractService contractService;
    @Autowired
    DeviceService deviceService;
    @Autowired
    AchievementDao achievementDao;

    @Override
    @Transactional(rollbackFor=Exception.class)
    public Boolean addAchievement(Integer salersID, String contractID, String companyID) {
        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        //因为初始值为0,所以要+1
        int month = cal.get(Calendar.MONTH)+1;
        Float achievement = contractService.findContractInfoByContractID(contractID).getDellFee();

        try{
            //添加个人业绩
            Float beforeAchievement = achievementDao.findPersonAchievement(salersID, month, year);
            Float curAchievement = achievement;
            if (beforeAchievement != null){
                curAchievement += beforeAchievement;
            }
            achievementDao.replacePersonAchievement(month, year, curAchievement, salersID, companyID);

            //添加合同业绩
            Integer appointContractCount = achievementDao.findContractCount(contractID, month, year);
            Integer curContractCount = 1;
            if (appointContractCount != null){
                curContractCount += appointContractCount;
            }
            String contractTypeName = contractService.findContractInfoByContractID(contractID).getContractTypeName();
            achievementDao.replaceContractAchievement(month, year, salersID, companyID, contractID, curContractCount, contractTypeName);

            //添加公司业绩
            //获取所有父公司ID
            List<String> parentIDList = companyService.getUpCompanyIDByCompanyID(companyID);
            //遍历所有父公司ID，添加业绩
            for(String parentCpyID : parentIDList){
                /**
                 * 添加公司业绩
                 */
                Float beforeCompanyAchievement = achievementDao.findCpyAchievement(parentCpyID, month, year);
                Float curCompanyAchievement = achievement;
                if (beforeCompanyAchievement != null){
                    curCompanyAchievement += beforeCompanyAchievement;
                }
                achievementDao.replaceCompanyAchievement(month, year, curCompanyAchievement, parentCpyID);
                //添加公司业绩统计信息
                AchievementCompanyInfo beforeAchievementInfo = achievementDao.findStaticsCpyAchievement(parentCpyID);
                //统计量初始化
                Float beforeAchievementCount = 0f;
                Float curAchievementCount = achievement;
                Integer salersCount = 0;
                Integer contractCount = 1;
                //说明公司业绩总统计里没有当前公司的统计信息
                if (beforeAchievementInfo == null){
                    List<CompanyInfo> cpyList = companyService.getAllCompanyListByCompanyName(companyService.findCompanyInfoByCompanyID(parentCpyID).getCompanyName());
                    //筛选出所有4s店ID
                    List<String> disCpyIDList = new ArrayList<>();
                    for (CompanyInfo cpyInfo : cpyList){
                        if (cpyInfo.getCompanylevel().equals(CompanyInfo.Companylevel.DISCPY.ordinal())){
                            disCpyIDList.add(cpyInfo.getCompanyID());
                        }
                    }
                    // TODO 调人事接口，获取当前公司下的销售人员数量
                    for (String disCpyID : disCpyIDList){

                    }
                }else {
                    curAchievementCount += beforeAchievementCount;
                    salersCount = beforeAchievementInfo.getSalersCount();
                    //新完成一份合同，总数加一
                    contractCount = beforeAchievementInfo.getContractCount()+1;
                }

                achievementDao.replaceCompanyStatistics(parentCpyID, curAchievementCount, salersCount, contractCount);

                /**
                 * 添加设备业绩
                 */
                //添加设备业绩
                ContractInfoResponse orderContractInfo = contractService.findContractInfoByContractID(contractID);
                String devName = orderContractInfo.getDevName();
                Integer devNums = orderContractInfo.getDevNums();
                Integer devTypeID = deviceService.findDevTypeByDevName(devName).getDevTypeID();
                //获取此公司指定月份之前设备销售数量
                Integer beforeSalesNums = achievementDao.findDevAchievement(devTypeID, parentCpyID, month, year);
                Integer curSalesNums = devNums;
                if (beforeSalesNums != null){
                    curSalesNums += beforeSalesNums;
                }
                achievementDao.replaceDeviceAchievement(devTypeID, parentCpyID, curSalesNums, month, year);
            }
        }catch (Exception e){
            log.info("Exception:{}",e.toString());
            throw new GlobalException(CodeMsg.SERVER_ERROR);
        }
        return true;
    }

    @Override
    public PageResponse findCompanyAchievementByCondition(PageCpyConditionRequest pageCpyConditionRequest) {
        CompanyInfo companyInfo = companyService.findCompanyInfoByCompanyID(pageCpyConditionRequest.getCompanyID());
        //设置DTO
        CpyConditionDto cpyConditionDto = new CpyConditionDto();
        cpyConditionDto.setLft(companyInfo.getLft());
        cpyConditionDto.setRgt(companyInfo.getRgt());
        cpyConditionDto.setCompanyCity(pageCpyConditionRequest.getCompanyCity());
        cpyConditionDto.setCompanyDistrict(pageCpyConditionRequest.getCompanyDistrict());
        cpyConditionDto.setCompanylevel(pageCpyConditionRequest.getCompanylevel());
        cpyConditionDto.setCompanyProvince(pageCpyConditionRequest.getCompanyProvince());
        cpyConditionDto.setSortMoney(pageCpyConditionRequest.getSortMoney());
        cpyConditionDto.setSortContract(pageCpyConditionRequest.getSortContract());

        PageRequest pageRequest = pageCpyConditionRequest.getPageRequest();
        int pageNum = pageRequest.getPageNum();
        int pageSize = pageRequest.getPageSize();
        Page page = PageHelper.startPage(pageNum, pageSize);

        //获取对应的公司业绩统计列表
        List<AchievementCompanyInfo> companyInfoList = achievementDao.findAchCpyInfoByCondition(cpyConditionDto);
        //包装response类
        List<PageCompanyListResponse> companyListResponseList = new ArrayList<>();
        for (AchievementCompanyInfo achievementCompanyInfo : companyInfoList){
            PageCompanyListResponse companyListResponse = new PageCompanyListResponse();
            CompanyInfo cpyInfo = companyService.findCompanyInfoByCompanyID(achievementCompanyInfo.getCompanyID());
            companyListResponse.setCompanyID(cpyInfo.getCompanyID());
            companyListResponse.setCompanyName(cpyInfo.getCompanyName());
            companyListResponse.setAchievement(achievementCompanyInfo.getAchievementCount());
            companyListResponse.setCompanyCity(cpyInfo.getCompanyCity());
            companyListResponse.setCompanyDistrict(cpyInfo.getCompanyDistrict());
            companyListResponse.setCompanyProvince(cpyInfo.getCompanyProvince());

            companyListResponseList.add(companyListResponse);
        }

        PageInfo<PageCompanyListResponse> companyListResponsePageInfo = new PageInfo(companyListResponseList);
        companyListResponsePageInfo.setTotal(page.getTotal());
        companyListResponsePageInfo.setPages(page.getPages());

        return PageUtils.getPageResponse(companyListResponsePageInfo);
    }

    @Override
    public PageResponse findCompanyStaticsAchievementByCompanyID(PageCpyIDRequest cpyIDRequest) {

        /**
         * 获取所有子公司业绩统计信息列表
         */
        CompanyInfo companyInfo = companyService.findCompanyInfoByCompanyID(cpyIDRequest.getCompanyID());
        //设置DTO
        PageRequest pageRequest = cpyIDRequest.getPageRequest();
        int pageNum = pageRequest.getPageNum();
        int pageSize = pageRequest.getPageSize();
        Page page = PageHelper.startPage(pageNum, pageSize);

        CpyConditionDto cpyConditionDto = new CpyConditionDto();
        cpyConditionDto.setLft(companyInfo.getLft());
        cpyConditionDto.setRgt(companyInfo.getRgt());
        List<AchievementCompanyInfo> achievementCompanyInfoList = achievementDao.findAchCpyInfoByCondition(cpyConditionDto);
        //包装成response类
        List<PageCpyStaticsResponse> cpyStaticsResponseList = new ArrayList<>();
        for (AchievementCompanyInfo achievementCompanyInfo : achievementCompanyInfoList){
            String companyName = companyService.findCompanyInfoByCompanyID(achievementCompanyInfo.getCompanyID()).getCompanyName();
            PageCpyStaticsResponse temp = new PageCpyStaticsResponse();
            temp.setCompanyName(companyName);
            temp.setAchievementCount(achievementCompanyInfo.getAchievementCount());
            temp.setContractCount(achievementCompanyInfo.getContractCount());
            temp.setSalersCount(achievementCompanyInfo.getSalersCount());
            cpyStaticsResponseList.add(temp);
        }

        PageInfo<PageCpyStaticsResponse> cpyStaticsResponsePageInfo = new PageInfo<>(cpyStaticsResponseList);
        cpyStaticsResponsePageInfo.setPages(page.getPages());
        cpyStaticsResponsePageInfo.setTotal(page.getTotal());

        return PageUtils.getPageResponse(cpyStaticsResponsePageInfo);
    }

    @Override
    public PageResponse findPersonStaticsAchievementByCompanyID(PageCpyIDRequest cpyIDRequest) {

        CompanyInfo companyInfo = companyService.findCompanyInfoByCompanyID(cpyIDRequest.getCompanyID());
        Integer lft = companyInfo.getLft();
        Integer rgt = companyInfo.getRgt();
        //获取所有子公司销售人员ID列表
        PageRequest pageRequest = cpyIDRequest.getPageRequest();
        int pageNum = pageRequest.getPageNum();
        int pageSize = pageRequest.getPageSize();
        Page page = PageHelper.startPage(pageNum, pageSize);
        List<String> salersIDList = achievementDao.findPersonIDByCompanyID(lft, rgt);

        List<PagePersonStaticsResponse> personStaticsResponseList = new ArrayList<>();
        //获取对应员工的销售统计
        for (String salersID : salersIDList){
            PagePersonStaticsResponse personStaticsResponse = new PagePersonStaticsResponse();
            personStaticsResponse.setSalersID(salersID);
            // TODO 调人事接口获取员工信息
//            personStaticsResponse.setSalersName();
//            personStaticsResponse.setSalersSexy();
//            String companyID = ;
//            personStaticsResponse.setCompanyName(companyService.findCompanyInfoByCompanyID(companyID).getCompanyName());
            //获取指定员工的销售额统计
            Integer salersAchievementCount = achievementDao.getPersonAchievementByPersonID(salersID);
            personStaticsResponse.setAchievementCount(salersAchievementCount);
            //获取指定员工的完成合同统计
            Integer salersContractCount = achievementDao.getPersonContractCountByPersonID(salersID);
            personStaticsResponse.setContractCount(salersContractCount);
            personStaticsResponseList.add(personStaticsResponse);
        }

        PageInfo<PagePersonStaticsResponse> personStaticsResponsePageInfo = new PageInfo<>(personStaticsResponseList);
        personStaticsResponsePageInfo.setTotal(page.getTotal());
        personStaticsResponsePageInfo.setPages(page.getPages());

        return PageUtils.getPageResponse(personStaticsResponsePageInfo);
    }


    @Override
    public List<String> getDevNameByCompanyID(String companyID) {
        CompanyInfo companyInfo = companyService.findCompanyInfoByCompanyID(companyID);
        Integer lft = companyInfo.getLft();
        Integer rgt = companyInfo.getRgt();

        //获取当前公司下有销售记录的设备类型ID列表
        List<String> devTypeIDList = achievementDao.getDevNameByCompanyID(lft, rgt);

        List<String> devNameList = new ArrayList<>();
        for (String devTypeID : devTypeIDList){
            String devName = achievementDao.findDevNameByID(devTypeID);
            devNameList.add(devName);
        }

        return devNameList;
    }

    @Override
    public PageResponse findDevAchievementByCondition(PageDevAchConditionRequest devAchConditionRequest) {

        //设置设备业绩的当前月份
        if(devAchConditionRequest.getYear().equals("")){
            Calendar cal = Calendar.getInstance();
            int year = cal.get(Calendar.YEAR);
            //因为初始值为0,所以要+1
            int month = cal.get(Calendar.MONTH)+1;
            devAchConditionRequest.setYear(year);
            devAchConditionRequest.setMonth(month);
        }

        CompanyInfo companyInfo = companyService.findCompanyInfoByCompanyID(devAchConditionRequest.getCompanyID());
        DevAchConditionDto devAchConditionDto = new DevAchConditionDto();
        devAchConditionDto.setDevTypeID(deviceService.findDevTypeByDevName(devAchConditionRequest.getDevName()).getDevTypeID());
        if(devAchConditionRequest.getYear() != null){
            devAchConditionDto.setYear(devAchConditionRequest.getYear());
        }
        if (devAchConditionRequest.getMonth() != null){
            devAchConditionDto.setMonth(devAchConditionRequest.getMonth());
        }
        devAchConditionDto.setLft(companyInfo.getLft());
        devAchConditionDto.setRgt(companyInfo.getRgt());

        PageRequest pageRequest = devAchConditionRequest.getPageRequest();
        int pageNum = pageRequest.getPageNum();
        int pageSize = pageRequest.getPageSize();
        Page page = PageHelper.startPage(pageNum, pageSize);


        //获取设备业绩信息列表
        List<AchievementDeviceInfo> achievementDeviceInfoList = achievementDao.findDevAchievementByCondition(devAchConditionDto);
        List<PageDevAchListResponse> devAchListResponseList = new ArrayList<>();
        for (AchievementDeviceInfo achievementDeviceInfo : achievementDeviceInfoList){
            PageDevAchListResponse devAchListResponse = new PageDevAchListResponse();
            String companyName = companyService.findCompanyInfoByCompanyID(achievementDeviceInfo.getCompanyID()).getCompanyName();
            String devName = achievementDao.findDevNameByID(achievementDeviceInfo.getDevTypeID());
            String Date = achievementDeviceInfo.getYear()+"年"+achievementDeviceInfo.getMonth()+"月";
            devAchListResponse.setCompanyName(companyName);
            devAchListResponse.setDevName(devName);
            devAchListResponse.setDate(Date);
            devAchListResponse.setSalesNums(achievementDeviceInfo.getSalesNums());

            devAchListResponseList.add(devAchListResponse);
        }

        PageInfo<PageDevAchListResponse> devAchListResponsePageInfo = new PageInfo<>(devAchListResponseList);
        devAchListResponsePageInfo.setPages(page.getPages());
        devAchListResponsePageInfo.setTotal(page.getTotal());

        return PageUtils.getPageResponse(devAchListResponsePageInfo);
    }

    @Override
    public PageResponse findPersonAchievementByCondition(PagePersonAchConditionRequest achConditionRequest) {

        PersonConditionDto personConditionDto = new PersonConditionDto();
        personConditionDto.setContractTypeName(achConditionRequest.getContractTypeName());
        personConditionDto.setMonth(achConditionRequest.getMonth());
        personConditionDto.setYear(achConditionRequest.getYear());
        personConditionDto.setSalersID(achConditionRequest.getSalersID());

        PageRequest pageRequest = achConditionRequest.getPageRequest();
        int pageNum = pageRequest.getPageNum();
        int pageSize = pageRequest.getPageSize();
        Page page = PageHelper.startPage(pageNum, pageSize);

        //根据条件获取指定员工的业绩信息
        List<AchievementInfo> achievementInfoList = achievementDao.findPersonAchievementByCondition(personConditionDto);
        List<PagePersonListResponse> personListResponseList = new ArrayList<>();
        Map<String, PagePersonListResponse> listResponseMap = new HashMap<>();
        //因为不指定合同的话，就会出现一个日期有多条记录的情况
        //所以需要把这些记录的合同完成数量累加起来，作为指定日期的总合同完成数量
        for(AchievementInfo achievementInfo : achievementInfoList){
            String Date = achievementInfo.getYear()+"年"+ achievementInfo.getMonth()+"月";
            if (!listResponseMap.containsKey(Date)){
                PagePersonListResponse temp = new PagePersonListResponse();
                temp.setDate(Date);
                temp.setAchievement(achievementInfo.getAchievement());
                temp.setContractCount(achievementInfo.getContractCount());
            }else {
                PagePersonListResponse temp = listResponseMap.get(Date);
                temp.setContractCount(listResponseMap.get(Date).getContractCount()+ achievementInfo.getContractCount());
            }
        }
        for (String Date : listResponseMap.keySet()){
            personListResponseList.add(listResponseMap.get(Date));
        }

        PageInfo<PagePersonListResponse> personListResponsePageInfo= new PageInfo<>(personListResponseList);
        personListResponsePageInfo.setTotal(page.getTotal());
        personListResponsePageInfo.setPages(page.getPages());

        return PageUtils.getPageResponse(personListResponsePageInfo);
    }

    @Override
    public Map<String, Integer> findSpecificPersonAchievement(SpecifiPersonRequest personRequest) {
        String Date = personRequest.getDate();
        //因为日期是xxxx年yy月，所以字符串截取固定长度
        Integer year = Integer.parseInt(Date.substring(0,4));
        Integer month = -1;
        //说明是y月,否则为yy月
        if (Date.length() == 7){
            month = Integer.parseInt(Date.substring(5,6));
        }else {
            month = Integer.parseInt(Date.substring(5,7));
        }

        PersonConditionDto personConditionDto = new PersonConditionDto();;
        personConditionDto.setMonth(month);
        personConditionDto.setYear(year);
        personConditionDto.setSalersID(personRequest.getSalersID());

        List<AchievementInfo> achievementInfoList = achievementDao.findPersonAchievementByCondition(personConditionDto);
        Map<String, Integer> resultMap = new HashMap<>();
        for(AchievementInfo achievementInfo : achievementInfoList){
            resultMap.put(achievementInfo.getContractTypeName(), achievementInfo.getContractCount());
        }

        return resultMap;
    }

    @Override
    public PageResponse findCpyAchievementByCondition(PageCpyAchConditionRequest cpyAchConditionRequest) {
        String companyID = companyService.findCompanyInfoByCompanyName(cpyAchConditionRequest.getCompanyName()).getCompanyID();
        CpyAchConditionDto cpyAchConditionDto = new CpyAchConditionDto();
        cpyAchConditionDto.setCompanyID(companyID);
        cpyAchConditionDto.setContractTypeName(cpyAchConditionRequest.getContractTypeName());
        cpyAchConditionDto.setMonth(cpyAchConditionRequest.getMonth());
        cpyAchConditionDto.setYear(cpyAchConditionRequest.getYear());

        PageRequest pageRequest = cpyAchConditionRequest.getPageRequest();
        int pageNum = pageRequest.getPageNum();
        int pageSize = pageRequest.getPageSize();
        Page page = PageHelper.startPage(pageNum, pageSize);

        //根据条件获取指定公司的业绩信息
        List<AchievementInfo> achievementInfoList = achievementDao.findCpyAchievementByCondition(cpyAchConditionDto);
        List<PagePersonListResponse> cpyListResonse = new ArrayList<>();
        Map<String, PagePersonListResponse> listResponseMap = new HashMap<>();
        //因为不指定合同的话，就会出现一个日期有多条记录的情况
        //所以需要把这些记录的合同完成数量累加起来，作为指定日期的总合同完成数量
        for(AchievementInfo achievementInfo : achievementInfoList){
            String Date = achievementInfo.getYear()+"年"+ achievementInfo.getMonth()+"月";
            if (!listResponseMap.containsKey(Date)){
                PagePersonListResponse temp = new PagePersonListResponse();
                temp.setDate(Date);
                temp.setAchievement(achievementInfo.getAchievement());
                temp.setContractCount(achievementInfo.getContractCount());
            }else {
                PagePersonListResponse temp = listResponseMap.get(Date);
                temp.setContractCount(listResponseMap.get(Date).getContractCount()+ achievementInfo.getContractCount());
            }
        }
        for (String Date : listResponseMap.keySet()){
            cpyListResonse.add(listResponseMap.get(Date));
        }

        PageInfo<PagePersonListResponse> cpyListResponsePageInfo= new PageInfo<>(cpyListResonse);
        cpyListResponsePageInfo.setPages(page.getPages());
        cpyListResponsePageInfo.setTotal(page.getTotal());

        return PageUtils.getPageResponse(cpyListResponsePageInfo);
    }

    @Override
    public Map<String, Integer> findSpecificCpyAchievement(SpecifiCpyRequest specifiCpyRequest) {
        String Date = specifiCpyRequest.getDate();
        //因为日期是xxxx年yy月，所以字符串截取固定长度
        Integer year = Integer.parseInt(Date.substring(0,4));
        Integer month = -1;
        //说明是y月,否则为yy月
        if (Date.length() == 7){
            month = Integer.parseInt(Date.substring(5,6));
        }else {
            month = Integer.parseInt(Date.substring(5,7));
        }

        String companyID = companyService.findCompanyInfoByCompanyName(specifiCpyRequest.getCompanyName()).getCompanyID();
        CpyAchConditionDto cpyAchConditionDto = new CpyAchConditionDto();
        cpyAchConditionDto.setCompanyID(companyID);
        cpyAchConditionDto.setMonth(month);
        cpyAchConditionDto.setYear(year);

        List<AchievementInfo> achievementInfoList = achievementDao.findCpyAchievementByCondition(cpyAchConditionDto);
        Map<String, Integer> resultMap = new HashMap<>();
        for(AchievementInfo achievementInfo : achievementInfoList){
            resultMap.put(achievementInfo.getContractTypeName(), achievementInfo.getContractCount());
        }

        return resultMap;
    }
}
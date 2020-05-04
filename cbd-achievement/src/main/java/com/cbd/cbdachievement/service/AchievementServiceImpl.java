package com.cbd.cbdachievement.service;

import com.cbd.cbdachievement.dao.AchievementDao;
import com.cbd.cbdcommoninterface.cbd_interface.achievement.AchievementService;
import com.cbd.cbdcommoninterface.cbd_interface.company.CompanyService;
import com.cbd.cbdcommoninterface.cbd_interface.contract.ContractService;
import com.cbd.cbdcommoninterface.request.PageCpyAchConditionRequest;
import com.cbd.cbdcommoninterface.response.PageResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.List;

@Service
@Slf4j
public class AchievementServiceImpl implements AchievementService {
    @Autowired
    CompanyService companyService;
    @Autowired
    ContractService contractService;
    @Autowired
    AchievementDao achievementDao;

    @Override
    public Boolean addAchievement(String salersID, String contractID, String companyID) {
        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH )+1;
        Float achievement = contractService.findContractInfoByContractID(contractID).getDellFee();
        //获取所有父公司ID
        List<String> parentIDList = companyService.getUpCompanyIDByCompanyID(companyID);
        //添加个人业绩
//        achievement.updatePerson(month, year, achievement, salersID);
        //添加公司业绩

        //添加合同业绩

        //添加设备业绩


        return true;
    }

    @Override
    public PageResponse findCompanyAchievementByCondition(PageCpyAchConditionRequest pageCpyAchConditionRequest) {
        return null;
    }
}

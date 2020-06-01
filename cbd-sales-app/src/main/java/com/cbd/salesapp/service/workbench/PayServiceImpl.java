package com.cbd.salesapp.service.workbench;

import com.cbd.cbdcommoninterface.cbd_interface.achievement.AchievementService;
import com.cbd.cbdcommoninterface.cbd_interface.salesapp.workbench.PayService;
import com.cbd.cbdcommoninterface.request.salesapp.workbench.PayLogQuery;
import com.cbd.salesapp.dao.workbench.PayServiceDao;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author: Monster
 * @date: 2020/5/31 15:42
 * @Description
 */
public class PayServiceImpl implements PayService {
    @Autowired
    private PayServiceDao payServiceDao;
    @Autowired
    private AchievementService achievementService;

    @Override
    public int payLog(PayLogQuery query) {
        achievementService.addAchievement(query.getSalesId(),query.getContractId(),query.getCompanyId());
        return payServiceDao.payLog(query);
    }
}

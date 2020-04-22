package com.cbd.installerapp.service.common;

import com.cbd.cbdcommoninterface.cbd_interface.installerapp.common.UpdateTimeService;
import com.cbd.installerapp.dao.common.UpdateTimeDao;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author: Monster
 * @date: 2020/4/22 10:21
 * @Description
 */
public class UpdateTimeServiceImpl implements UpdateTimeService {
    @Autowired
    private UpdateTimeDao updateTimeDao;

    @Override
    public int updateOrderTime(String orderTime,Integer orderId){
        updateTimeDao.updateOrderTime(orderTime,orderId);
        return updateTimeDao.updateOrderInfoTime(orderTime,orderId);
    }
}

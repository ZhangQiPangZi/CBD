package com.cbd.cbdcommoninterface.cbd_interface.installerapp.common;

/**
 * @author: Monster
 * @date: 2020/4/22 10:20
 * @Description
 */
public interface UpdateTimeService {
    /**
     * 工程师改变预约时间
     * @param orderTime
     * @param orderId
     * @return
     */
    int updateOrderTime(String orderTime,Integer orderId);
}

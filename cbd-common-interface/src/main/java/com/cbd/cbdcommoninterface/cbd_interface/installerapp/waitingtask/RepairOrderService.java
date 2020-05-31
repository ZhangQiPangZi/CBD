package com.cbd.cbdcommoninterface.cbd_interface.installerapp.waitingtask;

import com.cbd.cbdcommoninterface.request.installerapp.waitingtask.RemoveQuery;

/**
 * @author: Monster
 * @date: 2020/4/22 11:14
 * @Description
 */
public interface RepairOrderService {

    /**
     * 更换设备
     * @param query
     * @return
     */
    int changeDev(RemoveQuery query);

    /**
     * 维修订单完成
     * @param flag
     * @param orderId
     * @param phoneNumber
     * @param installerId
     * @return
     */
    int orderComplete(Integer flag,Integer orderId,String phoneNumber,Integer installerId);

}

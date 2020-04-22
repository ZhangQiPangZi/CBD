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
     * 订单完成
     * @param orderId
     * @param devId
     * @param simId
     * @param phoneNumber
     * @return
     */
    int orderComplete(Integer devId,Integer simId,Integer orderId,String phoneNumber);

}

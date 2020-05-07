package com.cbd.cbdcommoninterface.cbd_interface.installerapp.waitingtask;

/**
 * @author: Monster
 * @date: 2020/4/22 16:53
 * @Description
 */
public interface InstallOrderService {
    /**
     * 设备安装完成
     * @param orderId
     * @param installerId
     * @return
     */
    int installOrderComplete(Integer installerId,Integer orderId);
}

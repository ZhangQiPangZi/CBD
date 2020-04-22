package com.cbd.cbdcommoninterface.cbd_interface.installerapp.waitingtask;

import com.cbd.cbdcommoninterface.request.installerapp.waitingtask.RemoveQuery;

/**
 * @author: Monster
 * @date: 2020/4/22 11:14
 * @Description
 */
public interface RemoveOrderService {
    /**
     * 安装工拆除设备
     * @param query
     * @return
     */
    int removeDev(RemoveQuery query);
}

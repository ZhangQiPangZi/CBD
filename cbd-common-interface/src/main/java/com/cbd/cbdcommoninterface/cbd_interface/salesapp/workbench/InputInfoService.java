package com.cbd.cbdcommoninterface.cbd_interface.salesapp.workbench;

import com.cbd.cbdcommoninterface.request.salesapp.workbench.OrderInfoQuery;

/**
 * @author: Monster
 * @date: 2020/4/21 15:11
 * @Description
 */
public interface InputInfoService {
    /**
     * 录入用户信息
     * @param query
     * @return
     */
    int confirmInputInfo(OrderInfoQuery query);
}

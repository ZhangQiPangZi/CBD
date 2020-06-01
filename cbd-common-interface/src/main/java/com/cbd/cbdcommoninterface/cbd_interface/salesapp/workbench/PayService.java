package com.cbd.cbdcommoninterface.cbd_interface.salesapp.workbench;

import com.cbd.cbdcommoninterface.request.salesapp.workbench.PayLogQuery;

/**
 * @author: Monster
 * @date: 2020/5/31 15:41
 * @Description
 */
public interface PayService {
    /**
     * 付款后录入付款信息
     * @param query
     * @return
     */
    int payLog(PayLogQuery query);
}

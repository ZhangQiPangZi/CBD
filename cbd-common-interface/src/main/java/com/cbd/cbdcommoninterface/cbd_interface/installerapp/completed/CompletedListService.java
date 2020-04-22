package com.cbd.cbdcommoninterface.cbd_interface.installerapp.completed;

import com.cbd.cbdcommoninterface.pojo.installerapp.waitingtask.OrderInfoDO;

import java.util.List;

/**
 * @author: Monster
 * @date: 2020/4/22 16:06
 * @Description
 */
public interface CompletedListService {
    /**
     * 查询所有已经完成的订单 根据订单类别搜索
     * @param installerId
     * @param orderTypeCode
     * @return
     */
    List<OrderInfoDO> getList(Integer installerId,Integer orderTypeCode);
}

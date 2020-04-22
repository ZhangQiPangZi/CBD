package com.cbd.cbdcommoninterface.cbd_interface.installerapp.waitingtask;

import com.cbd.cbdcommoninterface.pojo.installerapp.waitingtask.OrderInfoDO;

import java.util.List;

/**
 * @author: Monster
 * @date: 2020/4/21 23:14
 * @Description
 */
public interface TaskListService {
    /**
     * 获取该工程师所有待处理订单 按订单类型进行搜索
     * @param installerId
     * @param orderTypeCode
     * @return
     */
    List<OrderInfoDO> getTaskList(Integer installerId,Integer orderTypeCode);
}

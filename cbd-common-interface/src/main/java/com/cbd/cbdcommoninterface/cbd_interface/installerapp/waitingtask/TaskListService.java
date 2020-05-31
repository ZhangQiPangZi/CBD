package com.cbd.cbdcommoninterface.cbd_interface.installerapp.waitingtask;

import com.cbd.cbdcommoninterface.request.PageRequest;
import com.cbd.cbdcommoninterface.response.PageResponse;

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
     * @param pageRequest
     * @return
     */
    PageResponse getTaskList(Integer installerId, Integer orderTypeCode, PageRequest pageRequest);
}

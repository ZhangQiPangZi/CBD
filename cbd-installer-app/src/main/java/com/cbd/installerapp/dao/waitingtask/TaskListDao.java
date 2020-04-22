package com.cbd.installerapp.dao.waitingtask;

import com.cbd.cbdcommoninterface.pojo.installerapp.waitingtask.OrderInfoDO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author: Monster
 * @date: 2020/4/21 23:18
 * @Description
 */
@Mapper
public interface TaskListDao {
    /**
     * 获取该工程师所有待处理订单 按订单类型进行搜索
     * @param installerId
     * @param orderTypeCode
     * @return
     */
    List<OrderInfoDO> getTaskList(@Param("installerId") Integer installerId, @Param("orderTypeCode") Integer orderTypeCode);

}

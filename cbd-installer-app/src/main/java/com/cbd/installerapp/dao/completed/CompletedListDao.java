package com.cbd.installerapp.dao.completed;

import com.cbd.cbdcommoninterface.pojo.installerapp.waitingtask.OrderInfosDO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author: Monster
 * @date: 2020/4/22 16:10
 * @Description
 */
@Mapper
public interface CompletedListDao {
    /**
     * 查询所有已经完成的订单 根据订单类别搜索
     * @param installerId
     * @param orderTypeCode
     * @return
     */
    List<OrderInfosDO> getList(@Param("installerId") Integer installerId, @Param("orderTypeCode") Integer orderTypeCode);
}

package com.cbd.salesapp.dao.messagelist;

import com.cbd.cbdcommoninterface.pojo.salesapp.messagelist.OrderInfoDO;
import com.cbd.cbdcommoninterface.response.salesapp.messagelist.OrderInfoVO;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * @author: Monster
 * @date: 2020/4/21 15:54
 * @Description
 */
@Mapper
public interface MessageListDao {
    /**
     * 获取所有订单列表 根据订单类型搜索
     * @param orderTypeCode
     * @return
     */
    List<OrderInfoDO> getList(@Param("orderTypeCode")Integer orderTypeCode);

    /**
     * 统计已指派订单的数量
     * @return
     */
    @Select("SELECT COUNT(*) FROM orderinfo WHERE orderstateTypeCode = '1';")
    int processedCount();

    /**
     * 统计未指派订单的数量
     * @return
     */
    @Select("SELECT COUNT(*) FROM orderinfo WHERE orderstateTypeCode = '-1';")
    int pendingCount();

    /**
     * 查看订单的详细信息
     * @param id
     * @return
     */
    OrderInfoVO getOrderInfo(@Param("id") Integer id);

    /**
     * 重新指派工程师
     * @param id
     * @return
     */
    @Update("UPDATE orderinfo SET installerId = NULL , orderStateTypeCode='-1' WHERE id = #{id};")
    int reAssign(@Param("id") Integer id);

    /**
     * 重新指派工程师时 将该订单从已指派的工程师的任务列表中移除
     * @param orderId
     * @return
     */
    @Delete("DELETE FROM installertasklist WHERE orderId=#{orderId};")
    int removeFromTaskList(@Param("orderId") Integer orderId);
}

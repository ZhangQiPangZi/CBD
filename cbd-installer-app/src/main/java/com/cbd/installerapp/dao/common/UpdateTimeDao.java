package com.cbd.installerapp.dao.common;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;

/**
 * @author: Monster
 * @date: 2020/4/22 10:21
 * @Description
 */
@Mapper
public interface UpdateTimeDao {
    /**
     * 工程师改变任务列表里的预约时间
     * @param orderTime
     * @return
     */
    @Update("UPDATE installertasklist SET ordertime =#{orderTime} WHERE orderId =#{orderId}")
    int updateOrderTime(@Param("orderTime") String orderTime,@Param("orderId") Integer orderId);

    /**
     * 工程师改变orderinfo里的预约时间
     * @param orderTime
     * @return
     */
    @Update("UPDATE orderinfo SET ordertime=#{orderTime} WHERE id =#{orderId}")
    int updateOrderInfoTime(@Param("orderTime") String orderTime,@Param("orderId") Integer orderId);
}

package com.cbd.salesapp.dao.workbench;

import com.cbd.cbdcommoninterface.request.salesapp.workbench.PayLogQuery;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * @author: Monster
 * @date: 2020/5/31 15:43
 * @Description
 */
@Mapper
@Repository
public interface PayServiceDao {
    /**
     * 支付成功后录入支付信息
     * @param query
     * @return
     */
    @Insert("insert into orderpayrecord values(NULL,#{query.orderId},#{query.payTime},#{query.payResult},#{query.serviceStartTime},#{query.serviceLimitYear},#{query.orderPrice});")
    int payLog(@Param("query") PayLogQuery query);
}

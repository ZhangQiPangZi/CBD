package com.black.lei.dao;

import com.cbd.cbdcommoninterface.response.leiVo.BaseOrderInfoVo;
import com.cbd.cbdcommoninterface.response.leiVo.OrderScheduledVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @author shy_black
 * @date 2020/5/29 13:49
 * @Description:
 */
@Mapper
public interface orderInfoDao {

    @Select("select id as orderId," +
            "salesId,carOwnerName,phoneNumber,licenseNumber " +
            "from orderinfo " +
            "where orderId=#{orderId} and orderTypeCode=1 ")
    BaseOrderInfoVo findOrderInfoByOrderID(@Param("orderId") String orderID);


    //查出devID不为空且为新增支付单的orderID 和devID
    @Select("select orderId as orderID ,devId as devID " +
            " from orderinfo where devID is not null orderStateTypeCode = 1 ")
    List<OrderScheduledVo>  findorderIDAndDevID();


}

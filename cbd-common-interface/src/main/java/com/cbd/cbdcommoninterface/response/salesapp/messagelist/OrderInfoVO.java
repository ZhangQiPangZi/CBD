package com.cbd.cbdcommoninterface.response.salesapp.messagelist;

import lombok.Data;

import java.io.Serializable;

/**
 * @author: Monster
 * @date: 2020/4/21 15:49
 * @Description
 */
@Data
public class OrderInfoVO implements Serializable {
    /**
     * 序号
     */
    private Integer id;
    /**
     * 预约时间
     */
    private String orderTime;
    /**
     * 预约地点
     */
    private String orderAddr;
    /**
     * 车主姓名
     */
    private String carOwnerName;
    /**
     * 车主电话
     */
    private String carOwnerNumber;
    /**
     * 工程师姓名
     */
    private String name;
    /**
     * 工程师电话
     */
    private String phoneNumber;
    /**
     * 订单类型
     */
    private Integer orderTypeCode;
    /**
     * 订单状态
     */
    private Integer orderStateTypeCode;

}

package com.cbd.cbdcommoninterface.pojo.salesapp.messagelist;

import lombok.Data;

import java.io.Serializable;

/**
 * @author: Monster
 * @date: 2020/4/21 15:50
 * @Description
 */
@Data
public class OrderInfoDO implements Serializable {
    /**
     * 序号
     */
    private Integer id;
    /**
     * 预约时间
     */
    private String orderTime;
    /**
     * 工程师姓名
     */
    private String name;
    /**
     * 订单类型
     */
    private Integer orderTypeCode;
    /**
     * 订单状态
     */
    private Integer orderStateTypeCode;
}

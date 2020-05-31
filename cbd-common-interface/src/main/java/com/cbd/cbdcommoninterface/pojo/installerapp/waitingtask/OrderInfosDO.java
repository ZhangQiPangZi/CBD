package com.cbd.cbdcommoninterface.pojo.installerapp.waitingtask;

import lombok.Data;

import java.io.Serializable;

/**
 * @author: Monster
 * @date: 2020/4/21 23:12
 * @Description
 */
@Data
public class OrderInfosDO implements Serializable {
    /**
     * 序号
     */
    private Integer id;
    /**
     * 预约时间
     */
    private String orderTime;
    /**
     * 车主姓名
     */
    private String carOwnerName;
    /**
     * 车主电话
     */
    private String phoneNumber;
    /**
     * 订单类型
     */
    private Integer orderTypeCode;
}

package com.cbd.cbdcommoninterface.request.salesapp.workbench;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @author: Monster
 * @date: 2020/4/21 15:10
 * @Description
 */
@Data
public class OrderInfoQuery implements Serializable {
    @ApiModelProperty(value = "序号",hidden = true)
    private Integer id;
    @ApiModelProperty(value = "销售人员ID",example = "125",required = true)
    private Integer salesID;

    @ApiModelProperty(value = "车主姓名",example = "张三",required = true)
    private String carOwnerName;

    @ApiModelProperty(value = "车牌号",example = "京A·125G2",required = true)
    private String licenseNumber;

    @ApiModelProperty(value = "车辆型号",example = "宝马五系",required = true)
    private String carType;

    @ApiModelProperty(value = "电话",example = "1531812431",required = true)
    private String phoneNumber;

    @ApiModelProperty(value = "预约时间",example = "2020-04-11 14:00:00",required = true)
    private String orderTime;

    @ApiModelProperty(value = "预约地点",example = "华清宫",required = true)
    private String orderAddr;

    @ApiModelProperty(value = "订单类型字典值",example = "1",required = true)
    private Integer orderTypeCode;

    @ApiModelProperty(value = "订单号")
    private String orderId;
}

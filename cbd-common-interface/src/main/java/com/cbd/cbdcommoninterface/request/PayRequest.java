package com.cbd.cbdcommoninterface.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
public class PayRequest implements Serializable {
    @ApiModelProperty(value = "web端是合同id")
    private String orderID;
    @ApiModelProperty(value = "订单信息--标题")
    private String orderSubject;
    @ApiModelProperty(value = "订单金额")
    private String orderPrice;
    @ApiModelProperty(value = "0 APP端订单 1 WEB端新增合同订单 2 WEB端续费合同订单")
    private Integer orderType;
    @ApiModelProperty(value = "只有续费时用，续费年限")
    private Float renewYears;
}

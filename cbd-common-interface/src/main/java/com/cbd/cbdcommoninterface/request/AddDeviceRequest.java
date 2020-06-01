package com.cbd.cbdcommoninterface.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
public class AddDeviceRequest implements Serializable {

    private String devName;
    @ApiModelProperty(value = "设备数量")
    private Integer devNums;
    private Integer managerID;
    private String companyID;
    private String SIMName;
    private Integer SIMNums;
    @ApiModelProperty(value = "网关id")
    private Integer gateWayID;
    @ApiModelProperty(value = "设备类型")
    private String devType;
    @ApiModelProperty(value = "设备厂家公司名")
    private String devFactoryName;
    @ApiModelProperty(value = "设备厂家联系人名")
    private String devFactoryPersonName;
    @ApiModelProperty(value = "设备厂家联系人方式")
    private String devFactoryPersonPhone;



}

package com.cbd.cbdcommoninterface.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
public class AddContractRequest implements Serializable {
    private String companyName;
    @ApiModelProperty(value = "合同类型名")
    private String contractTypeName;
    @ApiModelProperty(value = "设备名")
    private String devName;
    @ApiModelProperty(value = "设备数量")
    private Integer devNums;
    @ApiModelProperty(value = "服务时长", example = "三种 0.5 1 2年")
    private Float serverYears;
    @ApiModelProperty(value = "单笔成交费用")
    private Float dellFee;
    @ApiModelProperty(value = "合同服务费")
    private Float serverFee;

}

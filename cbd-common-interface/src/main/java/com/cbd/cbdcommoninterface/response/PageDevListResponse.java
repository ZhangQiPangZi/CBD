package com.cbd.cbdcommoninterface.response;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
public class PageDevListResponse implements Serializable {
    private String devID;
    private String devName;
    private String devType;
    private String companyName;
    @ApiModelProperty(value = "设备状态", example = "0 库中 1 出库 2 使用中 3 待返厂维修")
    private Integer devStatus;
}

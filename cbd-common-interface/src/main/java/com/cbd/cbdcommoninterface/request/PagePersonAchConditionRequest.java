package com.cbd.cbdcommoninterface.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
public class PagePersonAchConditionRequest implements Serializable {
    private PageRequest pageRequest;
    @ApiModelProperty(value = "员工ID，工号")
    private Integer salersID;
    private Integer year;
    private Integer month;
    @ApiModelProperty(value = "合同类型名")
    private String contractTypeName;


}

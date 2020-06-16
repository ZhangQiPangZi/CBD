package com.cbd.cbdcommoninterface.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
public class QueryUserAndCpyRequest implements Serializable {
    private String companyID;
    @ApiModelProperty(value = "可以是公司名或者员工姓名")
    private String queryKey;
    @ApiModelProperty(value = "不用传")
    private Integer lft;
    @ApiModelProperty(value = "不用传")
    private Integer rgt;
}

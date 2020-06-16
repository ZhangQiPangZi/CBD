package com.cbd.cbdcommoninterface.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
public class CompanyNameOrUserIDRequest implements Serializable {
    @ApiModelProperty(value = "公司名或者销售员工号")
    private String key;
}

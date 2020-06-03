package com.cbd.cbdcommoninterface.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
public class AddCpyRequest implements Serializable {
    @ApiModelProperty(value = "要添加的公司名")
    private String curCompanyName;
    @ApiModelProperty(value = "父公司名，可以不传，不传代表添加的为根公司")
    private String parentCompanyName;

    private String companyPhone;
    private String companyCode;
    private String companyMail;
    private Integer companyTypeID;
    private String companyProvince;
    private String companyCity;
    private String companyDistrict;
}

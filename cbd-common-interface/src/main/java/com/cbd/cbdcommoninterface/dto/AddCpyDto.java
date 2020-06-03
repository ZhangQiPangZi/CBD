package com.cbd.cbdcommoninterface.dto;

import lombok.Data;

@Data
public class AddCpyDto {
    private String companyID;
    private String companyName;
    private String companyPhone;
    private String companyCode;
    private String companyMail;
    private Integer companyTypeID;
    private String companyProvince;
    private String companyCity;
    private String companyDistrict;
    private Integer companylevel;
    private Integer lft;
    private Integer rgt;
}

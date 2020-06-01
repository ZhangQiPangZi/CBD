package com.cbd.cbdcommoninterface.pojo.company;

import lombok.Data;

import java.io.Serializable;

@Data
public class CompanyInfo implements Serializable {
    public enum Companylevel{
        /**
         * 区县4s销售处
         */
        DISCPY,
        /**
         * 市级子公司
         */
        CITYCPY,
        /**
         * 省级子公司
         */
        PROCPY,
        /**
         * 总公司
         */
        HEADCPY
    }

    private String companyID;
    private String companyName;
    private String companyPhone;
    private String companyCode;
    private String companyMail;
    private Integer companyTypeID;
    private Integer companyManagerID;
    private String companyProvince;
    private String companyCity;
    private String companyDistrict;
    private Integer companylevel;
    private Integer lft;
    private Integer rgt;


}

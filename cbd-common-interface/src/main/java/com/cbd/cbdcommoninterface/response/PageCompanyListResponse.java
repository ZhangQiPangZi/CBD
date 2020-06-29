package com.cbd.cbdcommoninterface.response;

import lombok.Data;

import java.io.Serializable;

@Data
public class PageCompanyListResponse implements Serializable {
    private String companyID;
    private String companyName;
    private String companyProvince;
    private String companyCity;
    private String companyDistrict;
    private Float achievement;
    private Integer contractCount;

}

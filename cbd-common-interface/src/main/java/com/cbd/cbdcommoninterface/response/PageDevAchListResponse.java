package com.cbd.cbdcommoninterface.response;

import java.io.Serializable;

public class PageDevAchListResponse implements Serializable {
    private String Date;
    private String salesNums;
    private String companyName;
    private String devName;

    public String getDate() {
        return Date;
    }

    public void setDate(String date) {
        Date = date;
    }

    public String getSalesNums() {
        return salesNums;
    }

    public void setSalesNums(String salesNums) {
        this.salesNums = salesNums;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getDevName() {
        return devName;
    }

    public void setDevName(String devName) {
        this.devName = devName;
    }
}

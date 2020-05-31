package com.cbd.cbdcommoninterface.pojo.achievement;

import java.io.Serializable;

public class AchievementDeviceInfo implements Serializable {
    private String devTypeID;
    private String companyID;
    private Integer month;
    private Integer year;
    private String salesNums;

    public String getDevTypeID() {
        return devTypeID;
    }

    public void setDevTypeID(String devTypeID) {
        this.devTypeID = devTypeID;
    }

    public String getCompanyID() {
        return companyID;
    }

    public void setCompanyID(String companyID) {
        this.companyID = companyID;
    }

    public Integer getMonth() {
        return month;
    }

    public void setMonth(Integer month) {
        this.month = month;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public String getSalesNums() {
        return salesNums;
    }

    public void setSalesNums(String salesNums) {
        this.salesNums = salesNums;
    }
}

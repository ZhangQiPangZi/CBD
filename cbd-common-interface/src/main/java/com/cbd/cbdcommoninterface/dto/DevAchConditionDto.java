package com.cbd.cbdcommoninterface.dto;

import java.io.Serializable;

public class DevAchConditionDto implements Serializable {
    private Integer year;
    private Integer month;
    private Integer devTypeID;
    private Integer lft;
    private Integer rgt;

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public Integer getMonth() {
        return month;
    }

    public void setMonth(Integer month) {
        this.month = month;
    }

    public Integer getDevTypeID() {
        return devTypeID;
    }

    public void setDevTypeID(Integer devTypeID) {
        this.devTypeID = devTypeID;
    }

    public Integer getLft() {
        return lft;
    }

    public void setLft(Integer lft) {
        this.lft = lft;
    }

    public Integer getRgt() {
        return rgt;
    }

    public void setRgt(Integer rgt) {
        this.rgt = rgt;
    }
}

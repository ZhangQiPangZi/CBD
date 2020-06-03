package com.cbd.cbdcommoninterface.request;

import java.io.Serializable;

public class SpecifiPersonRequest implements Serializable {
    private String Date;
    private Integer salersID;

    public String getDate() {
        return Date;
    }

    public void setDate(String date) {
        Date = date;
    }

    public Integer getSalersID() {
        return salersID;
    }

    public void setSalersID(Integer salersID) {
        this.salersID = salersID;
    }
}

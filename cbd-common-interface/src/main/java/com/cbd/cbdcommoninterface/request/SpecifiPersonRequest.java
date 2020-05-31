package com.cbd.cbdcommoninterface.request;

import java.io.Serializable;

public class SpecifiPersonRequest implements Serializable {
    private String Date;
    private String salersID;

    public String getDate() {
        return Date;
    }

    public void setDate(String date) {
        Date = date;
    }

    public String getSalersID() {
        return salersID;
    }

    public void setSalersID(String salersID) {
        this.salersID = salersID;
    }
}

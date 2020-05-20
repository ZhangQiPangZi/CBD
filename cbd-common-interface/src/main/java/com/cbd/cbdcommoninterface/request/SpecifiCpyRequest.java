package com.cbd.cbdcommoninterface.request;

import java.io.Serializable;

public class SpecifiCpyRequest implements Serializable {
    private String Date;
    private String companyName;

    public String getDate() {
        return Date;
    }

    public void setDate(String date) {
        Date = date;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }
}

package com.cbd.cbdcommoninterface.request;

import java.io.Serializable;

public class AllocationDevRequest implements Serializable {
    private String devID;
    private String companyName;

    public String getDevID() {
        return devID;
    }

    public void setDevID(String devID) {
        this.devID = devID;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }
}

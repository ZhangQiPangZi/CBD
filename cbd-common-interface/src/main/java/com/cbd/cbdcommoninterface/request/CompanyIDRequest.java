package com.cbd.cbdcommoninterface.request;

import java.io.Serializable;

public class CompanyIDRequest implements Serializable {
    private String companyID;

    public String getCompanyID() {
        return companyID;
    }

    public void setCompanyID(String companyID) {
        this.companyID = companyID;
    }
}

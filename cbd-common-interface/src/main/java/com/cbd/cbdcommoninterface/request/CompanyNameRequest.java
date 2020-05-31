package com.cbd.cbdcommoninterface.request;

import java.io.Serializable;

public class CompanyNameRequest implements Serializable {
    private String companyName;

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }
}

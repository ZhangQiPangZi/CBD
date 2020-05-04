package com.cbd.cbdcommoninterface.request;

import java.io.Serializable;

public class DistributeContractRequest implements Serializable {
    private String contractID;
    private String companyName;

    public String getContractID() {
        return contractID;
    }

    public void setContractID(String contractID) {
        this.contractID = contractID;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }
}

package com.cbd.cbdcommoninterface.request;

import java.io.Serializable;

public class AddContractDevMesRequest implements Serializable {
    private String companyID;
    private String devName;
    private Integer devNums;

    public String getCompanyID() {
        return companyID;
    }

    public void setCompanyID(String companyID) {
        this.companyID = companyID;
    }

    public String getDevName() {
        return devName;
    }

    public void setDevName(String devName) {
        this.devName = devName;
    }

    public Integer getDevNums() {
        return devNums;
    }

    public void setDevNums(Integer devNums) {
        this.devNums = devNums;
    }
}

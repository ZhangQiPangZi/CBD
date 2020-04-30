package com.cbd.cbdcommoninterface.response;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;

public class PageContractListResponse {
    private String contractID;
    private String contractCompanyName;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createTime;
    private String devName;

    public String getContractID() {
        return contractID;
    }

    public void setContractID(String contractID) {
        this.contractID = contractID;
    }

    public String getContractCompanyName() {
        return contractCompanyName;
    }

    public void setContractCompanyName(String contractCompanyName) {
        this.contractCompanyName = contractCompanyName;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getDevName() {
        return devName;
    }

    public void setDevName(String devName) {
        this.devName = devName;
    }
}

package com.cbd.cbdcommoninterface.response;

import java.io.Serializable;

public class UnpaidContractInfoResponse implements Serializable {
    private String contractTypeName;
    private String devName;
    private Integer devNums;
    private Float serverYears;
    private Float dellFee;
    private Float serverFee;

    public String getContractTypeName() {
        return contractTypeName;
    }

    public void setContractTypeName(String contractTypeName) {
        this.contractTypeName = contractTypeName;
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

    public Float getServerYears() {
        return serverYears;
    }

    public void setServerYears(Float serverYears) {
        this.serverYears = serverYears;
    }

    public Float getDellFee() {
        return dellFee;
    }

    public void setDellFee(Float dellFee) {
        this.dellFee = dellFee;
    }

    public Float getServerFee() {
        return serverFee;
    }

    public void setServerFee(Float serverFee) {
        this.serverFee = serverFee;
    }
}

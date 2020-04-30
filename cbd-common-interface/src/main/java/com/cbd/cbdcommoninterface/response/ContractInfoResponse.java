package com.cbd.cbdcommoninterface.response;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;

public class ContractInfoResponse {
    private String contractID;
    private String contractCompanyName;
    private String shopName;
    private String partyAPersonName;
    private String contractTypeName;
    private Float dellFee;
    private Float serverFee;
    private Float serverYears;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createTime;
    private String devName;
    private String devTypeName;
    private Integer devNums;
    private Integer contractStatus;

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

    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }

    public String getPartyAPersonName() {
        return partyAPersonName;
    }

    public void setPartyAPersonName(String partyAPersonName) {
        this.partyAPersonName = partyAPersonName;
    }

    public String getContractTypeName() {
        return contractTypeName;
    }

    public void setContractTypeName(String contractTypeName) {
        this.contractTypeName = contractTypeName;
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

    public Float getServerYears() {
        return serverYears;
    }

    public void setServerYears(Float serverYears) {
        this.serverYears = serverYears;
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

    public String getDevTypeName() {
        return devTypeName;
    }

    public void setDevTypeName(String devTypeName) {
        this.devTypeName = devTypeName;
    }

    public Integer getDevNums() {
        return devNums;
    }

    public void setDevNums(Integer devNums) {
        this.devNums = devNums;
    }

    public Integer getContractStatus() {
        return contractStatus;
    }

    public void setContractStatus(Integer contractStatus) {
        this.contractStatus = contractStatus;
    }
}

package com.cbd.cbdcommoninterface.pojo.contract;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.io.Serializable;
import java.util.Date;

public class ContractInfo implements Serializable {
    public enum ContractStatus{
        /**
         * 新建未支付
         */
        UNPAID,
        /**
         * 已支付生效中
         */
        PAID,
        /**
         * 合同已到期
         */
        EXPIRED
    }
    private String contractID;
    private String companyID;
    private String shopID;
    private String partyAPersonID;
    private Integer contractTypeID;
    private Float dellFee;
    private Float serverFee;
    private Float serverYears;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createTime;
    private Integer devTypeID;
    private Integer devNums;
    private Integer contractStatus;

    public String getContractID() {
        return contractID;
    }

    public void setContractID(String contractID) {
        this.contractID = contractID;
    }

    public String getCompanyID() {
        return companyID;
    }

    public void setCompanyID(String companyID) {
        this.companyID = companyID;
    }

    public String getShopID() {
        return shopID;
    }

    public void setShopID(String shopID) {
        this.shopID = shopID;
    }

    public String getPartyAPersonID() {
        return partyAPersonID;
    }

    public void setPartyAPersonID(String partyAPersonID) {
        this.partyAPersonID = partyAPersonID;
    }

    public Integer getContractTypeID() {
        return contractTypeID;
    }

    public void setContractTypeID(Integer contractTypeID) {
        this.contractTypeID = contractTypeID;
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

    public Integer getDevTypeID() {
        return devTypeID;
    }

    public void setDevTypeID(Integer devTypeID) {
        this.devTypeID = devTypeID;
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

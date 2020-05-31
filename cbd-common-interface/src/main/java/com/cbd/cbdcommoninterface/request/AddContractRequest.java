package com.cbd.cbdcommoninterface.request;

import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

public class AddContractRequest implements Serializable {
    private String companyID;
    private String userID;
    @ApiModelProperty(value = "合同类型名")
    private String contractTypeName;
    private String devName;
    @ApiModelProperty(value = "设备数量")
    private Integer devNums;
    @ApiModelProperty(value = "服务时长", example = "三种 0.5 1 2年")
    private Float serverYears;
    @ApiModelProperty(value = "单笔成交费用")
    private Float dellFee;
    @ApiModelProperty(value = "合同服务费")
    private Float serverFee;

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getContractTypeName() {
        return contractTypeName;
    }

    public void setContractTypeName(String contractTypeName) {
        this.contractTypeName = contractTypeName;
    }

    public String getDevName() {
        return devName;
    }

    public String getCompanyID() {
        return companyID;
    }

    public void setCompanyID(String companyID) {
        this.companyID = companyID;
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

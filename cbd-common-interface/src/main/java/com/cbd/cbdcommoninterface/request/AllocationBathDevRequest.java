package com.cbd.cbdcommoninterface.request;

import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

public class AllocationBathDevRequest implements Serializable {
    private String devName;
    private Integer devNums;
    @ApiModelProperty(value = "原公司ID")
    private String curCompanyID;
    @ApiModelProperty(value = "目标（调拨）公司名")
    private String dstCompanyName;

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

    public String getCurCompanyID() {
        return curCompanyID;
    }

    public void setCurCompanyID(String curCompanyID) {
        this.curCompanyID = curCompanyID;
    }

    public String getDstCompanyName() {
        return dstCompanyName;
    }

    public void setDstCompanyName(String dstCompanyName) {
        this.dstCompanyName = dstCompanyName;
    }
}

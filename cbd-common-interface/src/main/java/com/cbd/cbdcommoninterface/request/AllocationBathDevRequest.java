package com.cbd.cbdcommoninterface.request;

public class AllocationBathDevRequest {
    private String devName;
    private Integer devNums;
    private String curCompanyID;
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

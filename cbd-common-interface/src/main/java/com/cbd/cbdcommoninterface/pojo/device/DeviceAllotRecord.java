package com.cbd.cbdcommoninterface.pojo.device;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;

public class DeviceAllotRecord {
    private String devID;
    private String srcCompanyID;
    private String dstCompanyID;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date optTime;
    private String srcManagerID;
    private String dstManagerID;

    public String getDevID() {
        return devID;
    }

    public void setDevID(String devID) {
        this.devID = devID;
    }

    public String getSrcCompanyID() {
        return srcCompanyID;
    }

    public void setSrcCompanyID(String srcCompanyID) {
        this.srcCompanyID = srcCompanyID;
    }

    public String getDstCompanyID() {
        return dstCompanyID;
    }

    public void setDstCompanyID(String dstCompanyID) {
        this.dstCompanyID = dstCompanyID;
    }

    public Date getOptTime() {
        return optTime;
    }

    public void setOptTime(Date optTime) {
        this.optTime = optTime;
    }

    public String getSrcManagerID() {
        return srcManagerID;
    }

    public void setSrcManagerID(String srcManagerID) {
        this.srcManagerID = srcManagerID;
    }

    public String getDstManagerID() {
        return dstManagerID;
    }

    public void setDstManagerID(String dstManagerID) {
        this.dstManagerID = dstManagerID;
    }
}

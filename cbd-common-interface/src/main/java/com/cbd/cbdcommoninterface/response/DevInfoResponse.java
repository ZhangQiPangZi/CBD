package com.cbd.cbdcommoninterface.response;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;

public class DevInfoResponse {
    private String devID;
    private String devName;
    private String devStatus;
    private String devManagerName;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date devInputTime;
    private Integer devGateWayID;
    private String devCompanyName;
    private String devType;

    public String getDevID() {
        return devID;
    }

    public void setDevID(String devID) {
        this.devID = devID;
    }

    public String getDevName() {
        return devName;
    }

    public void setDevName(String devName) {
        this.devName = devName;
    }

    public String getDevStatus() {
        return devStatus;
    }

    public void setDevStatus(String devStatus) {
        this.devStatus = devStatus;
    }

    public String getDevManagerName() {
        return devManagerName;
    }

    public void setDevManagerName(String devManagerName) {
        this.devManagerName = devManagerName;
    }

    public Date getDevInputTime() {
        return devInputTime;
    }

    public void setDevInputTime(Date devInputTime) {
        this.devInputTime = devInputTime;
    }

    public Integer getDevGateWayID() {
        return devGateWayID;
    }

    public void setDevGateWayID(Integer devGateWayID) {
        this.devGateWayID = devGateWayID;
    }

    public String getDevCompanyName() {
        return devCompanyName;
    }

    public void setDevCompanyName(String devCompanyName) {
        this.devCompanyName = devCompanyName;
    }

    public String getDevType() {
        return devType;
    }

    public void setDevType(String devType) {
        this.devType = devType;
    }
}
package com.cbd.cbdcommoninterface.dto;

public class AllocationDevDto {
    private String devID;
    private String devManagerID;
    private Integer devStatus;

    public String getDevID() {
        return devID;
    }

    public void setDevID(String devID) {
        this.devID = devID;
    }

    public String getDevManagerID() {
        return devManagerID;
    }

    public void setDevManagerID(String devManagerID) {
        this.devManagerID = devManagerID;
    }

    public Integer getDevStatus() {
        return devStatus;
    }

    public void setDevStatus(Integer devStatus) {
        this.devStatus = devStatus;
    }
}

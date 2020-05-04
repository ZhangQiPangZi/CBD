package com.cbd.cbdcommoninterface.request;

import java.io.Serializable;

public class AddDeviceRequest implements Serializable {

    private String devName;
    private Integer devNums;
    private String managerID;
    private String companyID;
    private Integer gateWayID;

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

    public String getManagerID() {
        return managerID;
    }

    public void setManagerID(String managerID) {
        this.managerID = managerID;
    }

    public String getCompanyID() {
        return companyID;
    }

    public void setCompanyID(String companyID) {
        this.companyID = companyID;
    }

    public Integer getGateWayID() {
        return gateWayID;
    }

    public void setGateWayID(Integer gateWayID) {
        this.gateWayID = gateWayID;
    }
}

package com.cbd.cbdcommoninterface.request;

import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

public class AddDeviceRequest implements Serializable {

    private String devName;
    @ApiModelProperty(value = "设备数量")
    private Integer devNums;
    private String managerID;
    private String companyID;
    private String SIMName;
    private Integer SIMNums;
    @ApiModelProperty(value = "网关id")
    private Integer gateWayID;
    @ApiModelProperty(value = "设备类型")
    private String devType;
    @ApiModelProperty(value = "设备厂家公司名")
    private String devFactoryName;
    @ApiModelProperty(value = "设备厂家联系人名")
    private String devFactoryPersonName;
    @ApiModelProperty(value = "设备厂家联系人方式")
    private String devFactoryPersonPhone;


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

    public String getSIMName() {
        return SIMName;
    }

    public void setSIMName(String SIMName) {
        this.SIMName = SIMName;
    }

    public Integer getSIMNums() {
        return SIMNums;
    }

    public void setSIMNums(Integer SIMNums) {
        this.SIMNums = SIMNums;
    }

    public Integer getGateWayID() {
        return gateWayID;
    }

    public void setGateWayID(Integer gateWayID) {
        this.gateWayID = gateWayID;
    }

    public String getDevType() {
        return devType;
    }

    public void setDevType(String devType) {
        this.devType = devType;
    }

    public String getDevFactoryName() {
        return devFactoryName;
    }

    public void setDevFactoryName(String devFactoryName) {
        this.devFactoryName = devFactoryName;
    }

    public String getDevFactoryPersonName() {
        return devFactoryPersonName;
    }

    public void setDevFactoryPersonName(String devFactoryPersonName) {
        this.devFactoryPersonName = devFactoryPersonName;
    }

    public String getDevFactoryPersonPhone() {
        return devFactoryPersonPhone;
    }

    public void setDevFactoryPersonPhone(String devFactoryPersonPhone) {
        this.devFactoryPersonPhone = devFactoryPersonPhone;
    }
}

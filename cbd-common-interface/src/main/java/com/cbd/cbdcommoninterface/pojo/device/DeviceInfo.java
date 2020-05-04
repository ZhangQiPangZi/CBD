package com.cbd.cbdcommoninterface.pojo.device;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.io.Serializable;
import java.util.Date;

public class DeviceInfo implements Serializable {

    public enum DevStatus{
        /**
         * 库中
         */
        IN,
        /**
         * 出库
         */
        OUT,
        /**
         * 使用中
         */
        USE,
        /**
         * 待返厂维修
         */
        RETURN
    }
    private String devID;
    private Integer devTypeID;
    private Integer devStatus;
    /**
     * 库管人员ID
     */
    private String devManagerID;
    /**
     * 设备入库时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date devInputTime;
    /**
     * 网关ID
     */
    private Integer devGateWayID;
    /**
     * 设备现属公司ID
     */
    private String companyID;
    /**
     * 设备生产厂家ID
     */
    private String devFactoryID;

    /**
     * 设备对应SIM卡ID
     */
    private String SIMID;

    public String getDevID() {
        return devID;
    }

    public void setDevID(String devID) {
        this.devID = devID;
    }

    public Integer getDevTypeID() {
        return devTypeID;
    }

    public void setDevTypeID(Integer devTypeID) {
        this.devTypeID = devTypeID;
    }

    public Integer getDevStatus() {
        return devStatus;
    }

    public void setDevStatus(Integer devStatus) {
        this.devStatus = devStatus;
    }

    public String getDevManagerID() {
        return devManagerID;
    }

    public void setDevManagerID(String devManagerID) {
        this.devManagerID = devManagerID;
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

    public String getCompanyID() {
        return companyID;
    }

    public void setCompanyID(String companyID) {
        this.companyID = companyID;
    }

    public String getDevFactoryID() {
        return devFactoryID;
    }

    public void setDevFactoryID(String devFactoryID) {
        this.devFactoryID = devFactoryID;
    }

    public String getSIMID() {
        return SIMID;
    }

    public void setSIMID(String SIMID) {
        this.SIMID = SIMID;
    }
}

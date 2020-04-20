package com.cbd.cbdcommoninterface.pojo.device;

import java.io.Serializable;
import java.util.Date;

public class DeviceInfo implements Serializable {
    public enum DevType{
        /**
         * 有线
         */
        WIRED,
        /**
         * 无线
         */
        WIRLESS,
        /**
         * 行车记录仪
         */
        DVR,
        /**
         * SIM卡
         */
        SIMCARD
    }

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
    private Integer devType;
    private Integer devStatus;
    /**
     * 库管人员ID
     */
    private String devManagerID;
    /**
     * 设备入库时间
     */
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

    public String getDevID() {
        return devID;
    }

    public void setDevID(String devID) {
        this.devID = devID;
    }

    public Integer getDevType() {
        return devType;
    }

    public void setDevType(Integer devType) {
        this.devType = devType;
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
}

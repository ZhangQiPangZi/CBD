package com.cbd.cbdcommoninterface.response;

import java.io.Serializable;

public class DevMessageResponse implements Serializable {
    public enum MesType{
        /**
         * 返厂消息
         */
        RETURN,
        /**
         * 设备调拨消息
         */
        ALLOCATION,
        /**
         * 设备接收消息
         */
        ACCEPT,
        /**
         * 合同对应设备调拨消息
         */
        CONTRACT_ALLOCATION
    }
    private String mesID;
    private Integer mesType;
    private String devName;
    private Integer devNums;
    private String companyName;

    public String getMesID() {
        return mesID;
    }

    public void setMesID(String mesID) {
        this.mesID = mesID;
    }

    public Integer getMesType() {
        return mesType;
    }

    public void setMesType(Integer mesType) {
        this.mesType = mesType;
    }

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

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }
}

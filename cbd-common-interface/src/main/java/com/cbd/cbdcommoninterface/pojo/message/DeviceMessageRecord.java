package com.cbd.cbdcommoninterface.pojo.message;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.io.Serializable;
import java.util.Date;

public class DeviceMessageRecord implements Serializable {

    public enum MessageType{
        /**
         * 常规调拨消息
         */
        ALLOCATION,
        /**
         * 返厂消息
         */
        RETURN,
        /**
         * 合同对应设备调拨消息
         */
        CONTRACT_ALLOCATION
    }
    public enum MessageStatus{
        /**
         * 未确认
         */
        UNCONFIRMED,
        /**
         * 已确认
         */
        CONFIRMED
    }
    private String mesID;
    private Integer mesType;
    private String srcManagerID;
    private String dstManagerID;
    private Integer mesStatus;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date mesSendTime;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date mesAcceptTime;
    private String devName;
    private Integer devNums;

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

    public Integer getMesStatus() {
        return mesStatus;
    }

    public void setMesStatus(Integer mesStatus) {
        this.mesStatus = mesStatus;
    }

    public Date getMesSendTime() {
        return mesSendTime;
    }

    public void setMesSendTime(Date mesSendTime) {
        this.mesSendTime = mesSendTime;
    }

    public Date getMesAcceptTime() {
        return mesAcceptTime;
    }

    public void setMesAcceptTime(Date mesAcceptTime) {
        this.mesAcceptTime = mesAcceptTime;
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
}

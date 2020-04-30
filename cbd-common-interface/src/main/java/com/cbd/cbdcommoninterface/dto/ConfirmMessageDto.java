package com.cbd.cbdcommoninterface.dto;

import com.cbd.cbdcommoninterface.pojo.message.DeviceMessageRecord;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;

public class ConfirmMessageDto {
    private String mesID;
    private Integer mesStatus;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date mesAcceptTime;

    public String getMesID() {
        return mesID;
    }

    public void setMesID(String mesID) {
        this.mesID = mesID;
    }

    public Integer getMesStatus() {
        return mesStatus;
    }

    public void setMesStatus(Integer mesStatus) {
        this.mesStatus = mesStatus;
    }

    public Date getMesAcceptTime() {
        return mesAcceptTime;
    }

    public void setMesAcceptTime(Date mesAcceptTime) {
        this.mesAcceptTime = mesAcceptTime;
    }
}

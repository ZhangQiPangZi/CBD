package com.cbd.cbdcommoninterface.pojo.message;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
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
    private Integer srcManagerID;
    private Integer dstManagerID;
    private Integer mesStatus;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date mesSendTime;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date mesAcceptTime;
    private String devName;
    private Integer devNums;


}

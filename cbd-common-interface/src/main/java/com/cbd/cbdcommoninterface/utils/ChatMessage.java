package com.cbd.cbdcommoninterface.utils;


import com.cbd.cbdcommoninterface.enums.ReadFlagEnum;
import lombok.Data;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * WebSocket 聊天消息类
 */
@Data
public class ChatMessage implements Serializable {
    public enum MsgType{
        /**
         * 常规调拨消息
         */
        ALLOCATION,
        /**
         * 确认消息
         */
        ACCEPT,
        /**
         * 合同对应设备调拨消息
         */
        CONTRACT_ALLOCATION,
        /**
         * 返厂消息
         */
        RETURN,
        /**
         * 合同支付
         */
        PAY_CONTRACT,
        /**
         * 合同派发
         */
        DISTRIBUTE_CONTRACT,
        /**
         * 聊天消息
         */
        CHAT
    }

    private String mesID;
    /**
     * 消息类型
     */
    private String msgType = MsgType.ACCEPT.toString();
    private String senderId; //发送人
    private String receiverId; //接收人
    private String content; //发送消息
    //默认未读
    private Integer readFlag = ReadFlagEnum.UNREAD.ordinal();
    //时间戳
    private String timeStamp = new SimpleDateFormat("yyyyMMddHHmmSS").format(new Date());

}
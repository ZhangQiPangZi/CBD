package com.cbd.cbdcommoninterface.utils;


import lombok.Data;

import java.io.Serializable;

/**
 * WebSocket 聊天消息类
 */
@Data
public class ChatMessage implements Serializable {

    private String mesID;
    /**
     * 消息类型代表所发消息的格式，默认都是文本格式
     */
    private Integer msgType;
    private String senderId; //发送人
    private String receiverId; //接收人
    private String content; //发送消息

}
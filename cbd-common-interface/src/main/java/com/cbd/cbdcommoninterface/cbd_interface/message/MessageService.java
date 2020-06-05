package com.cbd.cbdcommoninterface.cbd_interface.message;

public interface MessageService {
    void saveMessage(String senderId, String receiverId,Integer msgType, String content, Integer readFlag);
}

package com.cbd.cbdcommoninterface.request;

import lombok.Data;

import java.io.Serializable;

@Data
public class ReceiverIDRequest implements Serializable {
    private String senderID;
    private String receiverID;
}

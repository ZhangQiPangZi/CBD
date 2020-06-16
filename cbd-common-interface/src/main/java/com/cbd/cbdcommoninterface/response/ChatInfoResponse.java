package com.cbd.cbdcommoninterface.response;

import lombok.Data;

import java.io.Serializable;

@Data
public class ChatInfoResponse implements Serializable {
    private String senderID;
    private String customerName;
    private Integer unReadCounts;
}

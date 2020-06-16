package com.cbd.cbdcommoninterface.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class ChatInfoDto implements Serializable {
    private String senderID;
    private Integer unReadCounts;
}

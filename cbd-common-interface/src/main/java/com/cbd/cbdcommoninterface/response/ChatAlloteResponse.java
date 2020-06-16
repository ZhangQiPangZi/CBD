package com.cbd.cbdcommoninterface.response;

import lombok.Data;

import java.io.Serializable;

@Data
public class ChatAlloteResponse implements Serializable {
    private String serverID;
    private String serverName;
}

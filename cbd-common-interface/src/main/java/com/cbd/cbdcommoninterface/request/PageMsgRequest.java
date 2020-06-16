package com.cbd.cbdcommoninterface.request;

import lombok.Data;

import java.io.Serializable;

@Data
public class PageMsgRequest implements Serializable {
    private PageRequest pageRequest;
    private String receiverID;
}

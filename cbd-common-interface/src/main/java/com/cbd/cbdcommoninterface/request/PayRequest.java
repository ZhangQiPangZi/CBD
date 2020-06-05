package com.cbd.cbdcommoninterface.request;

import lombok.Data;

import java.io.Serializable;

@Data
public class PayRequest implements Serializable {
    private String orderID;
    private String orderSubject;
    private String orderPrice;
    private Integer orderType;
    private Float renewYears;
}

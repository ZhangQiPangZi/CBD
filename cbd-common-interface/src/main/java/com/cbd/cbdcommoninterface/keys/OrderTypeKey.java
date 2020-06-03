package com.cbd.cbdcommoninterface.keys;

public class OrderTypeKey extends BasePrefix{
    public OrderTypeKey(int expireSeconds, String prefix) {
        super(expireSeconds, prefix);
    }

    public static OrderTypeKey ORDER_TYPE = new OrderTypeKey(60*60*24, "OTYPE");
}

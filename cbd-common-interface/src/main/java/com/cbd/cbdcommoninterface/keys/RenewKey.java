package com.cbd.cbdcommoninterface.keys;

public class RenewKey extends BasePrefix{
    public RenewKey(int expireSeconds, String prefix) {
        super(expireSeconds, prefix);
    }

    public static RenewKey RENEW_TIME = new RenewKey(60*60*24, "renew");
}

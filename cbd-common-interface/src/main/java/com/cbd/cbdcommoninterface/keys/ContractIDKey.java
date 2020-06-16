package com.cbd.cbdcommoninterface.keys;

public class ContractIDKey extends BasePrefix{

    public ContractIDKey(int expireSeconds, String prefix) {
        super(expireSeconds, prefix);
    }

    public static ContractIDKey CONTRACT_ID= new ContractIDKey(60*60*24, "cIDK");
}

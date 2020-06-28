package com.cbd.cbdcommoninterface.keys;

public class ContractAcceptKey extends BasePrefix{

    public ContractAcceptKey(int expireSeconds, String prefix) {
        super(expireSeconds, prefix);
    }

    public static ContractAcceptKey CONTRACT_ACCEPT = new ContractAcceptKey(60*60*24, "CAA");
}

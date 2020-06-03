package com.cbd.cbdcommoninterface.request;

import java.io.Serializable;

public class ContractIDRequest implements Serializable {
    private String contractID;

    public String getContractID() {
        return contractID;
    }

    public void setContractID(String contractID) {
        this.contractID = contractID;
    }
}

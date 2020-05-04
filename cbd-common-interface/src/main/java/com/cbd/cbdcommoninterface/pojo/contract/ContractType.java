package com.cbd.cbdcommoninterface.pojo.contract;

import java.io.Serializable;

public class ContractType implements Serializable {
    private Integer contractTypeID;
    private String contractTypeName;

    public Integer getContractTypeID() {
        return contractTypeID;
    }

    public void setContractTypeID(Integer contractTypeID) {
        this.contractTypeID = contractTypeID;
    }

    public String getContractTypeName() {
        return contractTypeName;
    }

    public void setContractTypeName(String contractTypeName) {
        this.contractTypeName = contractTypeName;
    }
}

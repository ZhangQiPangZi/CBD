package com.cbd.cbdcommoninterface.request;

import lombok.Data;

import java.io.Serializable;

@Data
public class DistributeContractRequest implements Serializable {

    private String doAllocationCompanyID;
    private String contractID;
    private String companyName;
}

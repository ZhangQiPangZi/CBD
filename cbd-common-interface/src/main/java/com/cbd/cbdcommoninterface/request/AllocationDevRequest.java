package com.cbd.cbdcommoninterface.request;

import lombok.Data;

import java.io.Serializable;

@Data
public class AllocationDevRequest implements Serializable {
    private String doAllocationCompanyID;
    private String devID;
    private String companyName;

}

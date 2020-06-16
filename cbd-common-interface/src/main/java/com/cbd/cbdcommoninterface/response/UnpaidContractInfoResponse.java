package com.cbd.cbdcommoninterface.response;

import lombok.Data;

import java.io.Serializable;

@Data
public class UnpaidContractInfoResponse implements Serializable {
    private String contractID;
    private String companyName;
    private String contractTypeName;
    private String devName;
    private String devTypeName;
    private Integer devNums;
    private Float serverYears;
    private Float dellFee;
    private Float serverFee;

}

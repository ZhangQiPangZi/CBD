package com.cbd.cbdcommoninterface.response;

import lombok.Data;

import java.io.Serializable;

@Data
public class ContractInfoResponse implements Serializable {
    private String contractID;
    private String contractCompanyName;
    private String shopName="";
    private String shopAddress="";
    private String partyAPersonName;
    private String contractTypeName;
    private Float dellFee;
    private Float serverFee;
    private Float serverYears;
    private String expireDate;
    private String devName;
    private String devTypeName;
    private Integer devNums;
    private String contractStatus;
}

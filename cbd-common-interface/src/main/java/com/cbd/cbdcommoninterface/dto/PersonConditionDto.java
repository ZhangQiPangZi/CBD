package com.cbd.cbdcommoninterface.dto;

public class PersonConditionDto {
    private String salersID;
    private Integer year;
    private Integer month;
    private String contractTypeName;

    public String getSalersID() {
        return salersID;
    }

    public void setSalersID(String salersID) {
        this.salersID = salersID;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public Integer getMonth() {
        return month;
    }

    public void setMonth(Integer month) {
        this.month = month;
    }

    public String getContractTypeName() {
        return contractTypeName;
    }

    public void setContractTypeName(String contractTypeName) {
        this.contractTypeName = contractTypeName;
    }
}

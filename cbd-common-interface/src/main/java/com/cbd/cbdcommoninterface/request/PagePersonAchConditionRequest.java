package com.cbd.cbdcommoninterface.request;

import java.io.Serializable;

public class PagePersonAchConditionRequest implements Serializable {
    private PageRequest pageRequest;
    private String salersID;
    private Integer year;
    private Integer month;
    private String contractTypeName;

    public PageRequest getPageRequest() {
        return pageRequest;
    }

    public void setPageRequest(PageRequest pageRequest) {
        this.pageRequest = pageRequest;
    }

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

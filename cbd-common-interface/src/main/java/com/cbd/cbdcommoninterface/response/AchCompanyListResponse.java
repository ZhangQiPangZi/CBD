package com.cbd.cbdcommoninterface.response;

import java.io.Serializable;

public class AchCompanyListResponse implements Serializable {
    private String companyName;
    private Integer salersNums;
    private Integer contractNums;
    private Float achievement;

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public Integer getSalersNums() {
        return salersNums;
    }

    public void setSalersNums(Integer salersNums) {
        this.salersNums = salersNums;
    }

    public Integer getContractNums() {
        return contractNums;
    }

    public void setContractNums(Integer contractNums) {
        this.contractNums = contractNums;
    }

    public Float getAchievement() {
        return achievement;
    }

    public void setAchievement(Float achievement) {
        this.achievement = achievement;
    }
}

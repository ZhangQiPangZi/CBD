package com.cbd.cbdcommoninterface.response;

import java.io.Serializable;

public class PagePersonListResponse implements Serializable {
    private String Date;
    private Float achievement;
    private Integer contractCount;

    public String getDate() {
        return Date;
    }

    public void setDate(String date) {
        Date = date;
    }

    public Float getAchievement() {
        return achievement;
    }

    public void setAchievement(Float achievement) {
        this.achievement = achievement;
    }

    public Integer getContractCount() {
        return contractCount;
    }

    public void setContractCount(Integer contractCount) {
        this.contractCount = contractCount;
    }
}

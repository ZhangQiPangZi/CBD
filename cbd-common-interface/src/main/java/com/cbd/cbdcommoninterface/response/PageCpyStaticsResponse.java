package com.cbd.cbdcommoninterface.response;

import java.io.Serializable;

public class PageCpyStaticsResponse implements Serializable {
    private String companyName;
    private Integer salersCount;
    private Integer contractCount;
    private float achievementCount;

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public Integer getSalersCount() {
        return salersCount;
    }

    public void setSalersCount(Integer salersCount) {
        this.salersCount = salersCount;
    }

    public Integer getContractCount() {
        return contractCount;
    }

    public void setContractCount(Integer contractCount) {
        this.contractCount = contractCount;
    }

    public float getAchievementCount() {
        return achievementCount;
    }

    public void setAchievementCount(float achievementCount) {
        this.achievementCount = achievementCount;
    }
}

package com.cbd.cbdcommoninterface.pojo.achievement;

import java.io.Serializable;

public class AchievementCompanyInfo implements Serializable {
    private String companyID;
    private Integer salersCount;
    private Integer contractCount;
    private float achievementCount;


    public String getCompanyID() {
        return companyID;
    }

    public void setCompanyID(String companyID) {
        this.companyID = companyID;
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

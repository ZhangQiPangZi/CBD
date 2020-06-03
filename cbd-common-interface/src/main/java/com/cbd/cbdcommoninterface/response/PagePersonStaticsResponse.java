package com.cbd.cbdcommoninterface.response;

import java.io.Serializable;

public class PagePersonStaticsResponse implements Serializable {
    private String salersID;
    private String salersName;
    private String salersSexy;
    private String companyName;
    private Integer achievementCount;
    private Integer contractCount;

    public String getSalersID() {
        return salersID;
    }

    public void setSalersID(String salersID) {
        this.salersID = salersID;
    }

    public String getSalersName() {
        return salersName;
    }

    public void setSalersName(String salersName) {
        this.salersName = salersName;
    }

    public String getSalersSexy() {
        return salersSexy;
    }

    public void setSalersSexy(String salersSexy) {
        this.salersSexy = salersSexy;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public Integer getAchievementCount() {
        return achievementCount;
    }

    public void setAchievementCount(Integer achievementCount) {
        this.achievementCount = achievementCount;
    }

    public Integer getContractCount() {
        return contractCount;
    }

    public void setContractCount(Integer contractCount) {
        this.contractCount = contractCount;
    }
}

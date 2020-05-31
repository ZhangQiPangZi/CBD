package com.cbd.cbdcommoninterface.dto;

import java.io.Serializable;

public class CpyConditionDto implements Serializable {
    private Integer companylevel;
    private String companyProvince;
    private String companyCity;
    private String companyDistrict;
    private Integer sortMoney;
    private Integer sortContract;
    private Integer lft;
    private Integer rgt;

    public Integer getCompanylevel() {
        return companylevel;
    }

    public void setCompanylevel(Integer companylevel) {
        this.companylevel = companylevel;
    }

    public String getCompanyProvince() {
        return companyProvince;
    }

    public void setCompanyProvince(String companyProvince) {
        this.companyProvince = companyProvince;
    }

    public String getCompanyCity() {
        return companyCity;
    }

    public void setCompanyCity(String companyCity) {
        this.companyCity = companyCity;
    }

    public String getCompanyDistrict() {
        return companyDistrict;
    }

    public void setCompanyDistrict(String companyDistrict) {
        this.companyDistrict = companyDistrict;
    }

    public Integer getSortMoney() {
        return sortMoney;
    }

    public void setSortMoney(Integer sortMoney) {
        this.sortMoney = sortMoney;
    }

    public Integer getSortContract() {
        return sortContract;
    }

    public void setSortContract(Integer sortContract) {
        this.sortContract = sortContract;
    }

    public Integer getLft() {
        return lft;
    }

    public void setLft(Integer lft) {
        this.lft = lft;
    }

    public Integer getRgt() {
        return rgt;
    }

    public void setRgt(Integer rgt) {
        this.rgt = rgt;
    }
}

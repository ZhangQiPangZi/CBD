package com.cbd.cbdcommoninterface.dto;

public class DevConditionDto {
    public enum timeSort{
        /**
         * 按入库时间降序排列
         */
        DESC,
        /**
         * 按入库时间升序排列
         */
        ASC
    }
    private String companyID;
    private String devFactoryName;
    private Integer devStatus;
    private Integer companylevel;
    private String companyProvince;
    private String companyCity;
    private String companyDistrict;
    private Integer timeSort;
    private Integer lft;
    private Integer rgt;

    public String getCompanyID() {
        return companyID;
    }

    public void setCompanyID(String companyID) {
        this.companyID = companyID;
    }

    public String getDevFactoryName() {
        return devFactoryName;
    }

    public void setDevFactoryName(String devFactoryName) {
        this.devFactoryName = devFactoryName;
    }

    public Integer getDevStatus() {
        return devStatus;
    }

    public void setDevStatus(Integer devStatus) {
        this.devStatus = devStatus;
    }

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

    public Integer getTimeSort() {
        return timeSort;
    }

    public void setTimeSort(Integer timeSort) {
        this.timeSort = timeSort;
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

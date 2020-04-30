package com.cbd.cbdcommoninterface.pojo.company;

public class CompanyInfo {
    public enum Companylevel{
        /**
         * 4s销售处
         */
        SALES,
        /**
         * 区县子公司
         */
        DISCPY,
        /**
         * 市级子公司
         */
        CITYCPY,
        /**
         * 省级子公司
         */
        PROCPY
    }
    private String companyID;
    private String companyName;
    private String companyPhone;
    private String companyCode;
    private String companyMail;
    private Integer companyTypeID;
    private String companyManagerID;
    private String companyProvince;
    private String companyCity;
    private String companyDistrict;
    private Integer companylevel;
    private Integer lft;
    private Integer rgt;

    public String getCompanyID() {
        return companyID;
    }

    public void setCompanyID(String companyID) {
        this.companyID = companyID;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getCompanyPhone() {
        return companyPhone;
    }

    public void setCompanyPhone(String companyPhone) {
        this.companyPhone = companyPhone;
    }

    public String getCompanyCode() {
        return companyCode;
    }

    public void setCompanyCode(String companyCode) {
        this.companyCode = companyCode;
    }

    public String getCompanyMail() {
        return companyMail;
    }

    public void setCompanyMail(String companyMail) {
        this.companyMail = companyMail;
    }

    public Integer getCompanyTypeID() {
        return companyTypeID;
    }

    public void setCompanyTypeID(Integer companyTypeID) {
        this.companyTypeID = companyTypeID;
    }

    public String getCompanyManagerID() {
        return companyManagerID;
    }

    public void setCompanyManagerID(String companyManagerID) {
        this.companyManagerID = companyManagerID;
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

    public Integer getCompanylevel() {
        return companylevel;
    }

    public void setCompanylevel(Integer companylevel) {
        this.companylevel = companylevel;
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

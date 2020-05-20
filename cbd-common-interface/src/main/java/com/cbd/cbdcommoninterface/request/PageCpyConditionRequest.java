package com.cbd.cbdcommoninterface.request;

import java.io.Serializable;

public class PageCpyConditionRequest implements Serializable {
    public enum sortMoney{
        /**
         * 按销售额降序排列
         */
        DESC,
        /**
         * 按销售额升序排列
         */
        ASC
    }

    public enum sortContract{
        /**
         * 按合同数量降序排列
         */
        DESC,
        /**
         * 按合同数量升序排列
         */
        ASC
    }

    private PageRequest pageRequest;
    private String companyID;
    private Integer companylevel;
    private String companyProvince;
    private String companyCity;
    private String companyDistrict;
    private Integer sortMoney;
    private Integer sortContract;

    public PageRequest getPageRequest() {
        return pageRequest;
    }

    public void setPageRequest(PageRequest pageRequest) {
        this.pageRequest = pageRequest;
    }

    public String getCompanyID() {
        return companyID;
    }

    public void setCompanyID(String companyID) {
        this.companyID = companyID;
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
}

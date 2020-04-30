package com.cbd.cbdcommoninterface.request;

public class PageCpyAchConditionRequest {
    public enum sortType{
        /**
         * 根据销售总金额排序
         */
        MONEY,
        /**
         * 根据完成的合同数量排序
         */
        CONTRACT
    }
    private Integer companylevel;
    private String companyProvince;
    private String companyCity;
    private String companyDistrict;
    private Integer sortType;

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

    public Integer getSortType() {
        return sortType;
    }

    public void setSortType(Integer sortType) {
        this.sortType = sortType;
    }
}

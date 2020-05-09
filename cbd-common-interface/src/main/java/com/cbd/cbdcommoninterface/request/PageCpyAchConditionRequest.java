package com.cbd.cbdcommoninterface.request;

import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

public class PageCpyAchConditionRequest implements Serializable {
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

    private String companyID;
    @ApiModelProperty(value = "公司级别", example = "0 区县4s销售处 1 市级子公司 2 省级子公司 3 总公司")
    private Integer companylevel;
    @ApiModelProperty(value = "公司所在省名")
    private String companyProvince;
    @ApiModelProperty(value = "公司所在市名")
    private String companyCity;
    @ApiModelProperty(value = "公司所在区县名")
    private String companyDistrict;
    @ApiModelProperty(value = "销售金额排序", example = "0 按销售额降序排列 1 升序")
    private Integer sortMoney;

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
}

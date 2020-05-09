package com.cbd.cbdcommoninterface.request;

import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

public class PageDevConditionRequest implements Serializable {
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
    private PageRequest pageRequest;
    private String companyID;
    @ApiModelProperty(value = "设备厂家名")
    private String devFactoryName;
    @ApiModelProperty(value = "设备状态", example = "0 库中 1 出库 2 使用中 3 待返厂维修")
    private Integer devStatus;
    @ApiModelProperty(value = "公司级别", example = "0 区县4s销售处 1 市级子公司 2 省级子公司 3 总公司")
    private Integer companylevel;
    @ApiModelProperty(value = "公司所在省名")
    private String companyProvince;
    @ApiModelProperty(value = "公司所在市名")
    private String companyCity;
    @ApiModelProperty(value = "公司所在区县名")
    private String companyDistrict;
    @ApiModelProperty(value = "时间排序", example = "0 按入库时间降序排列 1 升序")
    private Integer timeSort;

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
}

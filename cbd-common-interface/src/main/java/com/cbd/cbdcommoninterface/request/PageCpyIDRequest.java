package com.cbd.cbdcommoninterface.request;

public class PageCpyIDRequest {
    private PageRequest pageRequest;
    private String CompanyID;

    public PageRequest getPageRequest() {
        return pageRequest;
    }

    public void setPageRequest(PageRequest pageRequest) {
        this.pageRequest = pageRequest;
    }

    public String getCompanyID() {
        return CompanyID;
    }

    public void setCompanyID(String companyID) {
        CompanyID = companyID;
    }
}

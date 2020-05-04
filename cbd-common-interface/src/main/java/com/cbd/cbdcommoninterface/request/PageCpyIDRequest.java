package com.cbd.cbdcommoninterface.request;

import java.io.Serializable;

public class PageCpyIDRequest implements Serializable {
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

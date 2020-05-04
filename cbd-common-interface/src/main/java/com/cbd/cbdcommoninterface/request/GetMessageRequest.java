package com.cbd.cbdcommoninterface.request;

import java.io.Serializable;

public class GetMessageRequest implements Serializable {
    private PageRequest pageRequest;
    private String managerID;
    private Integer mesStatus;

    public PageRequest getPageRequest() {
        return pageRequest;
    }

    public void setPageRequest(PageRequest pageRequest) {
        this.pageRequest = pageRequest;
    }

    public String getManagerID() {
        return managerID;
    }

    public void setManagerID(String managerID) {
        this.managerID = managerID;
    }

    public Integer getMesStatus() {
        return mesStatus;
    }

    public void setMesStatus(Integer mesStatus) {
        this.mesStatus = mesStatus;
    }
}

package com.cbd.cbdcommoninterface.response;

import java.io.Serializable;

public class DevNameNumsResponse implements Serializable {
    private String devName;
    private Integer devNums;

    public String getDevName() {
        return devName;
    }

    public void setDevName(String devName) {
        this.devName = devName;
    }

    public Integer getDevNums() {
        return devNums;
    }

    public void setDevNums(Integer devNums) {
        this.devNums = devNums;
    }
}

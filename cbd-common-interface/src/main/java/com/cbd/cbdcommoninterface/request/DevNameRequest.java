package com.cbd.cbdcommoninterface.request;

import java.io.Serializable;

public class DevNameRequest implements Serializable {
    private String devName;

    public String getDevName() {
        return devName;
    }

    public void setDevName(String devName) {
        this.devName = devName;
    }
}

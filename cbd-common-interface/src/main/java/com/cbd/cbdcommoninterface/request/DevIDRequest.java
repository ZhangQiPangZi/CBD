package com.cbd.cbdcommoninterface.request;

import java.io.Serializable;

public class DevIDRequest implements Serializable {
    private String devID;

    public String getDevID() {
        return devID;
    }

    public void setDevID(String devID) {
        this.devID = devID;
    }
}

package com.cbd.cbdcommoninterface.pojo.device;

import java.io.Serializable;

public class DevType implements Serializable {
    private Integer devTypeID;
    private String devName;
    private String devType;
    private String devFactoryID;

    public Integer getDevTypeID() {
        return devTypeID;
    }

    public void setDevTypeID(Integer devTypeID) {
        this.devTypeID = devTypeID;
    }

    public String getDevName() {
        return devName;
    }

    public void setDevName(String devName) {
        this.devName = devName;
    }

    public String getDevType() {
        return devType;
    }

    public void setDevType(String devType) {
        this.devType = devType;
    }

    public String getDevFactoryID() {
        return devFactoryID;
    }

    public void setDevFactoryID(String devFactoryID) {
        this.devFactoryID = devFactoryID;
    }
}

package com.cbd.cbdcommoninterface.response;

import java.io.Serializable;

public class DevReturnResponse implements Serializable {
    private String devFactoryPersonName;
    private String devFactoryPersonPhone;

    public String getDevFactoryPersonName() {
        return devFactoryPersonName;
    }

    public void setDevFactoryPersonName(String devFactoryPersonName) {
        this.devFactoryPersonName = devFactoryPersonName;
    }

    public String getDevFactoryPersonPhone() {
        return devFactoryPersonPhone;
    }

    public void setDevFactoryPersonPhone(String devFactoryPersonPhone) {
        this.devFactoryPersonPhone = devFactoryPersonPhone;
    }
}

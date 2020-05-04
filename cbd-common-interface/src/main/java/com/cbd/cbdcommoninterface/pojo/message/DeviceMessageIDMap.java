package com.cbd.cbdcommoninterface.pojo.message;

import java.io.Serializable;

public class DeviceMessageIDMap implements Serializable {
    private String mesID;
    private String devID;

    public String getMesID() {
        return mesID;
    }

    public void setMesID(String mesID) {
        this.mesID = mesID;
    }

    public String getDevID() {
        return devID;
    }

    public void setDevID(String devID) {
        this.devID = devID;
    }
}

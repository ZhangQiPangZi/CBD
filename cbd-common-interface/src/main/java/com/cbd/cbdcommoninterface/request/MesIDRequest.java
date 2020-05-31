package com.cbd.cbdcommoninterface.request;

import java.io.Serializable;

public class MesIDRequest implements Serializable {
    private String mesID;

    public String getMesID() {
        return mesID;
    }

    public void setMesID(String mesID) {
        this.mesID = mesID;
    }
}

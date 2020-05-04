package com.cbd.cbdcommoninterface.pojo.device;

import java.io.Serializable;

public class SIMInfo implements Serializable {
    public enum SIMStatus{
        /**
         * 库中
         */
        IN,
        /**
         * 出库
         */
        OUT,
        /**
         * 使用中
         */
        USE,
        /**
         * 待返厂维修
         */
        RETURN
    }

    private String SIMID;
    private Integer SIMStatus;

    public String getSIMID() {
        return SIMID;
    }

    public void setSIMID(String SIMID) {
        this.SIMID = SIMID;
    }

    public Integer getSIMStatus() {
        return SIMStatus;
    }

    public void setSIMStatus(Integer SIMStatus) {
        this.SIMStatus = SIMStatus;
    }
}

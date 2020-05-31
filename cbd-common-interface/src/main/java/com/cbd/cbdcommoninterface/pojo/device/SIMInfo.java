package com.cbd.cbdcommoninterface.pojo.device;

import java.io.Serializable;

public class SIMInfo implements Serializable {
    public enum SIMStatus{
        /**
         * 库中,已绑定设备
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
        RETURN,
        /**
         * 未配对
         */
        UNPAIR
    }

    private String SIMID;
    private Integer SIMStatus;
    private String SIMName;

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

    public String getSIMName() {
        return SIMName;
    }

    public void setSIMName(String SIMName) {
        this.SIMName = SIMName;
    }
}

package com.cbd.cbdcommoninterface.pojo.device;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class DeviceInfo implements Serializable {

    public enum DevStatus{
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
    private String devID;
    private Integer devTypeID;
    private Integer devStatus;
    /**
     * 库管人员ID
     */
    private Integer devManagerID;
    /**
     * 设备入库时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date devInputTime;
    /**
     * 网关ID
     */
    private Integer devGateWayID;
    /**
     * 设备现属公司ID
     */
    private String companyID;
    /**
     * 设备生产厂家ID
     */
    private String devFactoryID;

    /**
     * 设备对应SIM卡ID
     */
    private String SIMID;


}

package com.cbd.cbdcommoninterface.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class DevInfoResponse implements Serializable {
    private String devID;
    private String SIMID;
    private String devName;
    private String devStatus;
    private String devManagerName;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date devInputTime;
    private Integer devGateWayID;
    private String devCompanyName;
    private String devType;
}
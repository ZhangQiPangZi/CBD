package com.cbd.cbdcommoninterface.pojo.device;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class DeviceAllotRecord implements Serializable {
    private String devID;
    private String srcCompanyID;
    private String dstCompanyID;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date optTime;
    private Integer srcManagerID;
    private Integer dstManagerID;


}

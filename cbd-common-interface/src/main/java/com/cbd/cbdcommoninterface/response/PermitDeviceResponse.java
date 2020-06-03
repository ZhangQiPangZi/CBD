package com.cbd.cbdcommoninterface.response;

import lombok.Data;

import java.io.Serializable;

@Data
public class PermitDeviceResponse implements Serializable {
    private Boolean flag;
    private String devStatus;
}

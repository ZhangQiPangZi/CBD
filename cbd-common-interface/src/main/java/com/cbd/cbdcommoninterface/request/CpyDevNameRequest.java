package com.cbd.cbdcommoninterface.request;

import lombok.Data;

import java.io.Serializable;

@Data
public class CpyDevNameRequest implements Serializable {
    private String companyID;
    private String devName;
}

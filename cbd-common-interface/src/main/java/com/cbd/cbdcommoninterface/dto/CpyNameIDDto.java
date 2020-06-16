package com.cbd.cbdcommoninterface.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class CpyNameIDDto implements Serializable {
    private String companyName;
    private String companyID;
}

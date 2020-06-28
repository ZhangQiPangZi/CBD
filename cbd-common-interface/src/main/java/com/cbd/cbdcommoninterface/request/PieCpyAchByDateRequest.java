package com.cbd.cbdcommoninterface.request;

import lombok.Data;

import java.io.Serializable;

@Data
public class PieCpyAchByDateRequest implements Serializable {
    private String companyID;
    private Integer year;
    private Integer month;
}

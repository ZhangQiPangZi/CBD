package com.cbd.cbdcommoninterface.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class DevAchConditionDto implements Serializable {
    private Integer year;
    private Integer month;
    private Integer devTypeID;
    private String devType;
    private Integer lft;
    private Integer rgt;

}

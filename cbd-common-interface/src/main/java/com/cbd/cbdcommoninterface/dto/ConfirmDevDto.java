package com.cbd.cbdcommoninterface.dto;

import lombok.Data;

@Data
public class ConfirmDevDto {
    private String devID;
    private Integer devManagerID;
    private Integer devStatus;
    private String companyID;
}

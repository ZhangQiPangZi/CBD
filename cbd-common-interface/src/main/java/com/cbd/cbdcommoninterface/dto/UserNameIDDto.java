package com.cbd.cbdcommoninterface.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class UserNameIDDto implements Serializable {
    private Integer ID;
    private String username;
}

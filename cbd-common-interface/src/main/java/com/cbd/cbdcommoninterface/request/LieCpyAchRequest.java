package com.cbd.cbdcommoninterface.request;

import lombok.Data;

import java.io.Serializable;

@Data
public class LieCpyAchRequest implements Serializable {
    private String companyName;
    private Integer year;
}

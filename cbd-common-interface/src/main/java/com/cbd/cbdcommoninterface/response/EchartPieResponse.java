package com.cbd.cbdcommoninterface.response;

import lombok.Data;

import java.io.Serializable;

@Data
public class EchartPieResponse implements Serializable {
    private Float value;
    private String name;
}

package com.cbd.cbdcommoninterface.response;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class EchartDataResponse implements Serializable {
    private String name;
    private String type = "line";
    private String stack = "same";
    private Float[] data = new Float[12];
}

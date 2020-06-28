package com.cbd.cbdcommoninterface.response;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class EchartLieResponse implements Serializable {
    private String title;
    private List<String> legend;
    private List<EchartDataResponse> echartDataResponseList;
}

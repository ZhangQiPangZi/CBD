package com.cbd.cbdcommoninterface.response;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class EchartPieResultResponse implements Serializable {
    private List<String> legend;
    private List<EchartPieResponse> echartPieResponseList;
}

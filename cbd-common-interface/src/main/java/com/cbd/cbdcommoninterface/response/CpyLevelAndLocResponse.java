package com.cbd.cbdcommoninterface.response;

import lombok.Data;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

@Data
public class CpyLevelAndLocResponse implements Serializable {
    public enum Companylevel{
        /**
         * 区县4s销售处
         */
        DISCPY,
        /**
         * 市级子公司
         */
        CITYCPY,
        /**
         * 省级子公司
         */
        PROCPY,
        /**
         * 总公司
         */
        HEADCPY
    }
    private Integer companyLevel;
    private List<LocListResponse> locListResponseList;
}

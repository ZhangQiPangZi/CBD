package com.cbd.cbdcommoninterface.response;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

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
    private Map<String, Map<String, List<String>>> loc;

    public Integer getCompanyLevel() {
        return companyLevel;
    }

    public void setCompanyLevel(Integer companyLevel) {
        this.companyLevel = companyLevel;
    }

    public Map<String, Map<String, List<String>>> getLoc() {
        return loc;
    }

    public void setLoc(Map<String, Map<String, List<String>>> loc) {
        this.loc = loc;
    }
}

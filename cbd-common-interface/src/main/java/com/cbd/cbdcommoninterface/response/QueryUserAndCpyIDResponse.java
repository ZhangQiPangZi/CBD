package com.cbd.cbdcommoninterface.response;

import lombok.Data;

import java.io.Serializable;

@Data
public class QueryUserAndCpyIDResponse implements Serializable {
    /**
     * 这条结果的类型，公司或者销售员
     */
    private String queryType;
    /**
     * 对应结果的ID，公司ID或者是销售员ID
     */
    private String resultID;
    /**
     * 公司名或者员工姓名
     */
    private String resultName;
}

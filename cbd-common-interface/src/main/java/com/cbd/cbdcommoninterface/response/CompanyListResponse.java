package com.cbd.cbdcommoninterface.response;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class CompanyListResponse implements Serializable {
    //值
    private String id;

    //名称
    private String label;

    //子list
    private List<CompanyListResponse> children;
}

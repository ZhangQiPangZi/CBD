package com.cbd.cbdcommoninterface.response;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class LocListResponse implements Serializable {
    //值
    private String value;

    //名称
    private String label;

    //子list
    private List<LocListResponse> children;
}

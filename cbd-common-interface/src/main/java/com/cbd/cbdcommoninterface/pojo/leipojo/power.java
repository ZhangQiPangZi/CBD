package com.cbd.cbdcommoninterface.pojo.leipojo;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

/**
 * @author shy_black
 * @date 2020/4/18 11:13
 * @Description:
 */
@Getter
@Setter
@ToString
public class power implements Serializable {
    private Integer powerID;

    private String powerName;

    private String url;

    private Integer status;
}

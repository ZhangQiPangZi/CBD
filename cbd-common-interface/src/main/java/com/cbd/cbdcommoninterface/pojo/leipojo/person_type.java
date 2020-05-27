package com.cbd.cbdcommoninterface.pojo.leipojo;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

/**
 * @author shy_black
 * @date 2020/4/19 0:47
 * @Description: 无须dao
 */
@Getter
@Setter
@ToString
public class person_type implements Serializable {
    private Integer typeID;

    private String TypeName;

}

package com.cbd.cbdcommoninterface.pojo.leipojo;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

/**
 * @author shy_black
 * @date 2020/4/18 11:10
 * @Description:
 */
@Getter
@Setter
@ToString
public class company_type  implements Serializable {
    private Integer typeID;

    private String typeName;
}

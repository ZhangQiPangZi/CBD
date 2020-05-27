package com.cbd.cbdcommoninterface.pojo.leipojo;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

/**
 * @author shy_black
 * @date 2020/4/18 11:16
 * @Description:
 */
@Getter
@Setter
@ToString
public class role  implements Serializable {
    private Integer roleID;

    private String roleName;

    private String remark;

}

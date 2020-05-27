package com.cbd.cbdcommoninterface.pojo.leipojo;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

/**
 * @author shy_black
 * @date 2020/4/18 11:19
 * @Description:
 */
@Getter
@Setter
@ToString
public class role_user implements Serializable {
    private Integer id;

    private Integer roleID;

    private Integer userID;

}

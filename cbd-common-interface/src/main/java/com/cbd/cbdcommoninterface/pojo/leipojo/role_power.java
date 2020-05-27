package com.cbd.cbdcommoninterface.pojo.leipojo;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

/**
 * @author shy_black
 * @date 2020/4/18 11:18
 * @Description:
 *     private Integer id;
 *     private Integer roleID;
 *     private Integer powerID;
 *     private String companyID;
 */
@Getter
@Setter
@ToString
public class role_power implements Serializable {
    private Integer id;

    private Integer roleID;

    private Integer powerID;

    private Integer status;

}

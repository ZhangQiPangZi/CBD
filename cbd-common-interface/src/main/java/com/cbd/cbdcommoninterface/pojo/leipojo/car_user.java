package com.cbd.cbdcommoninterface.pojo.leipojo;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

/**
 * @author shy_black
 * @date 2020/4/18 11:05
 * @Description:车辆-车主表
 */
@Getter
@Setter
@ToString
public class car_user  implements Serializable {
    private String carUUID;

    private String userID;

    private String companyID;

}

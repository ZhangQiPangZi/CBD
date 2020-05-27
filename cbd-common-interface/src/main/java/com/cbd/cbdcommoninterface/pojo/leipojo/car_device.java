package com.cbd.cbdcommoninterface.pojo.leipojo;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

/**
 * @author shy_black
 * @date 2020/4/18 10:58
 * @Description: 车辆和设备间的关联表
 */
@Getter
@Setter
@ToString
public class car_device implements Serializable {
    private String carUUID;

    private String companyID;

    private String devID;

    private int status;

}

package com.cbd.cbdcommoninterface.pojo.leipojo;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

/**
 * @author shy_black
 * @date 2020/4/18 11:01
 * @Description:车辆信息表
 */
@Getter
@Setter
@ToString
public class car_info implements Serializable {
    private Integer id;

    private String UUID;

    private String devID;

    private String owerID;

    private Integer orderID;

    private  String owerName;

    private String companyID;//is company

    private  String companyName;

    private String carPlateNum;


    private Integer orderTime;

    private  String phoneNum;


    private Integer status;

}

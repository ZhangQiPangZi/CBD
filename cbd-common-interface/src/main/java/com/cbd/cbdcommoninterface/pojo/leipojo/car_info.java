package com.cbd.cbdcommoninterface.pojo.leipojo;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @author shy_black
 * @date 2020/4/18 11:01
 * @Description:车辆信息表
 */
@Getter
@Setter
@ToString
public class car_info {
    private Integer id;

    private String UUID;

    private String devID;

    private String owerID;

    private  String owerName;

    private String companyID;//is company

    private  String companyName;

    private String carPlateNum;

    private  String carModelID;//车辆具体品牌型号

    private String modelName;

    private Integer buyTime;

    private Integer serviceStartTime;

    private Integer serviceTime;//服务期限

    private String carColor;

    private  String phoneNum;

    private String carPrice;

    private Integer status;

}

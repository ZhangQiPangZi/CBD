package com.cbd.cbdcommoninterface.response.leiVo;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.annotation.Resource;
import java.io.Serializable;

/**
 * @author shy_black
 * @date 2020/5/3 11:36
 * @Description:
 * devID,owerName,phoneNum, nTime,dbLon,dbLat
 */
//@JsonIgnoreProperties(value = {"hibernateLazyInitializer", "handler"})
@Resource
@Getter
@Setter
@ToString
public class CarForTreeVo implements Serializable {

    private String devID;

    private String owerName;

    private String phoneNum;

    private long nTime;

    private String dbLon;

    private String dbLat;

    private String carPlateNum;

    private Integer speed;

    private String companyID;

    private String companyName;

}

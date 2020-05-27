package com.cbd.cbdcommoninterface.response.leiVo;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.annotation.Resource;
import java.io.Serializable;

/**
 * @author shy_black
 * @date 2020/5/5 9:26
 * @Description:
 */
@Resource
@Getter
@Setter
@ToString
public class findCarVo implements Serializable {

    private String devID;

    private String owerName;

    private String phoneNum;

    private String nTime;

    private String dbLon;

    private String dbLat;




}

package com.cbd.cbdcommoninterface.response.leiVo;

import com.cbd.cbdcommoninterface.pojo.leipojo.TrackLast;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

/**
 * @author shy_black
 * @date 2020/4/27 10:39
 * @Description:
 */
@Getter
@Setter
@ToString
public class RealTrackVo implements Serializable {

    private String devID;

    private String owerName;

    private String phoneNum;

    private String carPlateNum;

    private long nTime;

    private String dbLon;

    private String dbLat;

    private Integer speed;



//    private TrackLast trackLast;

}

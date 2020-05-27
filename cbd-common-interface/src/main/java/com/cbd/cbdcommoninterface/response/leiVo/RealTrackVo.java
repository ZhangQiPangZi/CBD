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

    private String userName;

    private String phoneNum;

    private String carPlateNum;

    private TrackLast trackLast;

}

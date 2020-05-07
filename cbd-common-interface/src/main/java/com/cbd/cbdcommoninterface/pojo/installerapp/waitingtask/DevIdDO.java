package com.cbd.cbdcommoninterface.pojo.installerapp.waitingtask;

import lombok.Data;

import java.io.Serializable;

/**
 * @author: Monster
 * @date: 2020/5/1 23:24
 * @Description
 */
@Data
public class DevIdDO implements Serializable {
    /**
     * 序号
     */
    private Integer id;
    /**
     * 设备Id
     */
    private String devId;
    /**
     * simId
     */
    private String simId;
}

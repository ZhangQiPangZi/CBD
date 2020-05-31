package com.cbd.cbdcommoninterface.request.installerapp.waitingtask;

import lombok.Data;
import java.io.Serializable;

/**
 * @author: Monster
 * @date: 2020/4/22 11:03
 * @Description
 */
@Data
public class RemoveQuery implements Serializable {
    /**
     * 安装工id
     */
    private Integer installerId;
    /**
     * 设备id
     */
    private String devId;
    /**
     * 车主电话
     */
    private String phoneNumber;
    /**
     * 拆除原因
     */
    private String removeReason;
    /**
     * 拆除时间
     */
    private String removeTime;
    /**
     * 拆除结果
     */
    private String removeResult;

}

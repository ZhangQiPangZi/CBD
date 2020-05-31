package com.cbd.cbdcommoninterface.request.salesapp.workbench;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @author: Monster
 * @date: 2020/4/20 22:43
 * @Description
 */
@Data
public class AssignQuery implements Serializable {
    /**
     * 订单id
     */
    private Integer id;
    /**
     * 订单类型
     */
    private Integer orderTypeCode;
    /**
     * 工程师id
     */
    private Integer installerId;
    /**
     * 预定时间
     */
    @ApiModelProperty(value = "预约时间",example = "2019-04-11 14:00:00")
    private String orderTime;
    /**
     * 经度
     */
    @ApiModelProperty(value = "经度",example = "108.930000")
    private Double longitude;
    /**
     * 纬度
     */
    @ApiModelProperty(value = "纬度",example = "34.230000")
    private Double latitude;
    /**
     * 名字
     */
    @ApiModelProperty(value = "搜索关键字",example = "张")
    private String key;
    /**
     * 升序降序的标志
     */
    @ApiModelProperty(value = "升序降序的标志位",example = "false")
    private Boolean flag;
    /**
     * 设备id
     */
    @ApiModelProperty(value = "设备id",example = "dddsdazz")
    private String devId;
    /**
     * simId
     */
    @ApiModelProperty(value = "sim卡id",example = "asdsde")
    private String simId;
}

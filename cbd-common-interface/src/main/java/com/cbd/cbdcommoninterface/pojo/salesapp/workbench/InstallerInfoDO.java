package com.cbd.cbdcommoninterface.pojo.salesapp.workbench;

import lombok.Data;

import java.io.Serializable;

/**
 * @author: Monster
 * @date: 2020/4/20 22:47
 * @Description
 */
@Data
public class InstallerInfoDO implements Serializable {
    /**
     * 序号
     */
    private Integer id;
    /**
     * 姓名
     */
    private String name;
    /**
     * 性别
     */
    private String sex;
    /**
     * 能力等级
     */
    private Integer level;
    /**
     * 经度
     */
    private Double longitude;
    /**
     * 纬度
     */
    private Double latitude;
    /**
     * 电话号码
     */
    private String phoneNumber;
    /**
     * 所属公司
     */
    private String company;
    /**
     * 邮箱
     */
    private String mail;
}

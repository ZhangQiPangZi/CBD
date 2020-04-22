package com.cbd.cbdcommoninterface.pojo.installerapp.user;

import lombok.Data;

import java.io.Serializable;

/**
 * @author: Monster
 * @date: 2020/4/21 19:44
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
     * 电话
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

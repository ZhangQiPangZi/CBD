package com.cbd.cbdcommoninterface.pojo.salesapp.user;

import lombok.Data;

import java.io.Serializable;

/**
 * @author: Monster
 * @date: 2020/4/20 20:37
 * @Description
 */
@Data
public class SalesInfoDO implements Serializable {
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

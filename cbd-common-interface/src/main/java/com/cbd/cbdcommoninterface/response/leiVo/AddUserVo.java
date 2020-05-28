package com.cbd.cbdcommoninterface.response.leiVo;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.util.List;

/**
 * @author shy_black
 * @date 2020/5/4 21:24
 * @Description:
 * .userName,a.phoneNum,a.email,b.typeName ,c.statusName , d.companyName
 *
 * userName,phoneNum,companyID,status,email,userType,sex
 */
@Getter
@Setter
@ToString
public class AddUserVo implements Serializable {

    private String userName;

    private String phoneNum;

    private String companyID;

    private String status;

    private String email;

    //用户类型--用于展示
    private String userType;

    private String sex;

    //用户角色列表，用于权限控制
    private List<Integer> roleList;

//    //判断是否为安装工
//    private boolean isInstaller;


    //private Integer installer_id;

//    private Integer level;
//
//    private String longitude;
//
//    private String latitude;
}

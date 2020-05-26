package com.cbd.cbdcommoninterface.response.leiVo;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

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
public class AddUserVo {

    private String userName;

    private String phoneNum;

    private String companyID;

    private String status;

    private String email;

    private String userType;

    private String sex;

    private List<Integer> roleList;
}

package com.cbd.cbdcommoninterface.response.leiVo;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

/**
 * @author shy_black
 * @date 2020/5/4 21:10
 * @Description:
 * a.ID,a.userName,a.phoneNum,a.email,b.typeName ,c.statusName , d.companyName,a.sex
 *
 */
@Getter
@Setter
@ToString
public class UserResponseVo implements Serializable {
    private int ID;

    private String userName;

    private String phoneNum;

    private String sex;

    private String email;

    private String typeName;

    private String statusName;

    private String companyID;

    private String companyName;

    private String parentCompanyList;
}

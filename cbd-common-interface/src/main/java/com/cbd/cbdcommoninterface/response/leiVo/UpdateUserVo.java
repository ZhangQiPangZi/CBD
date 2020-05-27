package com.cbd.cbdcommoninterface.response.leiVo;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

/**
 * @author shy_black
 * @date 2020/5/4 23:47
 * @Description:
 */
@Getter
@Setter
@ToString
public class UpdateUserVo implements Serializable {
    private Integer ID;

    private String userName;

    private String phoneNum;

    private String email;

    private String sex;

    private String typeName;

    private String statusName;

    private String companyName;

    private String password;
}

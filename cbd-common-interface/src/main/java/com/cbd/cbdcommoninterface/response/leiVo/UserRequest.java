package com.cbd.cbdcommoninterface.response.leiVo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

/**
 * @author shy_black
 * @date 2020/4/28 23:32
 * @Description:
 */
@Getter
@Setter
@ToString
public class UserRequest implements Serializable {

    @JsonProperty(value = "ID")
    private Integer ID;

    private String userName;

    private String password;

    private String phoneNum;

    private String companyID;

    private Integer status;

    private String email;

    private Integer userType;
}

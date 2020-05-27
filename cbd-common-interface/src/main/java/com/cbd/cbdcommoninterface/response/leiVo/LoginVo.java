package com.cbd.cbdcommoninterface.response.leiVo;

import lombok.Data;

import java.io.Serializable;

/**
 * @author shy_black
 * @date 2020/4/28 22:14
 * @Description:
 */
@Data
public class LoginVo implements Serializable {
    private String phoneNum;
    private String password;
}

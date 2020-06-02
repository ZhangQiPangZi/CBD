package com.cbd.cbdcontroller.qwe.authentication;

import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * @author shy_black
 * @date 2020/6/2 15:51
 * @Description:
 */
public class JWTPasswordEncoder implements PasswordEncoder {

    @Override
    public String encode(CharSequence charSequence) {
        //不做任何加密处理
        return charSequence.toString();
    }

    @Override
    public boolean matches(CharSequence charSequence, String s) {
        //charSequence是前端传过来的密码，s是数据库中查到的密码
        if (charSequence.toString().equals(s)) {
            return true;
        }
        return false;
    }
}
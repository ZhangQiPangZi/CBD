package com.cbd.cbdcontroller.qwe.handler;

import com.alibaba.fastjson.JSON;
import com.cbd.cbdcommoninterface.result.CodeMsg;
import com.cbd.cbdcommoninterface.result.Result;
import org.springframework.security.authentication.*;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author shy_black
 * @Description: 登录失败处理逻辑
 * @Date Create in
 */
@Component
public class CustomizeAuthenticationFailureHandler implements AuthenticationFailureHandler {

    @Override
    public void onAuthenticationFailure(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AuthenticationException e) throws IOException, ServletException {
        //返回json数据
        Result<String> result = null;

        if (e instanceof BadCredentialsException) {
            //密码错误
            result = Result.error(CodeMsg.PASSWORD_ERROR);
        }
        else if (e instanceof InternalAuthenticationServiceException) {
            //用户不存在
            result = Result.error(CodeMsg.USER_NOT_EXIT_ERROR);
        }else{
            //其他错误
            result = Result.error(CodeMsg.SERVER_ERROR);
    }
        //处理编码方式，防止中文乱码的情况
        httpServletResponse.setContentType("text/json;charset=utf-8");
        //塞到HttpServletResponse中返回给前台
        httpServletResponse.getWriter().write(JSON.toJSONString(result));
    }
}

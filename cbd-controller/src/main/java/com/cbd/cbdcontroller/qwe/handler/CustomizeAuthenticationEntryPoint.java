package com.cbd.cbdcontroller.qwe.handler;

import com.alibaba.fastjson.JSON;
import com.cbd.cbdcommoninterface.result.CodeMsg;
import com.cbd.cbdcommoninterface.result.Result;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;
import springfox.documentation.spring.web.json.Json;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author shy_black
 * @date 2020/6/1 16:26
 * @Description: 匿名用户访问无权限资源时的异常
 *
 */
@Component
public class CustomizeAuthenticationEntryPoint implements AuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AuthenticationException e) throws IOException, ServletException {

        Result<String> res =  Result.error(CodeMsg.DIDNT_LOGIN_ERROR);
        httpServletResponse.setContentType("text/json;charset=utf-8");
        httpServletResponse.getWriter().write(JSON.toJSONString(res));

//        JsonResult result = ResultTool.fail(ResultCode.USER_NOT_LOGIN);
//        httpServletResponse.setContentType("text/json;charset=utf-8");
//        httpServletResponse.getWriter().write(JSON.toJSONString(result));
    }
}

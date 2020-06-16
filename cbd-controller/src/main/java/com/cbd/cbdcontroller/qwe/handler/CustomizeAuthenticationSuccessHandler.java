package com.cbd.cbdcontroller.qwe.handler;

import com.alibaba.fastjson.JSON;
import com.cbd.cbdcommoninterface.cbd_interface.user.ILoginService;
import com.cbd.cbdcommoninterface.cbd_interface.user.IUserService;
import com.cbd.cbdcommoninterface.response.leiVo.UserBaseInfoAndPowerInfoVo;
import com.cbd.cbdcommoninterface.response.leiVo.UserResponseVo;
import com.cbd.cbdcommoninterface.result.CodeMsg;
import com.cbd.cbdcommoninterface.result.Result;
import com.cbd.cbdcontroller.qwe.authentication.VUserDetails;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author shy_black
 * @Description: 登录成功处理逻辑
 * @Date
 */
@Component
@Slf4j
public class CustomizeAuthenticationSuccessHandler implements AuthenticationSuccessHandler {
    @Autowired
    IUserService userService;


    @Autowired
    ILoginService loginService;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Authentication authentication) throws IOException, ServletException {
        //更新用户表上次登录时间、更新人、更新时间等字段
        //    user userDetails = (user) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
//        SysUser sysUser = sysUserService.selectByName(userDetails.getUsername());
//        sysUser.setLastLoginTime(new Date());
//        sysUser.setUpdateTime(new Date());
//        sysUser.setUpdateUser(sysUser.getId());
//        sysUserService.update(sysUser);

        //此处还可以进行一些处理，比如登录成功之后可能需要返回给前台当前用户有哪些菜单权限，
        //进而前台动态的控制菜单的显示等，具体根据自己的业务需求进行扩展

        //返回json数据

        boolean flag = false;

        //字符：
        // p-pc
        // s-销售
        // i-安装工
        String type = httpServletRequest.getParameter("type");



        VUserDetails userDetail = (VUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        log.info("type = "+type);
        log.info("userType = "+userDetail.getUserType());


        if (3 == userDetail.getUserType() && ("i".equals(type))) {
           flag = true;
        }else if(3 == userDetail.getUserType() && (!"i".equals(type))) {
            flag = false;
        }else if(2 == userDetail.getUserType() && ("s".equals(type))) {
            flag = true;
        }else if(2 == userDetail.getUserType() && (!"s" .equals(type))) {
            flag = false;
        }else {
            flag = true;
        }

        //else if((0 == userDetail.getUserType() ) && ("p".equals(type))) {
        //            flag = true;
        //        }else if(0 == userDetail.getUserType() && (!"p".equals(type))) {
        //            flag = false;
        //        }

        if(flag == true) {

            //获取用户详细信息
            UserBaseInfoAndPowerInfoVo userBaseInfoAndPowerInfoVo = userService.login(userDetail.getPhoneNum());

            userBaseInfoAndPowerInfoVo.setRoleList(userDetail.getRoleList());

            Result<UserResponseVo> res = Result.success(userBaseInfoAndPowerInfoVo);

            log.info("登录人员详细信息："+ JSON.toJSONString(res) );

            //处理编码方式，防止中文乱码的情况
            httpServletResponse.setContentType("text/json;charset=utf-8");
            //塞到HttpServletResponse中返回给前台
            httpServletResponse.getWriter().write(JSON.toJSONString(res));
            return;
        }else {
            Result<String> res = Result.error(CodeMsg.NO_PERMISSION);
            //处理编码方式，防止中文乱码的情况
            httpServletResponse.setContentType("text/json;charset=utf-8");
            //塞到HttpServletResponse中返回给前台
            httpServletResponse.getWriter().write(JSON.toJSONString(res));
            return;
        }

    }
}

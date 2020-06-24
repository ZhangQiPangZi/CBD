package com.cbd.cbdcontroller.security.authorization;


import com.cbd.cbdcommoninterface.cbd_interface.user.ILoginService;
import com.cbd.cbdcommoninterface.pojo.leipojo.power;
import com.cbd.cbdcommoninterface.pojo.leipojo.role;
import com.google.gson.Gson;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * @author shy_black
 * @date 2020/5/31 14:54
 * @Description:
 * 权限资源管理器，它的工作是通过用户的请求地址，来获取访问这个地址所需的权限
 */
@Slf4j
@Component
public class VFilterInvocationSecurityMetadataSource  implements FilterInvocationSecurityMetadataSource {

    Gson gson = new Gson();

    @Autowired
    ILoginService loginService;


    @Override
    public Collection<ConfigAttribute> getAttributes(Object object) throws IllegalArgumentException {
        //获取请求起源路径
        String requestUrl = ((FilterInvocation) object).getRequestUrl();

        Logger logger = LoggerFactory.getLogger(this.getClass());


        logger.info("当前请求资源路径：[requestUrl={}]", requestUrl);
        //登录页面就不需要权限
        if ("/login".equals(requestUrl)) {
            return null;
        }

        //用来存储访问路径需要的角色或权限信息
        List<String> tmpPowerList = new ArrayList<String>();

        //获取角色列表
        List<role> roleList = loginService.findSRoleListBySPermissionUrl(requestUrl);
        logger.info("访问资源需要角色：[sRoleList={}]", gson.toJson(roleList));

        for(role role : roleList) {
            tmpPowerList.add(role.getRoleName());
        }
        //获取资源权限列表
        List<power> powerList = loginService.findSPermissionListBySPermissionUrl(requestUrl);
        logger.info("访问资源需要权限 ：[sPermissionList={}]", gson.toJson(powerList));

        for(power power : powerList) {
            tmpPowerList.add(power.getPowerName());
        }
        //如果没有权限控制的url，可以设置都可以访问，也可以设置默认不许访问
        if(tmpPowerList.isEmpty()) {
            return null;//都可以访问
            //tempPermissionList.add("DEFAULT_FORBIDDEN");//默认禁止
        }

        String[] powerArray = tmpPowerList.toArray(new String[0]);
        logger.info("所需权限列表 getAttributes [permissionArray={}]", gson.toJson(powerArray));

        return SecurityConfig.createList(powerArray);
    }

    @Override
    public Collection<ConfigAttribute> getAllConfigAttributes() {
        return null;
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return true;
    }
}

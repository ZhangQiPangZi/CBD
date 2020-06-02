package com.cbd.cbdcontroller.qwe.authorization;

import com.cbd.cbdcommoninterface.result.CodeMsg;
import com.cbd.cbdcommoninterface.result.GlobalException;
import org.springframework.security.access.AccessDecisionManager;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

import java.util.Collection;

/**
 * @author shy_black
 * @date 2020/5/31 15:14
 * @Description:
 * 权限管理判断器，他的主要工作就是鉴权，
 * 通过拿到的访问路径所需的权限，和用户所拥有的权限进行对比，
 * 判断用户是否有权限访问
 *
 * 权限管理判断器|校验用户是否有权限访问请求资源
 */
@Component
public class VAccessDecisionManager implements AccessDecisionManager {

    @Override
    public void decide(Authentication authentication, Object object, Collection<ConfigAttribute> collection) throws AccessDeniedException, InsufficientAuthenticationException {
        //当前用户所具有的权限
        Collection<? extends GrantedAuthority> userAuthorityList = authentication.getAuthorities();
        //访问资源所需的权限信息
        Collection<ConfigAttribute> needAuthoritieList = collection;
        //依次循环对比，发现有匹配的即返回
        for (ConfigAttribute needAuthoritie : needAuthoritieList) {
            String needAuthoritieStr = needAuthoritie.getAttribute();
            for (GrantedAuthority userAuthority : userAuthorityList) {
                String userAuthorityStr = userAuthority.getAuthority();
                if (needAuthoritieStr.equals(userAuthorityStr)) {
                    return;
                }
            }

        }
        //执行到这里说明没有匹配到应有权限
        throw new AccessDeniedException("权限不足!");


    }

    @Override
    public boolean supports(ConfigAttribute configAttribute) {
        return true;
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return true;
    }
}

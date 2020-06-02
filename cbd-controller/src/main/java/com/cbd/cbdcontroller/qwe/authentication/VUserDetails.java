package com.cbd.cbdcontroller.qwe.authentication;


import com.cbd.cbdcommoninterface.pojo.leipojo.power;
import com.cbd.cbdcommoninterface.pojo.leipojo.role;
import com.cbd.cbdcommoninterface.pojo.leipojo.user;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.List;

/**
 * @author shy_black
 * @date 2020/5/31 13:55
 * @Description:
 * authentication包，用于认证
 *
 * 用户信息的封装，基本信息及权限信息
 */
@Slf4j
@Data
public class VUserDetails extends user implements UserDetails {

    private static final long serialVersionUID = 1L;

    private List<role> roleList = null;

    private List<power> powerList = null;

    public VUserDetails(user user ,List<role> roleList,List<power> powerList) {
        super(user);
        this.roleList = roleList;
        this.powerList = powerList;

    }

    /**
     * 获取用户权限列表方法
     * 可以理解成，返回了一个List<String>，之后所谓的权限控制、鉴权，
     *   其实就是跟这个list里的String进行对比
     * 这里处理了角色和资源权限两个列表，可以这么理解，
     * 角色是权限的抽象集合，是为了更方便的控制和分配权限，
     * 而真正颗粒化细节方面，还是需要资源权限自己来做
     */
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        StringBuilder authoritiesBuilder = new StringBuilder("");
        List<role> tmpRoleList = this.getRoleList();
        if(null != tmpRoleList) {
            for(role role : tmpRoleList) {
                authoritiesBuilder.append(",").append(role.getRoleName());
            }
        }
        List<power> tmpPowerList = this.getPowerList();
        if(null != tmpPowerList) {
            for(power power : tmpPowerList) {
                authoritiesBuilder.append(",").append(power.getPowerName());
            }
        }
        String authoritiesStr = "";
        if(authoritiesBuilder.length()>0) {
            authoritiesStr = authoritiesBuilder.deleteCharAt(0).toString();
        }
        log.info("VUserDetails getAuthorities [authoritiesStr={} ", authoritiesStr);


        return AuthorityUtils.commaSeparatedStringToAuthorityList(authoritiesStr);

    }

    @Override
    public String getPassword() {
        return super.getPassword();
    }

    @Override
    public String getUsername() {
        return super.getPhoneNum();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    public List<role> getRoleList() {
        return roleList;
    }
    public void setRoleList(List<role> roleList) {
        this.roleList = roleList;
    }
    public  List<power> getPowerList() {
        return  powerList;
    }
    public void setPowerList(List<power> powerList) {
        this.powerList = powerList;
    }

}

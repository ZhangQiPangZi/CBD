package com.cbd.cbdcommoninterface.pojo.leipojo;


import com.cbd.cbdcommoninterface.cbd_interface.user.IRoleDefineService;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;


import java.io.Serializable;
import java.util.Collection;
import java.util.List;

/**
 * @author shy_black
 * @date 2020/4/18 10:37
 * @Description:
 */

@Getter
@Setter
@ToString
public class user implements UserDetails, Serializable {




    @Autowired
    private IRoleDefineService roleDefineService;

    @JsonProperty(value = "ID")
    private Integer ID;

    private String userName;

    private String phoneNum;

    private String password;

    private Collection<? extends GrantedAuthority> authorities;

    private String companyID;

    private Integer status;

    private String email;

    private Integer userType;

    private String sex;

    public user() {

    }

    public user(String userName,String password,Collection<? extends GrantedAuthority> authorities) {
        this.phoneNum = userName;
        this.password = password;
        this.authorities = authorities;
    }

    //获取权限：根据用户id获取，调用service
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<SimpleGrantedAuthority> SGA =
                roleDefineService.getUserRoleByID(this.ID);

        return SGA;
    }

    @Override
    public String getUsername() {
        return this.phoneNum;
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

    //private String deptID;
}

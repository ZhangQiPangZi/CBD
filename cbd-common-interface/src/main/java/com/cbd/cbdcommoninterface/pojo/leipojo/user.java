package com.cbd.cbdcommoninterface.pojo.leipojo;


import com.cbd.cbdcommoninterface.cbd_interface.user.IRoleDefineService;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
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

@Data
@AllArgsConstructor
public class user implements Serializable {

    @JsonProperty(value = "ID")
    private Integer ID;

    private String userName;

    private String phoneNum;

    private String password;

    private String companyID;

    private Integer status;

    private String email;

    private Integer userType;

    private String sex;

    public user() {

    }

    public user(user addUser) {
        this.ID = addUser.getID();
        this.userName = addUser.getUserName();
        this.phoneNum = addUser.getPhoneNum();
        this.password = addUser.getPassword();
        this.companyID = addUser.getCompanyID();
        this.status = addUser.getStatus();
        this.email = addUser.getEmail();
        this.userType = addUser.getUserType();
        this.sex = addUser.getSex();
    }



}

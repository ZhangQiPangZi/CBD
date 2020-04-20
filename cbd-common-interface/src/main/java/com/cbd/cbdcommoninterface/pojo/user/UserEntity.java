package com.cbd.cbdcommoninterface.pojo.user;


import java.io.Serializable;

public class UserEntity implements Serializable {

    /** 主键 */
    private String id;

    /** 用户名 */
    private String username;

    /** 用户的角色 */
    private RoleEntity roleEntity;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public RoleEntity getRoleEntity() {
        return roleEntity;
    }

    public void setRoleEntity(RoleEntity roleEntity) {
        this.roleEntity = roleEntity;
    }
}

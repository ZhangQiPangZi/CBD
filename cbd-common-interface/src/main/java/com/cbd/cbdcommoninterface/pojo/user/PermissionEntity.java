package com.cbd.cbdcommoninterface.pojo.user;

import java.io.Serializable;

/**
 * 权限实体类
 */
public class PermissionEntity implements Serializable {

    /** 主键 */
    private String id;

    /** 权限名称 */
    private String permission;

    /** 权限描述 */
    private String desc;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPermission() {
        return permission;
    }

    public void setPermission(String permission) {
        this.permission = permission;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

}

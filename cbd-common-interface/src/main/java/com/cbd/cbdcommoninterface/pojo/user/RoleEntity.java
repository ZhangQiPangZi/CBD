package com.cbd.cbdcommoninterface.pojo.user;

import java.io.Serializable;
import java.util.List;

/**
 * 角色实体类
 */
public class RoleEntity implements Serializable {

    /** 主键 */
    private String id;

    /** 角色名称 */
    private String name;

    /** 角色描述 */
    private String desc;

    /** 该角色拥有的权限 */
    private List<PermissionEntity> permissionList;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public List<PermissionEntity> getPermissionList() {
        return permissionList;
    }

    public void setPermissionList(List<PermissionEntity> permissionList) {
        this.permissionList = permissionList;
    }


}

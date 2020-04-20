package com.cbd.cbdcommoninterface.pojo.user;


import java.io.Serializable;

/**
 * 接口访问权限的实体类
 */
public class AccessAuthEntity implements Serializable {

    /** 请求 URL */
    private String url;

    /** 接口方法名 */
    private String methodName;

    /** 当前接口是否需要登录 */
    private boolean isLogin;

    /** 当前接口的访问权限 */
    private String permission;

    public AccessAuthEntity() {
    }

    public AccessAuthEntity(String url, String methodName, boolean isLogin, String permission) {
        this.url = url;
        this.methodName = methodName;
        this.isLogin = isLogin;
        this.permission = permission;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getMethodName() {
        return methodName;
    }

    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }

    public boolean isLogin() {
        return isLogin;
    }

    public void setLogin(boolean login) {
        isLogin = login;
    }

    public String getPermission() {
        return permission;
    }

    public void setPermission(String permission) {
        this.permission = permission;
    }

}

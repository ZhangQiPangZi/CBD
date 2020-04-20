package com.cbd.cbdcommoninterface.keys;

public class AccessAuthKey extends BasePrefix{
    public AccessAuthKey(int expireSeconds, String prefix) {
        super(expireSeconds, prefix);
    }

    /** 接口访问权限的前缀 */
    public static AccessAuthKey Access_Auth= new AccessAuthKey(0,"AUTH");

    /** SessionID的前缀 */
    public static AccessAuthKey SessionID= new AccessAuthKey(0,"SESSION");

    /** 用户信息 */
    public static AccessAuthKey USER= new AccessAuthKey(0,"USER");
}

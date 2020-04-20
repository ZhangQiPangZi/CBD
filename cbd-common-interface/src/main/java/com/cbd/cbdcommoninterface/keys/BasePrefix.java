package com.cbd.cbdcommoninterface.keys;

import java.io.Serializable;

public abstract class BasePrefix implements KeyPrefix, Serializable {
//    key的有效时间默认0代表永不过期
    private int expireSeconds;
//    key的前缀，用来区分功能
    private String prefix;

    public BasePrefix(int expireSeconds, String prefix) {
        this.expireSeconds = expireSeconds;
        this.prefix = prefix;
    }

    public BasePrefix(String prefix) {
        this(0,prefix);
    }

    @Override
    public int expireSeconds() {
        return expireSeconds;
    }

    @Override
    public String getPrefix() {
        String className = getClass().getSimpleName();
        return className+":"+prefix;
    }
}

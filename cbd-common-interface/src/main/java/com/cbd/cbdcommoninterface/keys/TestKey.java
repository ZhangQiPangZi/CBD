package com.cbd.cbdcommoninterface.keys;

import java.io.Serializable;

public class TestKey extends BasePrefix {
    public TestKey(int expireSeconds, String prefix) {
        super(expireSeconds, prefix);
    }

    public static TestKey testKey = new TestKey(300,"tk");


}

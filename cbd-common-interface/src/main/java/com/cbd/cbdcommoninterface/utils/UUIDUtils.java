package com.cbd.cbdcommoninterface.utils;

import java.util.UUID;

public class UUIDUtils {
    public static String getUUID(){
        String uuid = UUID.randomUUID().toString();
        uuid = uuid.replaceAll("-", "");
        return uuid;
    }

}

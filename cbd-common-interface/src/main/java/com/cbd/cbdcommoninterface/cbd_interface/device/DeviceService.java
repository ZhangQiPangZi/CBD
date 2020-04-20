package com.cbd.cbdcommoninterface.cbd_interface.device;

import com.cbd.cbdcommoninterface.pojo.device.DeviceInfo;

public interface DeviceService {
    DeviceInfo testGetDevInfo(String devID);
    String testRedis();

}

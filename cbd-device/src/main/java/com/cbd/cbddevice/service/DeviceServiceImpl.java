package com.cbd.cbddevice.service;


import com.cbd.cbdcommoninterface.cbd_interface.device.DeviceService;
import com.cbd.cbdcommoninterface.cbd_interface.redis.RedisService;
import com.cbd.cbdcommoninterface.keys.TestKey;
import com.cbd.cbdcommoninterface.pojo.device.DeviceInfo;
import com.cbd.cbddevice.dao.DeviceDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DeviceServiceImpl implements DeviceService {
    @Autowired
    private DeviceDao deviceDao;
    @Autowired
    private RedisService redisService;

    @Override
    public DeviceInfo testGetDevInfo(String devID) {
        return deviceDao.findDeviceInfoByDevID(devID);
    }

    /**
     * 测试下redis
     **/
    @Override
    public String testRedis(){
        redisService.set(TestKey.testKey, "kk", "hh");
        return redisService.get(TestKey.testKey, "kk", String.class);
    }
}

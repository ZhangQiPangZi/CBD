package com.cbd.cbddevice.dao;

import com.cbd.cbdcommoninterface.pojo.device.DeviceInfo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface DeviceDao {
    /**
     * 根据设备ID查询设备信息
     * @param devID
     * @return
     */
    DeviceInfo findDeviceInfoByDevID(@Param("devID") String devID);
}

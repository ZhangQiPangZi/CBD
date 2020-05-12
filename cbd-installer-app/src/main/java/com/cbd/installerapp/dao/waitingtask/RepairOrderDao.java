package com.cbd.installerapp.dao.waitingtask;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;

/**
 * @author: Monster
 * @date: 2020/4/22 11:16
 * @Description
 */
@Mapper
public interface RepairOrderDao {

    /**
     * 将车辆和设备解绑
     * @param phoneNumber
     * @return
     */
    @Update("UPDATE orderinfo SET devId = null,simId = null WHERE phoneNumber =#{phoneNumber} AND orderTypeCode = '1';")
    int changeDev(@Param("phoneNumber")String phoneNumber);

    /**
     * 将车辆与设备再次绑定
     * @param devId
     * @param simId
     * @param phoneNumber
     * @return
     */
    @Update("UPDATE orderinfo SET devId = #{devId},simId = #{simId} WHERE phoneNumber =#{phoneNumber} AND orderStateTypeCode = '1' ")
    int reInstall(@Param("devId") String devId,@Param("simId") String simId,@Param("phoneNumber") String phoneNumber);
}

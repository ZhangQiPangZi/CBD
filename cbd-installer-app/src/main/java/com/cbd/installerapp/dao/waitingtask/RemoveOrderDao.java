package com.cbd.installerapp.dao.waitingtask;

import com.cbd.cbdcommoninterface.request.installerapp.waitingtask.RemoveQuery;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

/**
 * @author: Monster
 * @date: 2020/4/22 11:16
 * @Description
 */
@Mapper
public interface RemoveOrderDao {
    /**
     * 安装工拆除设备 存入log
     * @param query
     * @return
     */
    int removeDev(RemoveQuery query);


    /**
     * 拆除设备前获取devId
     * @param phoneNumber
     * @return
     */
    @Select("SELECT devId FROM orderinfo WHERE phoneNumber = #{phoneNumber} AND orderTypeCode = '1';")
    String getDevId(@Param("phoneNumber") String phoneNumber);

    /**
     * 拆除设备前获取simId
     * @param phoneNumber
     * @return
     */
    @Select("SELECT simId FROM orderinfo WHERE phoneNumber = #{phoneNumber} AND orderTypeCode = '1';")
    String getSimId(@Param("phoneNumber")String phoneNumber);
}

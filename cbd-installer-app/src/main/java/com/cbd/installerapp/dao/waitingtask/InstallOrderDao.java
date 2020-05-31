package com.cbd.installerapp.dao.waitingtask;

import com.cbd.cbdcommoninterface.pojo.installerapp.waitingtask.DevIdDO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;

/**
 * @author: Monster
 * @date: 2020/4/22 16:55
 * @Description
 */
@Mapper
public interface InstallOrderDao {


    /**
     * 查找该安装工当前还未安装的设备Id
     * @param installerId
     * @param orderId
     * @return
     */
    DevIdDO findDevId(@Param("installerId") Integer installerId,@Param("orderId") Integer orderId);
    /**
     * 设备安装完成
     * 设备id 和车辆对应
     * @param devId
     * @param orderId
     * @param simId
     * @param
     * @return
     */
    @Update("UPDATE orderinfo SET devId = #{devId},simId = #{simId} WHERE id = #{orderId};")
    int installOrderComplete(@Param("devId") String devId,@Param("simId") String simId,@Param("orderId") Integer orderId);

    /**
     * 任务列表中的任务状态发生改变
     * @param orderId
     * @return
     */
    @Update("UPDATE installertasklist SET state='1' , orderStateTypeCode='1' WHERE orderId =#{orderId};")
    int complete(@Param("orderId") Integer orderId);

    /**
     * 改变设备的使用状态
     * @param id
     * @return
     */
    @Update("UPDATE installerhasdev SET isInstall='1' WHERE id = #{id};")
    int updateState(@Param("id") Integer id);
}

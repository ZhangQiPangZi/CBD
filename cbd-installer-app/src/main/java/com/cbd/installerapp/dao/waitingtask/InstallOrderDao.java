package com.cbd.installerapp.dao.waitingtask;

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
     * 设备安装完成
     * 设备id sim卡id和车辆对应
     * @param devId
     * @param simId
     * @param orderId
     * @return
     */
    @Update("UPDATE orderinfo SET devId = #{devId},simId = #{simId} WHERE id = #{orderId};")
    int installOrderComplete(@Param("devId") Integer devId,@Param("simId") Integer simId,@Param("orderId") Integer orderId);

    /**
     * 任务列表中的任务状态发生改变
     * @param orderId
     * @return
     */
    @Update("UPDATE installertasklist SET state='1' , orderStateTypeCode='1' WHERE orderId =#{orderId};")
    int complete(@Param("orderId") Integer orderId);
}

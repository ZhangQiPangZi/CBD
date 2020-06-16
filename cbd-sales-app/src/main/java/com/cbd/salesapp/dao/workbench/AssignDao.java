package com.cbd.salesapp.dao.workbench;

import com.cbd.cbdcommoninterface.pojo.salesapp.workbench.InstallerInfoDO;
import com.cbd.cbdcommoninterface.request.salesapp.workbench.AssignQuery;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author: Monster
 * @date: 2020/4/20 22:27
 * @Description
 */
@Mapper
@Repository
public interface AssignDao {
    /**
     * 挑选出固定时间段的空闲的工程师，并根据能力等级进行排序
     * 根据关键字对工程师的名字进行模糊搜索
     * @param query
     * @return
     */
    List<InstallerInfoDO> orderByLevel(AssignQuery query);
    /**
     * 根据距离排序
     * @param query
     * @return
     */
    List<InstallerInfoDO> orderByDistance(AssignQuery query);

    /**
     * 指派工程师
     * @param query
     * @return
     */
    @Update("update orderinfo set installerId = #{query.installerId},orderStateTypeCode='1' WHERE id =#{query.id};")
    int assignInstaller(@Param("query") AssignQuery query);

    /**
     * 安装工持有设备的信息
     * @param devId
     * @param installerId
     * @param orderId
     * @param simId
     * @return
     */
    @Insert("INSERT INTO installerhasdev VALUES(NULL,#{orderId},#{devId},#{simId},#{installerId},'-1');")
    int installerHasDev(@Param("orderId") Integer orderId,@Param("devId") String devId,@Param("simId") String simId,@Param("installerId") Integer installerId );

    /**
     * 指派后加入到安装工的任务列表中
     * @param query
     * @return
     */
    @Insert("INSERT INTO installertasklist VALUES(NULL,#{query.installerId},#{query.id},#{query.orderTime},'-1','-1');")
    int insertToTaskList(@Param("query") AssignQuery query);

    /**
     * 改变订单的状态为待指派
     * @param id
     * @return
     */
    @Update("update orderinfo set orderStateTypeCode='-1' WHERE id =#{id};")
    int assignLater(@Param("id") Integer id);
}

package com.black.lei.dao;

import com.cbd.cbdcommoninterface.pojo.leipojo.role_power;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * @author shy_black
 * @date 2020/4/21 11:29
 * @Description:
 */
@Mapper
public interface role_powerDao {

    //保存用户权限（添加公司时保存默认管理员角色）
    @Insert("insert into role_power (roleID,powerID,companyID)" +
            "values (#{roleID},#{powerID},#{companyID})")
    void deliverPowerForRoler(role_power role_power);

    //获取当前角色已有权限
    @Select("select powerID from role_power " +
            "where roleID = #{0} and compnyID = #{1}")
    List<Integer> getRolerOfPower(int roleID, String companyID);

    //给角色新增权限
    @Insert("insert into role_power (roleID,powerID,status) " +
            "values (#{roleID},#{powerID},#{status})")
    Integer addPowerByRoleID(@Param("roleID") int roleID,
                             @Param("powerID")int powerID,
                             @Param("status")int status);

    //删除角色指定权限
    @Delete("delete from role_power where " +
            "roleID = #{roleID} and powerID = #{powerID}")
    Integer deletePowerByRoleID(int roleID, int powerID,int status);

    @Update("update role_power " +
            "set " +
            "status=#{status} " +
            "where roleID=#{roleID} and powerID=#{powerID}")
    Integer updateRolePowerStatus(int roleID,int powerID,int status);

    @Update("update role_power " +
            "set " +
            "status = 0 " +
            "where powerID = #{curPowerID} ")
    Integer updateRolePowerWithPowerTableByPowerID(@Param("curPowerID") Integer curPowerID);

}

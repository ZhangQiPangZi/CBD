package com.black.lei.dao;

import com.cbd.cbdcommoninterface.pojo.leipojo.power;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author shy_black
 * @date 2020/4/21 11:28
 * @Description:
 */
@Mapper
public interface powerDao {


    @Select("select * from power")
    List<power> getAllPowerList();

    @Select("select * from power " +
            "where powerName like CONCAT(CONCAT('%', #{key}), '%') or " +
            "url like CONCAT(CONCAT('%', #{key}), '%')")
    List<power> findLikelyPower(String key);

    @Insert("insert into power(powerName,url,status) " +
            "values (#{powerName},#{url},#{status})")
    Integer addPower(power power);

    @Update("update power " +
            "set " +
            "powerName=#{powerName}," +
            "url=#{url}," +
            "status=#{status} " +
            "where powerID=#{powerID}")
    Integer updatePower(power power);


    @Delete("delete from power where powerID={powerID}")
    Integer deletePower(power power);

    /**
     * 获取当前角色所有权限
     * @author wcj
     * @param nRoleID
     * @return
     */
    @Select("select * from power " +
            "where systemID = #{0} and nParentPowerID > 0 ")
    List<power> getPowerListByCompanyType(int nRoleID);
}

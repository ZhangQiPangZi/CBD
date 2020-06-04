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

    //SELECT * FROM s_permission sp " +
    //            " LEFT JOIN s_role_permission srp ON sp.id = srp.fk_permission_id " +
    //            " LEFT JOIN s_role sr ON srp.fk_role_id = sr.id " +

    //            " LEFT JOIN s_user_role sur ON sr.id = sur.fk_role_id " +
    //            " LEFT JOIN s_user su ON sur.fk_user_id = su.id " +
    //            " WHERE su.id = #{sUserId}

    @Select("select p.* from power p " +
            "left join role_power rp ON p.powerID = rp.powerID " +
            "left join role r ON rp.roleID = r.roleID " +
            "left join role_user ru ON r.roleID = ru.roleID " +
            "left join user u On ru.userID = u.ID " +
            "where u.ID = #{userID}  and p.status = 1 " +
            "GROUP BY p.powerID ")
    List<power> getPowerListByUserID(@Param("userID") int userID);

    //	@Select(value=" SELECT * FROM s_permission sp WHERE sp.url = #{sUserId} ")
    @Select("select * from power where url = #{url}")
    List<power> findSPermissionListBySPermissionUrl(@Param("url") String url);
}

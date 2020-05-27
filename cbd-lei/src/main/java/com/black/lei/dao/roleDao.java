package com.black.lei.dao;



import com.cbd.cbdcommoninterface.pojo.leipojo.power;
import com.cbd.cbdcommoninterface.pojo.leipojo.role;
import org.apache.ibatis.annotations.*;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.List;

/**
 * @author shy_black
 * @date 2020/4/18 23:18
 * @Description:
 */
@Mapper
public interface roleDao {


    @Insert("insert into role " +
            "(roleID, roleName, companyID,remark)" +
            "values(#{roleID}, #{roleName}, #{companyID},#{remark})")
    int insert(role record);


    @Select("select roleName from role where roleID=#{roleID}")
    SimpleGrantedAuthority getRoleNameByRoleID(int roleID);


    @Select("select * from role ")
    //返回roleID，roleName
    List<role> getAllRoles();

    @Select("select * from role where roleID=#{ID}")
    role getRoleInfoByRoleID(Integer ID);



    /**
     * 根据角色id查询该角色拥有的权限
     */
    @Select("select power.powerID, power.powerName, role_power.status from role " +
            "LEFT JOIN role_power on role.roleID=role_power.roleID " +
            "LEFT JOIN power on role_power.powerID=power.powerID " +
            "where role.roleID = #{roleID} ")
    //返回属性：powerID，powerName，status
    List<power> getPowerByRoleID(@Param("roleID") int roleID);

    /**
     * 添加角色
     */
    @Insert("insert into role " +
            "(roleID, roleName, companyID,remark)" +
            "values(#{roleID}, #{roleName}, #{companyID},#{remark})")
    void createRole(role addRole);

    /**
     * 修改角色
     * @param addRole
     */
    @Update("update role " +
            "set roleName = #{roleName}," +
            "remark = #{remark}" +
            "where roleID = #roleID")
    void updateRole(role addRole);

    /**
     * 删除指定角色
     * @param roleID
     */
    @Delete("delete from role where roleID = #{roleID}")
    void deleteRole(int roleID);

}

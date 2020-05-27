package com.black.lei.dao;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @author shy_black
 * @date 2020/4/19 0:52
 * @Description:
 */
@Mapper
public interface role_userDao {
    /**
     * 给用户分配权限(添加公司时给默认管理员分配权限)
     * @param roleID
     * @param userID
     */
    @Insert("insert into role_user(roleID,userID)" +
            "values(#{0},#{1})")
    void deliverRoler(int roleID, String userID);


    @Insert("insert into role_user(roleID,userID) " +
            "values(roleID,userID)")
    void addRole_User(int roleID,int userID);


    /**
     * 获取员工所有角色
     * @param userID
     * @return
     */
    @Select("select roleID " +
            "from role_user " +
            "where userID = #{0}")
    List<Integer> getRolesByUserID(Integer userID);

    /**
     * 删除员工所有角色
     * @param userID
     */
    @Delete("delete from role_user where userID = #{0}")
    void deleteUserRoles(String userID);

    /**
     * 给员工分配角色时，更新指定角色
     * @param roleID
     * @param userID
     */

    void updateRolesByUserID(int roleID, String userID);





}

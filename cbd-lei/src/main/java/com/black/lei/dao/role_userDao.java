package com.black.lei.dao;

import com.cbd.cbdcommoninterface.pojo.leipojo.role;
import org.apache.ibatis.annotations.*;

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
     * SELECT sr.* FROM s_role sr " +
     * " LEFT JOIN s_user_role sur ON sr.id = sur.fk_role_id " +
     * " LEFT JOIN s_user su ON sur.fk_user_id = su.id " +
     *   " WHERE su.id = #{sUserId} ")
     */
    @Select("select r.* from role r" +
            "left join role_user ru ON r.roleID = ru.roleID " +
            "left join user u ON ru.userID = u.ID " +
            "where u.ID = #{userID}")
    List<role> getRolesByUserID(@Param("userID") Integer userID);

    /**
     * 删除员工所有角色
     * @param userID
     */
    @Delete("delete from role_user where userID = #{0}")
    void deleteUserRoles(Integer userID);

    /**
     * 给员工分配角色时，更新指定角色
     * @param roleID
     * @param userID
     */

    void updateRolesByUserID(int roleID, String userID);





}

package com.black.lei.dao;



import com.cbd.cbdcommoninterface.pojo.leipojo.user;
import com.cbd.cbdcommoninterface.response.leiVo.*;
import org.apache.ibatis.annotations.*;

import java.util.List;
import java.util.Map;

/**
 * @author shy_black
 * @date 2020/4/18 23:58
 * @Description:
 */

//TODO

@Mapper
public interface userDao {



    @Select("select ID from user where phoneNum=#{phoneNum}")
    Integer findIDByPhoneNum(@Param("phoneNum") String phoneNum);


    @Select("select 1 from user where phoneNum = #{phoneNum} and password = #{password} limit 1")
    Integer isPasswordCorrect(@Param("phoneNum") String phoneNum,
                              @Param("password") String password);

    @Select("select * from user " +
            "where phoneNum = #{phoneNum} and password = #{password}")
    user findUserForLogin(String phoneNum, String password);

//    @Select("select ID,name,phoneNum from user" +
//            "where companyID = #{0} and nPersonType=4 and IsDeleted=0")
//    List<user> findEngineerList(String companyID);
    List<Map<String,String>> getPowerResource(Map<?, ?> paramMap);

    @Update("update user " +
            "set userType = (select typeID from person_type where typeName=#{typeName}) " +
            "where ID=")
    Integer updateUserTypeID(String typeName);

    @Select("select typeID from person_type where typeName=#{typeName}")
    Integer getUserTypeID(String typeName);

    @Select("select statusID from user_status where statusName=#{statusName}")
    Integer getUserStatusID(String statusName);

    @Select("select companyID from company_info where companyName = #{companyName}")
    String getCompanyIDByCompanyName(String companyName);

    @Select("select a.ID,a.userName,a.sex,a.companyID,c.statusName,a.phoneNum,a.email,b.typeName , d.companyName " +
            "from user a, person_type b,user_status c ,company_info d " +
            "where a.userType=b.typeID and a.`status`=c.statusID and a.companyID = d.companyID and d.lft >= #{lft} and d.rgt <= #{rgt}" +
            "and userType = #{userType}")
    List<UserResponseVo> findCarOwerByUserType(@Param("lft") String lft,
                                               @Param("rgt") String rgt ,
                                               @Param("userType") Integer userType);


     @Select("select a.ID,a.userName,a.sex,a.companyID,c.statusName,a.phoneNum,a.email,b.typeName , d.companyName " +
                "from user a, person_type b,user_status c ,company_info d " +
                "where a.userType=b.typeID and a.`status`=c.statusID and a.companyID = d.companyID and d.lft >= #{lft} and d.rgt <= #{rgt} " +
                "and ( a.userName like CONCAT(CONCAT('%', #{key}), '%') or a.phoneNum like CONCAT(CONCAT('%', #{key}), '%') )")
     List<UserResponseVo> findUserByPhoneNumOrByUserName(@Param("lft") String lft,
                                                       @Param("rgt") String rgt ,
                                                       @Param("key") String key);


    @Select("select a.ID,a.userName,a.sex,a.phoneNum,a.email,b.typeName ,c.statusName , d.companyName , d.companyID  " +
            "from user a, person_type b,user_status c ,company_info d " +
            "where a.userType=b.typeID and a.`status`=c.statusID and a.companyID = d.companyID and d.lft >= #{lft} and d.rgt <= #{rgt} ")
    List<UserResponseVo> findAllUserInfoByPage(@Param("lft") String lft,
                                               @Param("rgt") String rgt);


    @Select("select a.ID,a.userName,a.sex,a.phoneNum,a.email,b.typeName ,c.statusName , d.companyName , d.companyID " +
            "                        from user a,person_type b,user_status c,company_info d " +
            "                        where a.userType=b.typeID and a.status=c.statusID and a.companyID=d.companyID and a.phoneNum=#{phoneNum} and a.password=#{password}")
    UserBaseInfoAndPowerInfoVo login(@Param("phoneNum") String phoneNum ,
                                     @Param("password") String password);

    @Select("select * from user " +
            "where ID = #{0}")
    user findById(String carOwnerID);
    //      private Integer ID;userName;phoneNum; password; companyID; status; email; userType;
    @Update("update user " +
            "set userName = #{userName},phoneNum = #{phoneNum},cpmpanyID = #{companyID},status = #{status} ,email = #{email},userType = #{userType} " +
            "where ID = #{ID}")
    void update(user saveUser);

//    @Insert("insert into user (userName,phoneNum,companyID,status,email,userType) " +
//            "values (#{userName},#{phoneNum},#{companyID},#{status},#{email},#{userType})")
//    Integer insert(user saveUser);

    @Insert("insert into user (userName,phoneNum,companyID,status,email,userType) " +
            "values (#{userName},#{phoneNum},#{companyID},#{status},#{email},#{userType})")
    Integer insert(AddUserVo saveUser);

    /**
     * 创建用户(添加公司时默认创建管理员用户
     * @param saveUser
     */
    @Insert("insert into user (userName,phoneNum,password,companyID,status,email,userType) " +
            "values (#{userName},#{phoneNum},#{password},#{companyID},#{status}，#{email}，#{userType})")
    boolean createUser(user saveUser);

    /**
     * 根据公司id获取公司所属人员
     * @param CompanyID
     * @return
     */
    //select a.*,b.strTypeName
    //    	from Tlb_UserInfo as a,Tlb_PersonType as b
    //    	where a.nPersonType = b.nTypeID and a.nPersonType<![CDATA[<>]]> 0  and strCompanyID = #{0}
    //
    @Select("select a.*,b.typeName " +
            "from user as a,person_type as b " +
            "where a.userType = b.typeID and a.userType<![CDATA[<>]]> 0 and companyID = #{0}")
    List<Map<String,Object>> getUserListByCompanyID(String CompanyID);


    @Select("select companyID from user where id=#{userID}")
    String getCompanyIDByUserID(@Param("userID") Integer userID);

    @Select("select companyName from company_info where companyID=#{companyID} ")
    String getCompanyNameByCompanyID(@Param("companyID") String companyID);

    /**
     * 修改员工信息
     * @author shy_black
     * @param userInfo
     * @return
     */
    @Update("update user " +
            "set ID = #{ID},userName=#{userName},phoneNum=#{phoneNum},status=#{status} ,email = #{email},userType = #{userType} " +
            "where ID = #{ID} ")
    Integer updateUserInfo(user userInfo);


    @Select("select 1 from user where phoneNum = #{phoneNum} limit 1")
    Integer hasFindByPhone(String phoneNum);

    /**
     * 根据手机号查询用户
     * @param phoneNum
     * @return
     */
    @Select("select * from user " +
            "where phoneNum = #{phoneNum} limit 1")
    user findByPhone(@Param("phoneNum") String phoneNum);

    @Select("select count(phoneNum) from user " +
            "where phoneNum = #{phoneNum} ")
    Integer findCountByPhone(String phoneNum);


    /**
     * 根据姓名查询用户是否存在
     * @param userName
     * @return
     */
    @Select("select userName from user " +
            "where userName = #{userName}")
    boolean hasFindByUserName(String userName);


    /**
     * 根据姓名查询用户
     * @param userName
     * @return
     */
    @Select("select * from user " +
            "where userName = #{userName} limit 1")
    user findByUserName(String userName);


    /**
     * App登录查询
     * @param userName
     * @param password
     * @return
     */
    user findByUserName(String userName, String password);

    /**
     * 获取公司所属人员信息(APP)
     * @param strCompanyID
     * @return
     */

    List<Map<String, Object>> findPersonInfo(String strCompanyID);

    /**
     * 获取公司所有人员
     * @param
     * @return
     */
    //select strPersonID,strName from Tlb_UserInfo
    // where strCompanyID = #{0}
    @Select("select * from user " +
            "where companyID = #{companyID}")
    List<user> findAllUser(String companyID);

    /**
     * 获取项目经理
     * @param strCompanyID
     * @return
     */
    //select a.strName,a.strPersonID
    //    	from Tlb_UserInfo as a,Tlb_CompanyInfo as b
    //    	where b.strParentID = #{0} AND b.strManagerID = a.strPersonID
    //
    @Select("select a.userName a.ID from user as a,companyInfo as b " +
            "where b.parentID = #{0} and b.managerID = a.ID ")
    List<Map<String, String>> findManagerList(String strCompanyID);

    @Select("select userName,phoneNum from user where ID = #{owerID}")
    Map<String,Object> findUserBaseInfoByOwerID(String owerID);

    /**
     * 修改密码
     * @param user
     */
    //update Tlb_UserInfo
    //	    set
    //		  strPassWord = #{strPassWord,jdbcType=VARCHAR}
    //	    where strPersonID = #{strPersonID,jdbcType=VARCHAR}
    //
    @Update("update user " +
            "set password = #{password} " +
            "where ID = #{ID}")
    void updatePssword(user user);

    /**
     * 获取项目经理列表
     * @return
     */
    List<Map<String, String>> getManagerList(String strCompanyID);


    /**
     * 重置密码
     * @param strPhone
     * @param strNewPassWord
     */
    void resetPassword(String strPhone, String strNewPassWord);

//    @Select("select a.ID,a.userName,a.sex,a.phoneNum,a.email ,c.statusName , d.companyName , d.companyID, e.level , e.longitude , e.latitude" +
//            "from user a,user_status c,company_info d ,user_installer e " +
//            "where a.status=c.statusID and a.companyID=d.companyID " +
//            "and a.ID = e.installer_id and a.phoneNum= #{phoneNum} and a.password=#{password} ")


    @Select("select a.ID,a.userName,a.sex,a.phoneNum,a.email ,c.statusName , d.companyName , d.companyID, e.level , e.longitude , e.latitude " +
            "            from user a,user_status c,company_info d ,user_installer e " +
            "            where a.status=c.statusID and a.companyID=d.companyID " +
            "            and a.ID = e.installer_id and a.phoneNum= #{phoneNum} and a.password=#{password}")
    InstallerVo findInstallerByPhone(@Param("phoneNum") String phoneNum,
                                     @Param("password") String password);



    /**
     * 查询所有车主账号
     */
    //select distinct a.strPhone,a.headPicture from Tlb_UserInfo as a where a.nPersonType = 0 and a.strCompanyID in (select a.strCompanyID from Tlb_CompanyInfo as a where a.strCompanyID = 'df11640ed2824fd79fe617c37839caf4'
    //		union
    //		select a.strCompanyID from Tlb_CompanyInfo as a where a.strParentID = 'df11640ed2824fd79fe617c37839caf4'
    //		union
    //		select a.strCompanyID from Tlb_CompanyInfo as a,(select a.strCompanyID from Tlb_CompanyInfo as a where a.strParentID = 'df11640ed2824fd79fe617c37839caf4') as b where a.strParentID = b.strCompanyID)
    //
    List<Map<String, Object>> findAllCarOwnerList();

}


package com.cbd.salesapp.dao.user;

import com.cbd.cbdcommoninterface.pojo.salesapp.user.SalesInfoDO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

/**
 * @author: Monster
 * @date: 2020/4/20 20:32
 * @Description
 */
@Mapper
@Repository
public interface UserDao {
    /**
     * 根据用户电话查询用户基础信息
     * @param phoneNumber
     * @return
     */
    @Select("select * from salesinfo where phoneNumber=#{phoneNumber}")
    SalesInfoDO getUserInfo(@Param("phoneNumber") String phoneNumber);
}
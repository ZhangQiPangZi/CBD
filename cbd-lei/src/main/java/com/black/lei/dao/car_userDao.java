package com.black.lei.dao;

import com.black.lei.beans.car_user;
import org.apache.ibatis.annotations.Insert;

/**
 * @author shy_black
 * @date 2020/4/21 11:26
 * @Description:
 * private String carUUID;
 *     private String userID;
 *     private String companyID;
 */
public interface car_userDao {

    //插入car_user关联数据
    @Insert("insert into car_user (carUUID,userID,companyID) " +
            "values (#{carUUID},#{userID},#{companyID})")
    void insert(car_user carAndPersonRefer);
}

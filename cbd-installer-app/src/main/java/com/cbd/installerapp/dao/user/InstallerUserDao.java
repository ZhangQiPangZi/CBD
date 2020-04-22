package com.cbd.installerapp.dao.user;

import com.cbd.cbdcommoninterface.pojo.installerapp.user.InstallerInfoDO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

/**
 * @author: Monster
 * @date: 2020/4/21 19:57
 * @Description
 */
@Mapper
public interface InstallerUserDao {
    /**
     * 查看工程师的个人信息
     * @param phoneNumber
     * @return
     */
    @Select("select * from installerinfo where phoneNumber=#{phoneNumber}")
    InstallerInfoDO getUserInfo(@Param("phoneNumber") String phoneNumber);
}

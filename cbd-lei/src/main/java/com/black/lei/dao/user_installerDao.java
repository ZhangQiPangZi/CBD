package com.black.lei.dao;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * @author shy_black
 * @date 2020/5/27 15:48
 * @Description:
 */
@Mapper
public interface user_installerDao {

    @Insert("insert into user_installer (installer_id,level,longitude,latitude) " +
            "values (#{installer_id},#{level},#{longitude},#{latitude})")
    Integer addInstallerInfo(@Param("installer_id") Integer installer_id,
                             @Param("level") Integer level,
                             @Param("longitude") String longitude,
                             @Param("latitude") String latitude);

}

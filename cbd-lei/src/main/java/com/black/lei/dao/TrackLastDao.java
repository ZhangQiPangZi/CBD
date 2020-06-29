package com.black.lei.dao;

import com.cbd.cbdcommoninterface.pojo.leipojo.TrackLast;
import com.cbd.cbdcommoninterface.response.leiVo.RealTrackVo;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author shy_black
 * @date 2020/4/23 19:03
 * @Description:
 */
@Mapper
@Repository
public interface TrackLastDao {
    /**
     * 查询最后轨迹表(车辆平台初始化)
     *
     * @return
     * @author
     */
    @Select("select * from TrackLast")
    List<TrackLast> getTrackLastInfo();

    @Select("select * from TrackLast " +
            "where nAlarmState  <![CDATA[ <> ]]> 0")
    List<TrackLast> getTrackLastInfoForAlarm();

    @Select("select * from track " +
            "where devID = #{0}")
    TrackLast findByTEID(String devID);

    /**
     * 根据设备号查询轨迹信息
     *
     * @param
     * @return
     * @author
     */
    ////根据最近时间戳与devID/手机号查找实时定位
    //    @Select("select c.devID,c.owerName,c.phoneNum ,t.dbLon,t.dbLat, nTime,c.carPlateNum " +
    //            "       from track t LEFT JOIN car_info  c ON c.devID = t.devID  " +
    //            "       where t.devID = #{key} or c.phoneNum = #{key}" +
    //            "       ORDER BY nTime DESC limit 1 ")

//    select c.devID,c.owerName,c.phoneNum ,t.dbLon,t.dbLat, nTime,c.carPlateNum
//            from track t LEFT JOIN car_info  c ON c.devID = t.devID
//            where c.devID = 123
//						ORDER BY t.nTime
    @Select("select c.devID,c.owerName,c.phoneNum ,t.dbLon,t.dbLat, nTime,c.carPlateNum,t.speed " +
            "            from track t LEFT JOIN car_info  c ON c.devID = t.devID " +
            "            where c.devID = #{devID} and nTime >= #{startTime} and nTime <= #{endTime} " +
            "            ORDER BY t.nTime  ")
    List<RealTrackVo> getTrackInfoByTEID(@Param("devID") String devID,
                                         @Param("startTime") long startTime,
                                         @Param("endTime") long endTime);

    //根据最近时间戳与devID/手机号查找实时定位
    @Select("select c.devID,c.owerName,c.phoneNum ,t.dbLon,t.dbLat, nTime,c.carPlateNum ,t.speed " +
            "       from track t LEFT JOIN car_info  c ON c.devID = t.devID  " +
            "       where t.devID = #{key} or c.phoneNum = #{key}" +
            "       ORDER BY nTime DESC limit 1 ")
    RealTrackVo getRealTrackByTEID(String key);

//    @Insert("insert into user (userName,phoneNum,companyID,status,email,userType) " +
//            "values (#{userName},#{phoneNum},#{companyID},#{status},#{email},#{userType})")
//    Integer insert(user saveUser);

    @Insert("insert into track (devID,nTime,dbLon,dbLat,direction,speed,mileage) " +
            "values(#{devID},#{nTime},#{dbLon},#{dbLat},#{direction},#{speed},#{mileage} )")
    Integer addTrackData(TrackLast trackLast);

    @Insert("insert into track (devID,nTime,dbLon,dbLat) " +
            "values(#{devID},#{nTime},#{dbLon},#{dbLat})")
    Integer addTrackDataTest(TrackLast trackLast);

}

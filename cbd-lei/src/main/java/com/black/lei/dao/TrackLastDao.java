package com.black.lei.dao;

import com.black.lei.beans.TrackLast;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @author shy_black
 * @date 2020/4/23 19:03
 * @Description:
 */
@Mapper
public interface TrackLastDao {
    /**
     * 查询最后轨迹表(车辆平台初始化)
     * @author
     * @return
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
     * @author
     * @param
     * @return
     */
    @Select("select * from track " +
            "where devID = #{devID} and nTime >= #{startTime} and nTime <= #{endTime} ")
    List<TrackLast> getTrackInfoByTEID(String devID,int startTime,int endTime);


    //根据最近时间戳与devID查找实时定位
    @Select("select nID,devID,Max(nTime) as nTime,dbLon,dbLat,direction,speed,mileage " +
            "from track where devID = #{devID};")
   TrackLast getRealTrackByTEID(String devID);

}

package com.black.lei.dao;

import com.cbd.cbdcommoninterface.pojo.leipojo.car_device;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * @author shy_black
 * @date 2020/4/21 11:26
 * @Description:
 * 车辆-设备关联表
 *     private String carUUID;
 *     private String companyID;
 *     private String devID;
 *     private int status;
 */
@Mapper
public interface
car_deviceDao {

    //根据设备ID查找车辆设备关系
    @Select("select * from car_device " +
            "where devID = #{0}")
    car_device findFirst(String devID);

    //根据设备ID以及车辆ID删除设备车辆关系
    @Delete("delete from car_device " +
            "where companyID = #{0} and devID = #{1} and carUUID = #{2}")
    void deleteCarAndDevice(String companyID, String devID, String strCarUUID);

    //根据车辆，设备,查找公司下的设备车辆
    @Select("select * from car_device " +
            "where companyID = #{0} and devID = #{1} and carUUID = #{2} limit 0,1 ")
    car_device findFirstCarAndDev(String companyID, String devID, String carUUID);
    //插入一条已安装设备的车辆
    // insert into Tlb_CarAndDeviceRefer (strCarUUID, strCompanyID, strSIMID,
    //      strTEID, nDevType, nCarSizeType,
    //      nServiceState, nInterval)
    //    values (#{strCarUUID,jdbcType=VARCHAR}, #{strCompanyID,jdbcType=VARCHAR}, #{strSIMID,jdbcType=VARCHAR},
    //      #{strTEID,jdbcType=VARCHAR}, #{nDevType,jdbcType=INTEGER}, #{nCarSizeType,jdbcType=INTEGER},
    //      #{nServiceState,jdbcType=INTEGER}, #{nInterval,jdbcType=INTEGER})
    //
    @Insert("insert into car_device (carUUID,companyID,devID,status) " +
            "values (#{carUUID},#{companyID},#{devID},#{status} ) ")
    void save(car_device carAndDev);
    //select
    //  	<include refid="Base_Column_List"/>
    //  	from Tlb_CarAndDeviceRefer where strCompanyID = #{0,jdbcType=VARCHAR} and
    //  	strTEID = #{1,jdbcType=VARCHAR} and strCarUUID = #{2,jdbcType=VARCHAR} limit 0,1
    //
    @Select("select * from car_device " +
            "where companyID = #{0} and devID = #{1} and carUUID = #{2} limit 0,1 ")
    car_device findFirstById(String strShopID, String strTEID, String strCarUUID);
    //update Tlb_CarAndDeviceRefer
    //  	<set>
    //  		strTEID = #{strTEID,jdbcType=VARCHAR},strSIMID = #{strSIMID,jdbcType=VARCHAR},nDevType = #{nDevType,jdbcType = INTEGER},
    //  		nCarSizeType = #{nCarSizeType,jdbcType = INTEGER}, nServiceState = #{nServiceState,jdbcType = INTEGER},
    //  		nInterval = #{nInterval,jdbcType = INTEGER}
    //  	</set>
    //  	where strCarUUID = #{strCarUUID,jdbcType=VARCHAR}
    //
    @Update("update car_device " +
            "set devID = {devID} , companyID = #{companyID},status = #{status} " +
            "where carUUID = #{carUUID} ")
    void update(car_device carAndDevFinDeviceRefer);
    //select
    //  	<include refid="Base_Column_List"/>
    //  	from Tlb_CarAndDeviceRefer  where strCarUUID = #{0,jdbcType=VARCHAR} limit 0,1
    //
    @Select("select * from car_device " +
            "where carUUID = #{0} limit 0,1 ")
    car_device findFirstByUUID(String strCarUUID);

    //update Tlb_CarAndDeviceRefer
    //  	<set>
    //  		strCompanyID = #{strCompanyID,jdbcType=VARCHAR}
    //  	</set>
    //  	where strCarUUID = #{strCarUUID,jdbcType=VARCHAR}
    @Update("update car_device " +
            "set companyID = #{companyID} " +
            "where carUUID = #{carUUID}")
    void updateCompanyID(car_device andDeviceRefer);

    //select
    //  	<include refid="Base_Column_List"/>
    //  	from Tlb_CarAndDeviceRefer  where strCarUUID = #{0,jdbcType=VARCHAR}
    //
    @Select("select * from car_udevice " +
            "where carUUID = #{0}")
    List<car_device> find(String strCarUUID);
    //delete from Tlb_CarAndDeviceRefer
    //  	where strCarUUID = #{0}
    @Delete("delete from car_device where " +
            "carUUID = #{0}")
    void delete(String strCarUUID);



}

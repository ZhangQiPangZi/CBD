package com.cbd.cbddevice.dao;

import com.cbd.cbdcommoninterface.dto.AllocationDevDto;
import com.cbd.cbdcommoninterface.dto.ConfirmMessageDto;
import com.cbd.cbdcommoninterface.dto.DevConditionDto;
import com.cbd.cbdcommoninterface.dto.DevFactoryDto;
import com.cbd.cbdcommoninterface.pojo.device.DevType;
import com.cbd.cbdcommoninterface.pojo.device.DeviceAllotRecord;
import com.cbd.cbdcommoninterface.pojo.device.DeviceInfo;
import com.cbd.cbdcommoninterface.pojo.device.SIMInfo;
import com.cbd.cbdcommoninterface.pojo.message.DeviceMessageIDMap;
import com.cbd.cbdcommoninterface.pojo.message.DeviceMessageRecord;
import com.cbd.cbdcommoninterface.request.PageDevConditionRequest;
import com.cbd.cbdcommoninterface.response.DevReturnResponse;
import com.cbd.cbdcommoninterface.response.PageDevListResponse;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface DeviceDao {
    /**
     * 根据设备ID查询设备信息
     * @param devID
     * @return
     */
    DeviceInfo findDeviceInfoByDevID(@Param("devID") String devID);

    /**
     * 根据公司ID获取旗下的设备厂家列表
     * @param lft
     * @param rgt
     * @return
     */
    List<String> findAllDevFactoryNameByCompanyID(@Param("lft") Integer lft, @Param("rgt") Integer rgt);

    /**
     * 根据不同条件获得当前公司的设备列表
     * @param devConditionDto
     * @return
     */
    List<String> findDevListByCondition(DevConditionDto devConditionDto);

    /**
     * 根据设备ID获取设备类型信息
     * @param devID
     * @return
     */
    DevType getDevTypeByDevID(@Param("devID") String devID);

    /**
     * 根据设备名获取设备类型信息
     * @param devName
     * @return
     */
    DevType findDevTypeByDevName(@Param("devName") String devName);

    /**
     * 根据设备ID获得设备调拨记录列表
     * @param devID
     * @return
     */
    List<DeviceAllotRecord> findAllocationRecordByDevID(@Param("devID") String devID);

    /**
     * 根据devID获取设备厂家联系方式
     * @param devID
     * @return
     */
    DevReturnResponse findDeviceContactByDevID(@Param("devID") String devID);

    DeviceMessageRecord getDevMessageRecord(@Param("mesID") String mesID);

    List<DeviceMessageIDMap> getDevMessageIDMap(@Param("mesID") String mesID);

    void updateDevStatusAndManager(AllocationDevDto allocationDevDto);

    void insertAllotRecord(DeviceAllotRecord deviceAllotRecord);

    void insertAllotMessage(DeviceMessageRecord deviceMessageRecord);

    void insertMessageIDMap(DeviceMessageIDMap messageIDMap);

    void updateDevMessageStatus(ConfirmMessageDto confirmMessageDto);

    void updateDevMessageDevNums(@Param("devNums") Integer devNums, @Param("mesID") String mesID);

    List<DeviceMessageRecord> getDevMessageListByManageIDAndMessageStatus(@Param("managerID") String managerID, @Param("mesStatus") Integer mesStatus);

    void dealReturnDeviceByDevID(@Param("devID") String devID);

    void updateSIMStatus(@Param("SIMID") String SIMID, @Param("SIMStatus") Integer SIMStatus);

    List<String> getSIMIDByStatus(@Param("SIMStatus") Integer SIMStatus);

    void insertDevice(DeviceInfo deviceInfo);

    @Insert("insert into deviceFactoryInfo(devFactoryID, devFactoryName, devFactoryPersonName, devFactoryPersonPhone) values(#{devFactoryID}, #{devFactoryName}, #{devFactoryPersonName}, #{devFactoryPersonPhone})")
    void insertDeviceFactory(DevFactoryDto devFactoryDto);

    @Insert("insert into deviceType(devType, devName, devFactoryID) values(#{devType}, #{devName}, #{devFactoryID})")
    void insertDeviceType(DevType devTypeDto);

    @Insert("insert into SIMInfo(SIMID, SIMStatus, SIMName) values(#{SIMID}, #{SIMStatus}, #{SIMName})")
    void insertSIM(SIMInfo simInfo);

    @Select("select devName from deviceType")
    List<String> getAllDevName();
}

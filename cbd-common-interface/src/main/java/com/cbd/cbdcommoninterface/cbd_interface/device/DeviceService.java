package com.cbd.cbdcommoninterface.cbd_interface.device;

import com.cbd.cbdcommoninterface.pojo.device.DevType;
import com.cbd.cbdcommoninterface.request.*;
import com.cbd.cbdcommoninterface.response.*;

import java.util.List;

public interface DeviceService {

    /**
     * 新增设备
     * @param addDeviceRequest
     * @return
     */
    Boolean addDeviceInfo(AddDeviceRequest addDeviceRequest);

    /**
     * 获取当前公司下的设备厂家列表
     * @param companyID
     * @return
     */
    List<String> findAllDevFactoryNameByCompanyID(String companyID);

    /**
     * 根据指定条件获取对应的设备列表(五条件)
     * @param pageDevConditionRequest
     * @return
     */
    PageResponse findDevListByCondition(PageDevConditionRequest pageDevConditionRequest);

    /**
     * 根据设备ID获取设备详细信息
     * @param devID
     * @return
     */
    DevInfoResponse findDevInfoByDevID(String devID);

    /**
     * 获取当前公司下设备名列表及对应的数量,设备状态为入库
     * @param companyID
     * @return
     */
    List<DevNameNumsResponse> findDevNameListAndDevNumsByCompanyID(String companyID);

    /**
     * 根据设备名和当前公司ID获取设备信息列表,设备状态为入库
     * @param devName
     * @param companyID
     * @return
     */
    List<DevInfoResponse> findDevInfoListByDevNameAndCompanyID(String devName, String companyID);

    /**
     * 逻辑删除设备记录，确认设备返厂时用
     * @param devID
     * @return
     */
    Boolean delReturnDeviceByDevID(String devID);

    /**
     * 更新设备状态
     * @param devID
     * @param devStatus
     * @return
     */
    Boolean updateDevStatusByDevIDAndDevStatus(String devID, Integer devStatus);

    /**
     * 根据设备ID和公司名进行单个设备调拨
     * @param allocationDevRequest
     * @return
     */
    Boolean allocationDeviceByDevIDAndCompanyName(AllocationDevRequest allocationDevRequest);

    /**
     * 判断此设备是否可以调拨，即状态为入库
     * @param devID
     * @return
     */
    PermitDeviceResponse judgePermitDevice(String devID);

    /**
     * 根据设备名及数量和公司名进行设备批量调拨
     * @param allocationBathDevRequest
     * @return
     */
    Boolean allocationBatchDeviceByDevNameAndDevNumsAndCompanyName(AllocationBathDevRequest allocationBathDevRequest);

    /**
     * 根据设备ID获得设备调拨记录列表
     * @param devID
     * @return
     */
    List<AllocationRecordResponse> findAllocationRecordByDevID(String devID);

    /**
     * 根据设备ID获取生产厂家联系方式
     * @param devID
     * @return
     */
    DevReturnResponse findDeviceContactByDevID(String devID);

    /**
     *  根据消息ID确认消息并进行设备的调拨
     * @param mesID
     * @return
     */
    Boolean confirmDeviceMessageByMesID(String mesID) ;

    /**
     * 新增合同设备调拨消息,支付成功后调用
     */
    Boolean addContractDeviceMessage(AddContractDevMesRequest contractDevMesRequest);

    /**
     * 判断此消息是否确认
     * @param mesID
     * @return
     */
    Boolean judgeConfirmMessage(String mesID);

    /**
     * 获取分类后的消息，mesStatus可以不传，代表查询所有消息
     * @param messageRequest
     * @return
     */
    PageResponse findMessageByManagerIDAndMessageStatus(GetMessageRequest messageRequest);

    /**
     * 根据设备名获取设备类别信息
     * @param devName
     * @return
     */
    DevType findDevTypeByDevName(String devName);

    /**
     * 获取所有设备名,车佰度平台管理员访问
     * @return
     */
    List<String> getAllDevName();


}

package com.cbd.cbdcommoninterface.cbd_interface.device;

import com.cbd.cbdcommoninterface.request.AllocationBathDevRequest;
import com.cbd.cbdcommoninterface.request.AllocationDevRequest;
import com.cbd.cbdcommoninterface.request.PageDevConditionRequest;
import com.cbd.cbdcommoninterface.response.*;

import java.util.List;

public interface DeviceService {
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
     * 根据设备ID和公司名进行单个设备调拨
     * @param allocationDevRequest
     * @return
     */
    Boolean allocationDeviceByDevIDAndCompanyName(AllocationDevRequest allocationDevRequest);

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


}

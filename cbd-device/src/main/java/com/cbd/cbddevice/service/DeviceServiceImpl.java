package com.cbd.cbddevice.service;


import com.cbd.cbdcommoninterface.cbd_interface.company.CompanyService;
import com.cbd.cbdcommoninterface.cbd_interface.device.DeviceService;
import com.cbd.cbdcommoninterface.cbd_interface.redis.RedisService;
import com.cbd.cbdcommoninterface.dto.AllocationDevDto;
import com.cbd.cbdcommoninterface.dto.ConfirmMessageDto;
import com.cbd.cbdcommoninterface.dto.DevConditionDto;
import com.cbd.cbdcommoninterface.keys.TestKey;
import com.cbd.cbdcommoninterface.pojo.company.CompanyInfo;
import com.cbd.cbdcommoninterface.pojo.device.DevType;
import com.cbd.cbdcommoninterface.pojo.device.DeviceAllotRecord;
import com.cbd.cbdcommoninterface.pojo.device.DeviceInfo;
import com.cbd.cbdcommoninterface.pojo.message.DeviceMessageIDMap;
import com.cbd.cbdcommoninterface.pojo.message.DeviceMessageRecord;
import com.cbd.cbdcommoninterface.request.*;
import com.cbd.cbdcommoninterface.response.*;
import com.cbd.cbdcommoninterface.result.CodeMsg;
import com.cbd.cbdcommoninterface.result.GlobalException;
import com.cbd.cbdcommoninterface.utils.PageUtils;
import com.cbd.cbddevice.dao.DeviceDao;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.*;

@Service
@Slf4j
public class DeviceServiceImpl implements DeviceService {
    @Autowired
    private DeviceDao deviceDao;
    @Autowired
    private CompanyService companyService;

    @Override
    public List<String> findAllDevFactoryNameByCompanyID(String companyID) {
        CompanyInfo cpyInfo = companyService.findCompanyInfoByCompanyID(companyID);
        Integer lft = cpyInfo.getLft();
        Integer rgt = cpyInfo.getRgt();
        List<String> companyNameList = deviceDao.findAllDevFactoryNameByCompanyID(lft, rgt);
        return companyNameList;
    }

    @Override
    public PageResponse findDevListByCondition(PageDevConditionRequest pageDevConditionRequest) {
        /**
         * 给dto赋值，作为查询条件
         * 要放在startPage前面，防止分页查询的sql不是想要的那个
         */
        CompanyInfo cpyInfo = companyService.findCompanyInfoByCompanyID(pageDevConditionRequest.getCompanyID());
        DevConditionDto devConditionDto = new DevConditionDto();
        devConditionDto.setCompanyID(pageDevConditionRequest.getCompanyID());
        devConditionDto.setCompanyCity(pageDevConditionRequest.getCompanyCity());
        devConditionDto.setCompanyDistrict(pageDevConditionRequest.getCompanyDistrict());
        devConditionDto.setCompanylevel(pageDevConditionRequest.getCompanylevel());
        devConditionDto.setCompanyProvince(pageDevConditionRequest.getCompanyProvince());
        devConditionDto.setDevFactoryName(pageDevConditionRequest.getDevFactoryName());
        devConditionDto.setDevStatus(pageDevConditionRequest.getDevStatus());
        devConditionDto.setTimeSort(pageDevConditionRequest.getTimeSort());
        devConditionDto.setLft(cpyInfo.getLft());
        devConditionDto.setRgt(cpyInfo.getRgt());

        PageRequest pageRequest = pageDevConditionRequest.getPageRequest();
        int pageNum = pageRequest.getPageNum();
        int pageSize = pageRequest.getPageSize();
        PageHelper.startPage(pageNum, pageSize);

        /**
         * 封装设备列表,先分页查询devID，然后获取需要返回的信息
         */
        List<String> devIDList = deviceDao.findDevListByCondition(devConditionDto);
        List<PageDevListResponse> pageDevListResponseList = new ArrayList<>();
        for (String devID : devIDList){
            PageDevListResponse temp = new PageDevListResponse();
            temp.setDevID(devID);
            DevType devType = deviceDao.getDevTypeByDevID(devID);
            temp.setDevName(devType.getDevName());
            temp.setDevType(devType.getDevType());
            CompanyInfo companyInfo = companyService.findCompanyInfoByCompanyID(deviceDao.findDeviceInfoByDevID(devID).getCompanyID());
            temp.setCompanyName(companyInfo.getCompanyName());
            pageDevListResponseList.add(temp);
        }

        PageInfo<PageDevListResponse> devListResponsePageInfo = new PageInfo<>(pageDevListResponseList);

        return PageUtils.getPageResponse(devListResponsePageInfo);
    }

    @Override
    public DevInfoResponse findDevInfoByDevID(String devID) {
        DeviceInfo deviceInfo = deviceDao.findDeviceInfoByDevID(devID);

        /**
         * 包装成response对象
         */
        DevInfoResponse devInfoResponse = new DevInfoResponse();
        devInfoResponse.setDevID(devID);
        CompanyInfo companyInfo = companyService.findCompanyInfoByCompanyID(deviceInfo.getCompanyID());
        devInfoResponse.setDevCompanyName(companyInfo.getCompanyName());
        devInfoResponse.setDevGateWayID(deviceInfo.getDevGateWayID());
        devInfoResponse.setDevInputTime(deviceInfo.getDevInputTime());
        //TODO 这边要调人员接口获取
        devInfoResponse.setDevManagerName(companyInfo.getCompanyManagerID());

        DevType devType = deviceDao.getDevTypeByDevID(devID);
        devInfoResponse.setDevName(devType.getDevName());
        devInfoResponse.setDevType(devType.getDevType());
        //enum to String
        DeviceInfo.DevStatus[] devStatuses = DeviceInfo.DevStatus.values();
        devInfoResponse.setDevStatus(devStatuses[deviceInfo.getDevStatus()].toString());

        return devInfoResponse;
    }

    @Override
    public List<DevNameNumsResponse> findDevNameListAndDevNumsByCompanyID(String companyID) {
        DevConditionDto devConditionDto = new DevConditionDto();
        CompanyInfo cpyInfo = companyService.findCompanyInfoByCompanyID(companyID);
        devConditionDto.setRgt(cpyInfo.getRgt());
        devConditionDto.setLft(cpyInfo.getLft());
        devConditionDto.setDevStatus(DeviceInfo.DevStatus.IN.ordinal());

        /**
         * 获取所有子公司在库中的设备id
         */
        List<String> devIDList = deviceDao.findDevListByCondition(devConditionDto);
        List<DevNameNumsResponse> devNameNumsResponseList = new ArrayList<>();
        Map<String, Integer> typeMap = new HashMap<>();

        /**
         * 获得设备名与对应的数量的印射
         */
        for(String devID : devIDList){
            DevType devType = deviceDao.getDevTypeByDevID(devID);
            String devName = devType.getDevName();
            if (typeMap.containsKey(devName)){
                typeMap.put(devName, typeMap.get(devName)+1);
            }else {
                typeMap.put(devName, 1);
            }
        }

        /**
         * 将k:v放入response中
         */
        for (String devName : typeMap.keySet()){
            DevNameNumsResponse temp = new DevNameNumsResponse();
            temp.setDevName(devName);
            temp.setDevNums(typeMap.get(devName));
            devNameNumsResponseList.add(temp);
        }


        return devNameNumsResponseList;
    }

    @Override
    public List<DevInfoResponse> findDevInfoListByDevNameAndCompanyID(String devName, String companyID) {
        DevConditionDto devConditionDto = new DevConditionDto();
        CompanyInfo cpyInfo = companyService.findCompanyInfoByCompanyID(companyID);
        devConditionDto.setRgt(cpyInfo.getRgt());
        devConditionDto.setLft(cpyInfo.getLft());
        devConditionDto.setDevStatus(DeviceInfo.DevStatus.IN.ordinal());

        /**
         * 获取所有子公司在库中的设备id
         */
        List<String> devIDList = deviceDao.findDevListByCondition(devConditionDto);

        /**
         * 根据设备名筛选设备
         */
        List<DevInfoResponse> devInfoResponseList = new ArrayList<>();
        for(String devID : devIDList){
            DevType devType = deviceDao.getDevTypeByDevID(devID);
            String curDevName = devType.getDevName();
            if (curDevName.equals(devName)){
                devInfoResponseList.add(findDevInfoByDevID(devID));
            }
        }

        return devInfoResponseList;
    }

    @Override
    public Boolean allocationDeviceByDevIDAndCompanyName(AllocationDevRequest allocationDevRequest) {
        String devID = allocationDevRequest.getDevID();
        String companyName = allocationDevRequest.getCompanyName();
        String mesID = UUID.randomUUID().toString();
        String devName = deviceDao.getDevTypeByDevID(devID).getDevName();

        Boolean flag = doAllocationDevice(companyName, devID, mesID, devName);
        deviceDao.updateDevMessageDevNums(1,mesID);

        return flag;
    }

    @Override
    @Transactional(rollbackFor=Exception.class)
    public Boolean allocationBatchDeviceByDevNameAndDevNumsAndCompanyName(AllocationBathDevRequest allocationBathDevRequest) {
        DevConditionDto devConditionDto = new DevConditionDto();
        CompanyInfo cpyInfo = companyService.findCompanyInfoByCompanyName(allocationBathDevRequest.getCurCompanyID());
        devConditionDto.setRgt(cpyInfo.getRgt());
        devConditionDto.setLft(cpyInfo.getLft());
        devConditionDto.setDevStatus(DeviceInfo.DevStatus.IN.ordinal());
        /**
         * 获取所有符合条件的设备id
         */
        //先获取所有子公司在库中的设备ID列表
        List<String> devIDList = deviceDao.findDevListByCondition(devConditionDto);
        //再获取符合条件的设备devTypeID
        DevType devType = deviceDao.findDevTypeByDevName(allocationBathDevRequest.getDevName());
        Integer devTypeID = devType.getDevTypeID();
        //一一比较ID列表和devTypeID,根据调拨的数量，确定最后的设备ID列表
        Integer counts = 0;
        List<String> lastDevIDList = new ArrayList<>();
        for(String devID : devIDList){
            Integer curDevTypeID = deviceDao.getDevTypeByDevID(devID).getDevTypeID();
            if (curDevTypeID.equals(devTypeID)){
                lastDevIDList.add(devID);
                counts++;
            }
            if (counts.equals(allocationBathDevRequest.getDevNums())){
                break;
            }
        }

        // 如果此时库中的设备数量不够,则抛出异常
        if (counts < allocationBathDevRequest.getDevNums()){
            throw new GlobalException(CodeMsg.OUT_OF_STOCK);
        }

        //根据确定的设备ID列表调拨设备并记录消息
        String mesID = UUID.randomUUID().toString();
        try {
            for (String curDevID : lastDevIDList){
                doAllocationDevice(allocationBathDevRequest.getDstCompanyName(), curDevID, mesID, allocationBathDevRequest.getDevName());
            }
            deviceDao.updateDevMessageDevNums(counts, mesID);
        }catch (GlobalException e){
            throw e;
        }

        return true;
    }

    @Override
    public List<AllocationRecordResponse> findAllocationRecordByDevID(String devID) {
        List<DeviceAllotRecord> deviceAllotRecordList  = deviceDao.findAllocationRecordByDevID(devID);
        List<AllocationRecordResponse> recordResponseList = new ArrayList<>();
        for(DeviceAllotRecord allotRecord : deviceAllotRecordList){
            AllocationRecordResponse recordResponse = new AllocationRecordResponse();
            recordResponse.setOptTime(allotRecord.getOptTime());
            recordResponse.setSrcCompanyName(companyService.findCompanyInfoByCompanyID(allotRecord.getSrcCompanyID()).getCompanyName());
            recordResponse.setDstCompanyName(companyService.findCompanyInfoByCompanyID(allotRecord.getDstCompanyID()).getCompanyName());
            // TODO 要调人员接口
            recordResponse.setSrcManagerName(allotRecord.getSrcManagerID());

            recordResponseList.add(recordResponse);
        }
        return recordResponseList;
    }

    @Override
    public DevReturnResponse findDeviceContactByDevID(String devID) {
        DevReturnResponse devReturnResponse = deviceDao.findDeviceContactByDevID(devID);
        return devReturnResponse;
    }

    @Override
    public Boolean confirmDeviceMessageByMesID(String mesID) {
        DeviceMessageRecord messageRecord = deviceDao.getDevMessageRecord(mesID);
        /**
         * 如果是普通调拨消息，那么只需要更改设备状态和库管，还有消息状态
         * 如果是合同设备调拨，那么就相当于设备批量调拨
         */
        Integer mesType = messageRecord.getMesType();
        //普通调拨
        if(mesType.equals(DeviceMessageRecord.MessageType.ALLOCATION.ordinal())){
            //获取所有调拨的设备的id
            List<DeviceMessageIDMap> messageIDMapList  = deviceDao.getDevMessageIDMap(mesID);
            //逐一进行设备状态及库管的修改
            for (DeviceMessageIDMap idMap : messageIDMapList){
                AllocationDevDto allocationDevDto = new AllocationDevDto();
                allocationDevDto.setDevID(idMap.getDevID());
                allocationDevDto.setDevStatus(DeviceInfo.DevStatus.IN.ordinal());
                allocationDevDto.setDevManagerID(messageRecord.getDstManagerID());
                deviceDao.updateDevStatusAndManager(allocationDevDto);
            }
        }else if (mesType.equals(DeviceMessageRecord.MessageType.CONTRACT_ALLOCATION.ordinal())){
            AllocationBathDevRequest bathDevRequest = new AllocationBathDevRequest();
            // TODO 要调人员接口
            bathDevRequest.setCurCompanyID(messageRecord.getSrcManagerID());
            bathDevRequest.setDstCompanyName(messageRecord.getDstManagerID());
            bathDevRequest.setDevName(messageRecord.getDevName());
            bathDevRequest.setDevNums(messageRecord.getDevNums());
            //进行设备批量调拨
            allocationBatchDeviceByDevNameAndDevNumsAndCompanyName(bathDevRequest);
        }

        //消息状态修改
        Integer mesStatus = DeviceMessageRecord.MessageStatus.CONFIRMED.ordinal();
        Date mesAcceptTime = new Date();
        ConfirmMessageDto confirmMessageDto = new ConfirmMessageDto();
        confirmMessageDto.setMesID(mesID);
        confirmMessageDto.setMesStatus(mesStatus);
        confirmMessageDto.setMesAcceptTime(mesAcceptTime);
        deviceDao.updateDevMessageStatus(confirmMessageDto);

        return true;
    }

    @Transactional(rollbackFor=Exception.class)
    public Boolean doAllocationDevice(String companyName, String devID, String mesID, String devName) {
        /**
         *  先根据companyName获取调拨的公司设备管理人员ID
         *  并且暂存当前设备管理员ID
         */
        CompanyInfo companyInfo = companyService.findCompanyInfoByCompanyName(companyName);
        //TODO 这边要调人员接口获取
        String dstManagerID = companyInfo.getCompanyManagerID();
        String dstCompanyID = companyInfo.getCompanyID();
        DeviceInfo srcDeviceInfo = deviceDao.findDeviceInfoByDevID(devID);
        String srcManagerID = srcDeviceInfo.getDevManagerID();
        String srcCompanyID = srcDeviceInfo.getCompanyID();
        /**
         * 更改设备状态为出库
         * 库管信息待设备接收后更改
         */
        AllocationDevDto allocationDevDto = new AllocationDevDto();
        allocationDevDto.setDevID(devID);
        allocationDevDto.setDevStatus(DeviceInfo.DevStatus.OUT.ordinal());
        DeviceAllotRecord deviceAllotRecord = new DeviceAllotRecord();
        try{
            deviceDao.updateDevStatusAndManager(allocationDevDto);
            /**
             * 新增调拨记录
             */
            deviceAllotRecord.setDevID(allocationDevDto.getDevID());
            deviceAllotRecord.setDstCompanyID(dstCompanyID);
            deviceAllotRecord.setDstManagerID(dstManagerID);
            deviceAllotRecord.setOptTime(new Date());
            deviceAllotRecord.setSrcCompanyID(srcCompanyID);
            deviceAllotRecord.setSrcManagerID(srcManagerID);
            deviceDao.insertAllotRecord(deviceAllotRecord);

            /**
             * 新增设备调拨消息
             */
            insertAllotMessage(DeviceMessageRecord.MessageType.ALLOCATION.ordinal(), srcManagerID, dstManagerID, devID, mesID);
        }catch (Exception e){
            throw new GlobalException(CodeMsg.SERVER_ERROR);
        }

        return true;
    }

    public void insertAllotMessage(int type, String srcManagerID, String dstManagerID, String devID, String mesID) {
        // 这个if主要是防止批量调拨时消息重复插入
        if(deviceDao.getDevMessageRecord(mesID) == null){
            DeviceMessageRecord deviceMessageRecord = new DeviceMessageRecord();
            deviceMessageRecord.setMesID(mesID);
            deviceMessageRecord.setMesType(type);
            deviceMessageRecord.setSrcManagerID(srcManagerID);
            deviceMessageRecord.setDstManagerID(dstManagerID);
            // 新建消息为未确认状态
            deviceMessageRecord.setMesStatus(DeviceMessageRecord.MessageStatus.UNCONFIRMED.ordinal());
            deviceMessageRecord.setMesSendTime(new Date());
            deviceDao.insertAllotMessage(deviceMessageRecord);
        }

        /**
         * 增加消息ID与设备ID的印射关系
         */
        DeviceMessageIDMap messageIDMap = new DeviceMessageIDMap();
        messageIDMap.setMesID(mesID);
        messageIDMap.setDevID(devID);
        deviceDao.insertMessageIDMap(messageIDMap);
    }


}

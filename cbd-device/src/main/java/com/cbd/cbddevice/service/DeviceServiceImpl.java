package com.cbd.cbddevice.service;


import com.alibaba.fastjson.JSON;
import com.cbd.cbdcommoninterface.cbd_interface.MQ.MQSender;
import com.cbd.cbdcommoninterface.cbd_interface.company.CompanyService;
import com.cbd.cbdcommoninterface.cbd_interface.device.DeviceService;
import com.cbd.cbdcommoninterface.cbd_interface.message.MessageService;
import com.cbd.cbdcommoninterface.cbd_interface.salesapp.user.UserService;
import com.cbd.cbdcommoninterface.cbd_interface.user.IUserService;
import com.cbd.cbdcommoninterface.dto.*;
import com.cbd.cbdcommoninterface.enums.ReadFlagEnum;
import com.cbd.cbdcommoninterface.pojo.company.CompanyInfo;
import com.cbd.cbdcommoninterface.pojo.device.DevType;
import com.cbd.cbdcommoninterface.pojo.device.DeviceAllotRecord;
import com.cbd.cbdcommoninterface.pojo.device.DeviceInfo;
import com.cbd.cbdcommoninterface.pojo.device.SIMInfo;
import com.cbd.cbdcommoninterface.pojo.message.DeviceMessageIDMap;
import com.cbd.cbdcommoninterface.pojo.message.DeviceMessageRecord;
import com.cbd.cbdcommoninterface.request.*;
import com.cbd.cbdcommoninterface.response.*;
import com.cbd.cbdcommoninterface.result.CodeMsg;
import com.cbd.cbdcommoninterface.result.GlobalException;
import com.cbd.cbdcommoninterface.utils.*;
import com.cbd.cbddevice.dao.DeviceDao;
import com.github.pagehelper.Page;
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
    @Autowired
    private MQSender mqSender;
    @Autowired
    private IUserService userService;

    @Override
    @Transactional(rollbackFor=Exception.class)
    public Boolean addDeviceInfo(AddDeviceRequest addDeviceRequest) {
        try{
            if (addDeviceRequest.getSIMName().equals("")){
                int devNums = addDeviceRequest.getDevNums();
                for (int i = 0; i < devNums; i++) {
                    DeviceInfo deviceInfo = new DeviceInfo();
                    String devID = UUIDUtils.getUUID();
                    //选取一张SIM卡配对
                    List<String> SIMIDList = deviceDao.getSIMIDByStatus(SIMInfo.SIMStatus.UNPAIR.ordinal());
                    //SIM卡库存不足
                    if(SIMIDList.isEmpty()){
                        throw new GlobalException(CodeMsg.OUT_OF_SIM_STOCK);
                    }
                    String SIMID = SIMIDList.get(0);
                    //更新SIM卡状态
                    deviceDao.updateSIMStatus(SIMID, SIMInfo.SIMStatus.IN.ordinal());

                    deviceInfo.setDevID(devID);
                    deviceInfo.setSIMID(SIMID);
                    deviceInfo.setCompanyID(addDeviceRequest.getCompanyID());
                    deviceInfo.setDevGateWayID(addDeviceRequest.getGateWayID());
                    deviceInfo.setDevManagerID(addDeviceRequest.getManagerID());
                    deviceInfo.setDevStatus(DeviceInfo.DevStatus.IN.ordinal());

                    DevType devType = deviceDao.findDevTypeByDevName(addDeviceRequest.getDevName());
                    //说明此设备未收录
                    if(devType == null){
                        //添加至设备厂家表
                        String devFactoryID = UUIDUtils.getUUID();
                        DevFactoryDto devFactoryDto = new DevFactoryDto();
                        devFactoryDto.setDevFactoryID(devFactoryID);
                        devFactoryDto.setDevFactoryName(addDeviceRequest.getDevFactoryName());
                        devFactoryDto.setDevFactoryPersonName(addDeviceRequest.getDevFactoryPersonName());
                        devFactoryDto.setDevFactoryPersonPhone(addDeviceRequest.getDevFactoryPersonPhone());
                        deviceDao.insertDeviceFactory(devFactoryDto);
                        //添加至设备类型表中
                        DevType devTypeDto = new DevType();
                        devTypeDto.setDevFactoryID(devFactoryID);
                        devTypeDto.setDevName(addDeviceRequest.getDevName());
                        devTypeDto.setDevType(addDeviceRequest.getDevType());
                        deviceDao.insertDeviceType(devTypeDto);

                        devType = deviceDao.findDevTypeByDevName(addDeviceRequest.getDevName());
                    }
                    deviceInfo.setDevTypeID(devType.getDevTypeID());
                    deviceInfo.setDevFactoryID(devType.getDevFactoryID());

                    deviceInfo.setDevInputTime(new Date());
                    deviceDao.insertDevice(deviceInfo);
                }
            }else {
                int SIMNums = addDeviceRequest.getSIMNums();
                for (int i = 0; i < SIMNums; i++) {
                    String SIMID = UUIDUtils.getUUID();
                    SIMInfo simInfo = new SIMInfo();
                    simInfo.setSIMID(SIMID);
                    simInfo.setSIMName(addDeviceRequest.getSIMName());
                    simInfo.setSIMStatus(SIMInfo.SIMStatus.UNPAIR.ordinal());
                    deviceDao.insertSIM(simInfo);
                }
            }
        }catch (GlobalException e){
            throw e;
        }

        return true;
    }

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
        devConditionDto.setCompanyCity(pageDevConditionRequest.getCompanyCity());
        devConditionDto.setCompanyDistrict(pageDevConditionRequest.getCompanyDistrict());
        devConditionDto.setCompanylevel(pageDevConditionRequest.getCompanylevel());
        devConditionDto.setCompanyProvince(pageDevConditionRequest.getCompanyProvince());
        devConditionDto.setDevFactoryName(pageDevConditionRequest.getDevFactoryName());
        devConditionDto.setDevStatus(pageDevConditionRequest.getDevStatus());
        devConditionDto.setTimeSort(pageDevConditionRequest.getTimeSort());
        log.info("时间排序:{}",pageDevConditionRequest.getTimeSort());
        devConditionDto.setLft(cpyInfo.getLft());
        devConditionDto.setRgt(cpyInfo.getRgt());

        PageRequest pageRequest = pageDevConditionRequest.getPageRequest();
        int pageNum = pageRequest.getPageNum();
        int pageSize = pageRequest.getPageSize();
        Page page = PageHelper.startPage(pageNum, pageSize);

        /**
         * 封装设备列表,先分页查询devID，然后获取需要返回的信息
         */
        List<String> devIDList = deviceDao.findDevListByCondition(devConditionDto);
        List<PageDevListResponse> pageDevListResponseList = new ArrayList<>();
        for (String devID : devIDList){
            PageDevListResponse temp = new PageDevListResponse();
            temp.setDevID(devID);
            temp.setDevStatus(deviceDao.findDeviceInfoByDevID(devID).getDevStatus());
            DevType devType = deviceDao.getDevTypeByDevID(devID);
            temp.setDevName(devType.getDevName());
            temp.setDevType(devType.getDevType());
            CompanyInfo companyInfo = companyService.findCompanyInfoByCompanyID(deviceDao.findDeviceInfoByDevID(devID).getCompanyID());
            temp.setCompanyName(companyInfo.getCompanyName());
            pageDevListResponseList.add(temp);
        }

        PageInfo<PageDevListResponse> devListResponsePageInfo = new PageInfo<>(pageDevListResponseList);
        devListResponsePageInfo.setPages(page.getPages());
        devListResponsePageInfo.setTotal(page.getTotal());

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
        devInfoResponse.setSIMID(deviceInfo.getSIMID());
        CompanyInfo companyInfo = companyService.findCompanyInfoByCompanyID(deviceInfo.getCompanyID());
        devInfoResponse.setDevCompanyName(companyInfo.getCompanyName());
        devInfoResponse.setDevGateWayID(deviceInfo.getDevGateWayID());
        devInfoResponse.setDevInputTime(deviceInfo.getDevInputTime());
        //TODO 这边要调人员接口获取
        devInfoResponse.setDevManagerName(companyInfo.getCompanyManagerID().toString());

        DevType devType = deviceDao.getDevTypeByDevID(devID);
        devInfoResponse.setDevName(devType.getDevName());
        devInfoResponse.setDevType(devType.getDevType());
        //enum to String
        Integer status = deviceInfo.getDevStatus();
        String returnStatus = "";
        if (status.equals(DeviceInfo.DevStatus.IN.ordinal())){
            returnStatus = "库中";
        }else if (status.equals(DeviceInfo.DevStatus.OUT.ordinal())){
            returnStatus = "出库";
        }else if (status.equals(DeviceInfo.DevStatus.USE.ordinal())){
            returnStatus = "使用中";
        }else {
            returnStatus = "待返厂";
        }
        devInfoResponse.setDevStatus(returnStatus);

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
        CompanyInfo cpyInfo = companyService.findCompanyInfoByCompanyID(companyID);
        Integer devTypeID = deviceDao.findDevTypeByDevName(devName).getDevTypeID();
        DevNameDto devNameDto = new DevNameDto();
        devNameDto.setDevTypeID(devTypeID);
        devNameDto.setLft(cpyInfo.getLft());
        devNameDto.setRgt(cpyInfo.getRgt());

        /**
         * 获取所有符合条件的设备id
         */
        List<String> devIDList = deviceDao.findDeviceIDByDevName(devNameDto);

        /**
         * 根据设备名筛选设备
         */
        List<DevInfoResponse> devInfoResponseList = new ArrayList<>();
        for(String devID : devIDList){
            devInfoResponseList.add(findDevInfoByDevID(devID));
        }

        return devInfoResponseList;
    }

    @Override
    public Boolean delReturnDeviceByDevID(String devID) {
        try {
            deviceDao.dealReturnDeviceByDevID(devID);
        }catch (GlobalException e){
            throw e;
        }

        return true;
    }

    @Override
    @Transactional(rollbackFor=Exception.class)
    public Boolean updateDevStatusByDevIDAndDevStatus(String devID, Integer devStatus) {
        try {
            AllocationDevDto devDto = new AllocationDevDto();
            devDto.setDevStatus(devStatus);
            devDto.setDevID(devID);
            deviceDao.updateDevStatusAndManager(devDto);

            //更改对应SIMStatus
            String SIMID = findDevInfoByDevID(devID).getSIMID();
            deviceDao.updateSIMStatus(SIMID, devStatus);
        }catch (GlobalException e){
            throw e;
        }

        return true;
    }

    @Override
    public Boolean allocationDeviceByDevIDAndCompanyName(AllocationDevRequest allocationDevRequest) {
        String devID = allocationDevRequest.getDevID();
        String companyName = allocationDevRequest.getCompanyName();
        String mesID = UUIDUtils.getUUID();
        String devName = deviceDao.getDevTypeByDevID(devID).getDevName();

        Boolean flag = doAllocationDevice(companyName, devID, mesID, devName);
        //单个设备，所以消息列表中设备数量为1
        deviceDao.updateDevMessageDevNums(1,mesID);

        /**
         * 推送消息
         */
        pushMsg(allocationDevRequest.getDoAllocationCompanyID(),companyService.findCompanyInfoByCompanyName(companyName).getCompanyManagerID(), mesID);
        return flag;
    }

    @Override
    public PermitDeviceResponse judgePermitDevice(String devID) {
        Integer devStatus = deviceDao.findDeviceInfoByDevID(devID).getDevStatus();
        PermitDeviceResponse permitDeviceResponse = new PermitDeviceResponse();
        permitDeviceResponse.setFlag(true);
        if (devStatus != DeviceInfo.DevStatus.IN.ordinal()){
            permitDeviceResponse.setFlag(false);
            if (devStatus == DeviceInfo.DevStatus.OUT.ordinal()){
                permitDeviceResponse.setDevStatus("出库");
            }else if (devStatus == DeviceInfo.DevStatus.USE.ordinal()){
                permitDeviceResponse.setDevStatus("使用中");
            }else {
                permitDeviceResponse.setDevStatus("待返厂维修");
            }
        }

        return permitDeviceResponse;
    }

    @Override
    @Transactional(rollbackFor=Exception.class)
    public Boolean allocationBatchDeviceByDevNameAndDevNumsAndCompanyName(AllocationBathDevRequest allocationBathDevRequest) {
        DevConditionDto devConditionDto = new DevConditionDto();
        CompanyInfo cpyInfo = companyService.findCompanyInfoByCompanyID(allocationBathDevRequest.getCurCompanyID());
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
        String mesID = UUIDUtils.getUUID();
        try {
            for (String curDevID : lastDevIDList){
                doAllocationDevice(allocationBathDevRequest.getDstCompanyName(), curDevID, mesID, allocationBathDevRequest.getDevName());
            }
            deviceDao.updateDevMessageDevNums(counts, mesID);
            /**
             * 推送消息
             */
            pushMsg(allocationBathDevRequest.getCurCompanyID(), companyService.findCompanyInfoByCompanyName(allocationBathDevRequest.getDstCompanyName()).getCompanyManagerID(), mesID);
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
            recordResponse.setSrcManagerName(allotRecord.getSrcManagerID().toString());

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
            String dstCpyID = userService.findUserCPYIDByUserID(messageRecord.getDstManagerID());
            //获取所有调拨的设备的id
            List<DeviceMessageIDMap> messageIDMapList  = deviceDao.getDevMessageIDMap(mesID);
            //逐一进行设备状态及库管,公司的修改
            for (DeviceMessageIDMap idMap : messageIDMapList){
                ConfirmDevDto confirmDevDto = new ConfirmDevDto();
                confirmDevDto.setDevID(idMap.getDevID());
                confirmDevDto.setDevStatus(DeviceInfo.DevStatus.IN.ordinal());
                confirmDevDto.setDevManagerID(messageRecord.getDstManagerID());
                confirmDevDto.setCompanyID(dstCpyID);
                deviceDao.updateDstDev(confirmDevDto);
            }
        }else if (mesType.equals(DeviceMessageRecord.MessageType.CONTRACT_ALLOCATION.ordinal())){
            AllocationBathDevRequest bathDevRequest = new AllocationBathDevRequest();
            bathDevRequest.setCurCompanyID(userService.findUserCPYIDByUserID(messageRecord.getSrcManagerID()));
            log.info("curCompanyID:{}",bathDevRequest.getCurCompanyID());
            String dstCpyID = userService.findUserCPYIDByUserID(messageRecord.getDstManagerID());
            log.info("curCompanyID:{}",bathDevRequest.getCurCompanyID());
            bathDevRequest.setDstCompanyName(companyService.findCompanyInfoByCompanyID(dstCpyID).getCompanyName());
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

    @Override
    @Transactional(rollbackFor=Exception.class)
    public Boolean addContractDeviceMessage(AddContractDevMesRequest contractDevMesRequest) {
        try{
            String mesID = UUIDUtils.getUUID();
            DeviceMessageRecord deviceMessageRecord = new DeviceMessageRecord();
            deviceMessageRecord.setMesID(mesID);
            deviceMessageRecord.setMesType(DeviceMessageRecord.MessageType.CONTRACT_ALLOCATION.ordinal());
            // TODO 要调人事接口
            //srcComapny为车百度平台
            String srcCompanyName = "车佰度平台";
            deviceMessageRecord.setSrcManagerID(companyService.findCompanyInfoByCompanyName(srcCompanyName).getCompanyManagerID());
            // TODO 要调人事接口
            deviceMessageRecord.setDstManagerID(companyService.findCompanyInfoByCompanyID(contractDevMesRequest.getCompanyID()).getCompanyManagerID());
            // 新建消息为未确认状态
            deviceMessageRecord.setMesStatus(DeviceMessageRecord.MessageStatus.UNCONFIRMED.ordinal());
            deviceMessageRecord.setDevName(contractDevMesRequest.getDevName());
            deviceMessageRecord.setMesSendTime(new Date());
            deviceDao.insertAllotMessage(deviceMessageRecord);
            deviceDao.updateDevMessageDevNums(contractDevMesRequest.getDevNums(), mesID);

            /**
             * 向甲方公司推送消息
             */
            ChatMessage chatMessage = new ChatMessage();
            chatMessage.setMesID(mesID);
            chatMessage.setSenderId(deviceMessageRecord.getSrcManagerID().toString());
            chatMessage.setReceiverId(deviceMessageRecord.getDstManagerID().toString());

            String pushMsg = "合同已完成支付，如需获取合同规定的设备，请点击“获取设备”,将由本平台调拨设备至贵公司，谢谢！！！";
            chatMessage.setContent(pushMsg);
            chatMessage.setMsgType(ChatMessage.MsgType.CONTRACT_ALLOCATION.toString());
            BusinessMessage message = new BusinessMessage(BusinessType.CBD_BUSINESS_QUEUE, JSON.toJSON(chatMessage));
            mqSender.send(message);

        }catch (Exception e){
            throw new GlobalException(CodeMsg.SERVER_ERROR);
        }

        return true;
    }

    @Override
    public DevType findDevTypeByDevName(String devName) {
        return deviceDao.findDevTypeByDevName(devName);
    }

    @Override
    public List<String> getAllDevName(String companyID) {
        log.info("companyID:{}",companyID);
        List<String> devIDList = deviceDao.getAllDevName(companyID);
        List<String> devNameList = new ArrayList<>();
        Map<String, Integer> exitMap = new HashMap<>();
        for (String devID : devIDList){
            String devName = deviceDao.getDevTypeByDevID(devID).getDevName();
            if (!exitMap.containsKey(devName)){
                devNameList.add(devName);
                exitMap.put(devName, 1);
            }

        }
        return devNameList;
    }

    @Override
    public List<String> getAllDevManagerIDList() {
        List<String> devManagerIDList = deviceDao.getAllDevManagerIDList();
        return devManagerIDList;
    }

    @Override
    public Integer getNeedReturnDeviceCounts(String managerID) {
        Integer counts = deviceDao.getNeedReturnDeviceCounts(managerID);
        return counts;
    }

    @Override
    public Integer getDevMaxNums(String companyID, String devName) {
        CompanyInfo cpyInfo = companyService.findCompanyInfoByCompanyID(companyID);
        Integer devTypeID = deviceDao.findDevTypeByDevName(devName).getDevTypeID();
        DevNameDto devNameDto = new DevNameDto();
        devNameDto.setDevTypeID(devTypeID);
        devNameDto.setLft(cpyInfo.getLft());
        devNameDto.setRgt(cpyInfo.getRgt());

        return deviceDao.getDevNumsByDevName(devNameDto);
    }

    @Override
    public DeviceMessageRecord getDevMessageRecord(String mesID) {
        return deviceDao.getDevMessageRecord(mesID);
    }

    @Transactional(rollbackFor=Exception.class)
    public Boolean doAllocationDevice(String companyName, String devID, String mesID, String devName) {
        /**
         *  先根据companyName获取调拨的公司设备管理人员ID
         *  并且暂存当前设备管理员ID
         */
        CompanyInfo companyInfo = companyService.findCompanyInfoByCompanyName(companyName);
        //TODO 这边要调人员接口获取
        Integer dstManagerID = companyInfo.getCompanyManagerID();
        String dstCompanyID = companyInfo.getCompanyID();
        DeviceInfo srcDeviceInfo = deviceDao.findDeviceInfoByDevID(devID);
        Integer srcManagerID = srcDeviceInfo.getDevManagerID();
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
            insertAllotMessage(DeviceMessageRecord.MessageType.ALLOCATION.ordinal(), srcManagerID, dstManagerID, devID, mesID, devName);

        }catch (Exception e){
            e.printStackTrace();
            throw new GlobalException(CodeMsg.SERVER_ERROR);
        }

        return true;
    }

    @Transactional(rollbackFor=Exception.class)
    public void insertAllotMessage(int type, Integer srcManagerID, Integer dstManagerID, String devID, String mesID, String devName) {
        // 这个if主要是防止批量调拨时消息重复插入
        try{
            if(deviceDao.getDevMessageRecord(mesID) == null){
                DeviceMessageRecord deviceMessageRecord = new DeviceMessageRecord();
                deviceMessageRecord.setMesID(mesID);
                deviceMessageRecord.setMesType(type);
                deviceMessageRecord.setSrcManagerID(srcManagerID);
                deviceMessageRecord.setDstManagerID(dstManagerID);
                // 新建消息为未确认状态
                deviceMessageRecord.setMesStatus(DeviceMessageRecord.MessageStatus.UNCONFIRMED.ordinal());
                deviceMessageRecord.setDevName(devName);
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
        }catch (Exception e){
            e.printStackTrace();
            throw new GlobalException(CodeMsg.SERVER_ERROR);
        }
    }

    private void pushMsg(String doAllocationCompanyID, Integer dstManagerID, String mesID){
        /**
         * 推送消息
         */
        ChatMessage chatMessage = new ChatMessage();
        chatMessage.setMesID(mesID);
        //这块的srcManagerID不一定是实际调拨的人，因为父级公司也可以调拨子级公司的设备
        CompanyInfo doAllocationCpy = companyService.findCompanyInfoByCompanyID(doAllocationCompanyID);
        chatMessage.setSenderId(doAllocationCpy.getCompanyManagerID().toString());
        chatMessage.setReceiverId(dstManagerID.toString());

        String pushMsg = doAllocationCpy.getCompanyName()+"正在向您调拨一批设备，收到设备后请尽快确认，谢谢！！！";
        chatMessage.setContent(pushMsg);
        chatMessage.setMsgType(ChatMessage.MsgType.ALLOCATION.toString());
        BusinessMessage message = new BusinessMessage(BusinessType.CBD_BUSINESS_QUEUE, JSON.toJSON(chatMessage));
        mqSender.send(message);
    }

}

package com.cbd.cbdcontract.service;

import com.alibaba.fastjson.JSON;
import com.cbd.cbdcommoninterface.cbd_interface.MQ.MQSender;
import com.cbd.cbdcommoninterface.cbd_interface.company.CompanyService;
import com.cbd.cbdcommoninterface.cbd_interface.contract.ContractService;
import com.cbd.cbdcommoninterface.cbd_interface.device.DeviceService;
import com.cbd.cbdcommoninterface.cbd_interface.redis.RedisService;
import com.cbd.cbdcommoninterface.dto.ContractConditionDto;
import com.cbd.cbdcommoninterface.dto.DevConditionDto;
import com.cbd.cbdcommoninterface.keys.OrderTypeKey;
import com.cbd.cbdcommoninterface.keys.RenewKey;
import com.cbd.cbdcommoninterface.pojo.company.CompanyInfo;
import com.cbd.cbdcommoninterface.pojo.contract.ContractInfo;
import com.cbd.cbdcommoninterface.pojo.contract.ContractType;
import com.cbd.cbdcommoninterface.pojo.device.DevType;
import com.cbd.cbdcommoninterface.request.*;
import com.cbd.cbdcommoninterface.response.*;
import com.cbd.cbdcommoninterface.result.CodeMsg;
import com.cbd.cbdcommoninterface.result.GlobalException;
import com.cbd.cbdcommoninterface.utils.*;
import com.cbd.cbdcontract.dao.ContractDao;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
@Slf4j
public class ContractServiceImpl implements ContractService {
    @Autowired
    CompanyService companyService;
    @Autowired
    ContractDao contractDao;
    @Autowired
    DeviceService deviceService;
    @Autowired
    RedisService redisService;
    @Autowired
    MQSender mqSender;

    @Override
    public List<String> findContractTypeNameByCompanyID(String companyID) {
        CompanyInfo cpyInfo = companyService.findCompanyInfoByCompanyID(companyID);
        Integer lft = cpyInfo.getLft();
        Integer rgt = cpyInfo.getRgt();

        return contractDao.findContractTypeNameByCompanyID(lft, rgt);
    }

    @Override
    public PageResponse findContractListByCondition(PageContractConditionRequest contractConditionRequest) {
        /**
         * 给dto赋值，作为查询条件
         * 要放在startPage前面，防止分页查询的sql不是想要的那个
         */
        CompanyInfo cpyInfo = companyService.findCompanyInfoByCompanyID(contractConditionRequest.getCompanyID());
        ContractConditionDto contractConditionDto = new ContractConditionDto();
        contractConditionDto.setCompanyCity(contractConditionRequest.getCompanyCity());
        contractConditionDto.setCompanyDistrict(contractConditionRequest.getCompanyDistrict());
        contractConditionDto.setCompanylevel(contractConditionRequest.getCompanylevel());
        contractConditionDto.setCompanyProvince(contractConditionRequest.getCompanyProvince());
        contractConditionDto.setContractStatus(contractConditionRequest.getContractStatus());
        contractConditionDto.setTimeSort(contractConditionRequest.getTimeSort());
        contractConditionDto.setContractTypeName(contractConditionRequest.getContractTypeName());
        contractConditionDto.setLft(cpyInfo.getLft());
        contractConditionDto.setRgt(cpyInfo.getRgt());

        PageRequest pageRequest = contractConditionRequest.getPageRequest();
        int pageNum = pageRequest.getPageNum();
        int pageSize = pageRequest.getPageSize();
        Page page = PageHelper.startPage(pageNum, pageSize);

        /**
         * 封装合同列表,先分页查询contractID，然后获取需要返回的信息
         */
        List<String> contractIDList = contractDao.findContractListByCondition(contractConditionDto);
        List<PageContractListResponse> contractListResponseList = new ArrayList<>();
        for (String contractID : contractIDList){
            ContractInfo contractInfo = contractDao.findContractInfoByContractID(contractID);
            PageContractListResponse temp = new PageContractListResponse();
            temp.setContractID(contractID);

            CompanyInfo companyInfo = companyService.findCompanyInfoByCompanyID(contractInfo.getCompanyID());
            temp.setContractCompanyName(companyInfo.getCompanyName());

            DevType devType = contractDao.getDevType(contractInfo.getDevTypeID());
            temp.setDevName(devType.getDevName());

            temp.setCreateTime(contractInfo.getCreateTime());

            temp.setContractTypeName(contractDao.getContractTypeName(contractInfo.getContractTypeID()));

            contractListResponseList.add(temp);
        }

        PageInfo<PageContractListResponse> devListResponsePageInfo = new PageInfo<>(contractListResponseList);
        devListResponsePageInfo.setTotal(page.getTotal());
        devListResponsePageInfo.setPages(page.getPages());

        return PageUtils.getPageResponse(devListResponsePageInfo);

    }

    @Override
    public ContractInfoResponse findContractInfoByContractID(String contractID) {
        ContractInfo contractInfo = contractDao.findContractInfoByContractID(contractID);
        ContractInfoResponse contractInfoResponse = new ContractInfoResponse();
        contractInfoResponse.setContractID(contractID);

        CompanyInfo companyInfo = companyService.findCompanyInfoByCompanyID(contractInfo.getCompanyID());
        contractInfoResponse.setContractCompanyName(companyInfo.getCompanyName());
        //存在未派发至4s店的合同
        String shopID = contractInfo.getShopID();
        if (shopID!=null && !shopID.isEmpty()){
            CompanyInfo shopInfo = companyService.findCompanyInfoByCompanyID(shopID);
            contractInfoResponse.setShopName(shopInfo.getCompanyName());
            contractInfoResponse.setShopAddress(shopInfo.getCompanyProvince()+shopInfo.getCompanyCity()+shopInfo.getCompanyDistrict());
        }

        String contractTypeName = contractDao.getContractTypeName(contractInfo.getContractTypeID());
        contractInfoResponse.setContractTypeName(contractTypeName);

        DevType devType = contractDao.getDevType(contractInfo.getDevTypeID());
        contractInfoResponse.setDevName(devType.getDevName());
        contractInfoResponse.setDevTypeName(devType.getDevType());

        String contractStatus = "";
        if (contractInfo.getContractStatus() == ContractInfo.ContractStatus.UNPAID.ordinal()){
            contractStatus = "新建未支付";
        }else if (contractInfo.getContractStatus() == ContractInfo.ContractStatus.PAID.ordinal()){
            contractStatus = "已支付生效中";
        }else {
            contractStatus = "合同已到期";
        }
        contractInfoResponse.setContractStatus(contractStatus);

        Calendar c = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String date = sdf.format(contractInfo.getCreateTime());
        try {
            c.setTime(sdf.parse(date));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        c.add(Calendar.MONTH, (int) (contractInfo.getServerYears()*12));
        String expireDate = new SimpleDateFormat("yyyy-MM-dd").format(c.getTime());
        contractInfoResponse.setExpireDate(expireDate);


        contractInfoResponse.setDellFee(contractInfo.getDellFee());
        contractInfoResponse.setDevNums(contractInfo.getDevNums());
        // TODO 需要调用人事接口
        contractInfoResponse.setPartyAPersonName(contractInfo.getPartyAPersonID().toString());
        contractInfoResponse.setServerFee(contractInfo.getServerFee());
        contractInfoResponse.setServerYears(contractInfo.getServerYears());

        return contractInfoResponse;
    }

    @Override
    public List<PageContractListResponse> findUsingContractListByCompanyID(String companyID) {
        /**
         * 给dto赋值，作为查询条件
         */
        CompanyInfo cpyInfo = companyService.findCompanyInfoByCompanyID(companyID);
        ContractConditionDto contractConditionDto = new ContractConditionDto();
        contractConditionDto.setContractStatus(ContractInfo.ContractStatus.PAID.ordinal());
        contractConditionDto.setTimeSort(ContractConditionDto.timeSort.DESC.ordinal());
        contractConditionDto.setLft(cpyInfo.getLft());
        contractConditionDto.setRgt(cpyInfo.getRgt());

        /**
         * 查询出来对应的ContractID，然后再获取包装返回的信息
         */
        List<String> contractIDList = contractDao.findContractListByCondition(contractConditionDto);
        List<PageContractListResponse> contractListResponseList = new ArrayList<>();
        for (String contractID : contractIDList){
            ContractInfo contractInfo = contractDao.findContractInfoByContractID(contractID);
            PageContractListResponse temp = new PageContractListResponse();
            temp.setContractID(contractID);

            CompanyInfo companyInfo = companyService.findCompanyInfoByCompanyID(contractInfo.getCompanyID());
            temp.setContractCompanyName(companyInfo.getCompanyName());

            DevType devType = contractDao.getDevType(contractInfo.getDevTypeID());
            temp.setDevName(devType.getDevName());

            temp.setCreateTime(contractInfo.getCreateTime());
            temp.setContractTypeName(contractDao.getContractTypeName(contractInfo.getContractTypeID()));

            contractListResponseList.add(temp);
        }

        return contractListResponseList;
    }

    @Override
    @Transactional(rollbackFor=Exception.class)
    public Boolean distributeContractByContractIDAndCompanyName(DistributeContractRequest contractRequest) {
        //合同状态效验
        if(contractDao.findContractInfoByContractID(contractRequest.getContractID()).getContractStatus() != ContractInfo.ContractStatus.PAID.ordinal()){
            throw new GlobalException(CodeMsg.CONTRACT_STATUS_FAILURE);
        }
        /**
         * 获取调拨公司的信息
         */
        CompanyInfo dstCpy = companyService.findCompanyInfoByCompanyName(contractRequest.getCompanyName());
        log.info("目标公司名:{}", dstCpy.getCompanyName());

        String companyID = dstCpy.getCompanyID();
        String shopID = null;
        //如果派发的公司为4s店，则赋值给shopID
        if(dstCpy.getCompanylevel().equals(CompanyInfo.Companylevel.DISCPY.ordinal())){
            shopID = dstCpy.getCompanyID();
        }
        try {
            contractDao.updateContractByDistribute(contractRequest.getContractID(),companyID, shopID);
            /**
             * 推送消息
             */
            ChatMessage chatMessage = new ChatMessage();
            chatMessage.setMesID(UUIDUtils.getUUID());
            CompanyInfo doAllocationCpy = companyService.findCompanyInfoByCompanyID(contractRequest.getDoAllocationCompanyID());
            chatMessage.setSenderId(doAllocationCpy.getCompanyManagerID().toString());
            chatMessage.setReceiverId(dstCpy.getCompanyManagerID().toString());

            String pushMsg = contractRequest.getContractID()+":"+doAllocationCpy.getCompanyName()+"已向您派发合同，点击“查看详细信息”查看合同详细信息，谢谢！！！";
            chatMessage.setContent(pushMsg);
            chatMessage.setMsgType(ChatMessage.MsgType.DISTRIBUTE_CONTRACT.toString());
            BusinessMessage message = new BusinessMessage(BusinessType.CBD_BUSINESS_QUEUE, JSON.toJSON(chatMessage));
            mqSender.send(message);
        }catch (Exception e){
            throw new GlobalException(CodeMsg.SERVER_ERROR);
        }
        return true;
    }

    @Override
    public List<Float> getRenewMoneyByContractID(String contractID) {
        ContractInfo contractInfo = contractDao.findContractInfoByContractID(contractID);
        //获取当前合同的服务费用和时长
        Float serverFee = contractInfo.getServerFee();
        Float serverYears = contractInfo.getServerYears();

        List<Float> renewMoneyList = new ArrayList<>();
        // 计算半年金额为基本单元
        Float halfMoney = (serverYears/0.5f)*serverFee;

        // 三种年限金额放入list
        renewMoneyList.add(halfMoney);
        renewMoneyList.add(halfMoney*2);
        renewMoneyList.add(halfMoney*4);

        return renewMoneyList;
    }

    @Override
    public UnpaidContractInfoResponse getUnPaidContractInfoByCompanyID(String companyID) {
        //获取未支付合同id
        ContractConditionDto conditionDto = new ContractConditionDto();
        conditionDto.setContractStatus(ContractInfo.ContractStatus.UNPAID.ordinal());
        CompanyInfo cpyInfo = companyService.findCompanyInfoByCompanyID(companyID);
        Integer lft = cpyInfo.getLft();
        Integer rgt = cpyInfo.getRgt();
        conditionDto.setLft(lft);
        conditionDto.setRgt(rgt);
        conditionDto.setTimeSort(ContractConditionDto.timeSort.DESC.ordinal());
        List<String> IDList = contractDao.findContractListByCondition(conditionDto);
        if (IDList.isEmpty()){
            return null;
        }
        String contractID = IDList.get(0);

        // 包装
        ContractInfo contractInfo = contractDao.findContractInfoByContractID(contractID);
        UnpaidContractInfoResponse contractInfoResponse = new UnpaidContractInfoResponse();
        contractInfoResponse.setContractID(contractInfo.getContractID());
        contractInfoResponse.setCompanyName(companyService.findCompanyInfoByCompanyID(contractInfo.getCompanyID()).getCompanyName());
        contractInfoResponse.setContractTypeName(contractDao.getContractTypeName(contractInfo.getContractTypeID()));
        contractInfoResponse.setDellFee(contractInfo.getDellFee());
        DevType devType = contractDao.getDevType(contractInfo.getDevTypeID());
        contractInfoResponse.setDevName(devType.getDevName());
        contractInfoResponse.setDevTypeName(devType.getDevType());
        contractInfoResponse.setDevNums(contractInfo.getDevNums());
        contractInfoResponse.setServerFee(contractInfo.getServerFee());
        contractInfoResponse.setServerYears(contractInfo.getServerYears());

        return contractInfoResponse;
    }

    @Override
    @Transactional(rollbackFor=Exception.class)
    public String addContract(AddContractRequest addContractRequest) {
        //先获取甲方公司信息
        CompanyInfo partyACpyInfo = companyService.findCompanyInfoByCompanyName(addContractRequest.getCompanyName());

        //先删除甲方公司之前的未支付合同,即甲方公司只能有一份未支付合同
        //获取未支付合同id
        ContractConditionDto conditionDto = new ContractConditionDto();
        conditionDto.setContractStatus(ContractInfo.ContractStatus.UNPAID.ordinal());
        CompanyInfo cpyInfo = companyService.findCompanyInfoByCompanyID(partyACpyInfo.getCompanyID());
        Integer lft = cpyInfo.getLft();
        Integer rgt = cpyInfo.getRgt();
        conditionDto.setLft(lft);
        conditionDto.setRgt(rgt);
        conditionDto.setTimeSort(ContractConditionDto.timeSort.DESC.ordinal());
        List<String> IDList = contractDao.findContractListByCondition(conditionDto);

        String contractID = UUIDUtils.getUUID();
        //删除即将合同状态变为过期
        try{
            if (!(IDList == null || IDList.isEmpty())){
                String delContractID = IDList.get(0);
                Integer contractStatus = ContractInfo.ContractStatus.EXPIRED.ordinal();
                contractDao.updateContractStatus(delContractID, contractStatus);
            }

            //新建合同
            ContractInfo contractInfo = new ContractInfo();
            contractInfo.setCompanyID(partyACpyInfo.getCompanyID());
            contractInfo.setContractID(contractID);
//          合同状态为未支付
            contractInfo.setContractStatus(ContractInfo.ContractStatus.UNPAID.ordinal());
            contractInfo.setContractTypeID(contractDao.getContractTypeID(addContractRequest.getContractTypeName()));
            contractInfo.setServerFee(addContractRequest.getServerFee());
            contractInfo.setServerYears(addContractRequest.getServerYears());
            contractInfo.setDellFee(addContractRequest.getDellFee());
            contractInfo.setDevNums(addContractRequest.getDevNums());
            contractInfo.setPartyAPersonID(partyACpyInfo.getCompanyManagerID());
            contractInfo.setDevTypeID(contractDao.getDevTypeID(addContractRequest.getDevName()));
            contractInfo.setCreateTime(new Date());

            contractDao.addContractInfo(contractInfo);
        }catch (Exception e){
            throw new GlobalException(CodeMsg.SERVER_ERROR);
        }
        // TODO 发送消息提醒甲方公司

        return contractID;
    }

    @Override
    public List<String> getAllContractType() {
        List<String> contractTypeNameList = contractDao.getAllContractType();
        return contractTypeNameList;
    }

    @Override
    @Transactional(rollbackFor=Exception.class)
    public void addOrder(String contractID) {
        //先增加order记录
        String contractOrderID = UUIDUtils.getUUID();
        ContractInfo contractInfo = contractDao.findContractInfoByContractID(contractID);
        Float serverFee = contractInfo.getServerFee();
        Date createTime = new Date();
        contractDao.insertOrder(contractOrderID, contractID, serverFee, createTime);

        //记录设备批量调拨信息
        AddContractDevMesRequest addContractDevMesRequest = new AddContractDevMesRequest();
        addContractDevMesRequest.setCompanyID(contractInfo.getCompanyID());
        addContractDevMesRequest.setDevName(contractDao.getDevType(contractInfo.getDevTypeID()).getDevName());
        addContractDevMesRequest.setDevNums(contractInfo.getDevNums());
        //增加设备调拨记录，完成设备批量调拨
        deviceService.addContractDeviceMessage(addContractDevMesRequest);

        //更改合同状态
        Integer contractStatus = ContractInfo.ContractStatus.PAID.ordinal();
        contractDao.updateContractStatus(contractID, contractStatus);

        pushPayMsg(contractID);
    }

    @Override
    @Transactional(rollbackFor=Exception.class)
    public void renewContract(String contractID) {
        //先增加order记录
        String contractOrderID = UUIDUtils.getUUID();
        ContractInfo contractInfo = contractDao.findContractInfoByContractID(contractID);
        Float serverFee = contractInfo.getServerFee();
        Date createTime = new Date();
        contractDao.insertOrder(contractOrderID, contractID, serverFee, createTime);

        //更新合同过期记录
        Float years = redisService.get(RenewKey.RENEW_TIME, contractID, Double.class).floatValue();
        log.info("续费年限:{}",years);
        contractDao.updateContractServerYears(contractID, years, createTime);

        redisService.delete(RenewKey.RENEW_TIME, contractID);

        pushPayMsg(contractID);
    }

    /**
     * 推送支付消息
     */
    public void pushPayMsg(String contractID){
        ContractInfo contractInfo = contractDao.findContractInfoByContractID(contractID);
        ChatMessage chatMessage = new ChatMessage();
        chatMessage.setMesID(UUIDUtils.getUUID());
        chatMessage.setSenderId(contractInfo.getPartyAPersonID().toString());
        chatMessage.setReceiverId(contractInfo.getPartyAPersonID().toString());

        String pushMsg = contractID+":"+"支付成功，合同已生效，点击“查看详细信息”查看合同详细信息，谢谢！！！";
        chatMessage.setContent(pushMsg);
        chatMessage.setMsgType(ChatMessage.MsgType.PAY_CONTRACT.toString());
        BusinessMessage message = new BusinessMessage(BusinessType.CBD_BUSINESS_QUEUE, JSON.toJSON(chatMessage));
        mqSender.send(message);
    }
}

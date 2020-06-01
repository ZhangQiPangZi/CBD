package com.cbd.cbdcontract.service;

import com.cbd.cbdcommoninterface.cbd_interface.company.CompanyService;
import com.cbd.cbdcommoninterface.cbd_interface.contract.ContractService;
import com.cbd.cbdcommoninterface.cbd_interface.device.DeviceService;
import com.cbd.cbdcommoninterface.dto.ContractConditionDto;
import com.cbd.cbdcommoninterface.dto.DevConditionDto;
import com.cbd.cbdcommoninterface.pojo.company.CompanyInfo;
import com.cbd.cbdcommoninterface.pojo.contract.ContractInfo;
import com.cbd.cbdcommoninterface.pojo.contract.ContractType;
import com.cbd.cbdcommoninterface.pojo.device.DevType;
import com.cbd.cbdcommoninterface.request.AddContractRequest;
import com.cbd.cbdcommoninterface.request.DistributeContractRequest;
import com.cbd.cbdcommoninterface.request.PageContractConditionRequest;
import com.cbd.cbdcommoninterface.request.PageRequest;
import com.cbd.cbdcommoninterface.response.*;
import com.cbd.cbdcommoninterface.result.CodeMsg;
import com.cbd.cbdcommoninterface.result.GlobalException;
import com.cbd.cbdcommoninterface.utils.PageUtils;
import com.cbd.cbdcommoninterface.utils.UUIDUtils;
import com.cbd.cbdcontract.dao.ContractDao;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
@Slf4j
public class ContractServiceImpl implements ContractService {
    @Autowired
    CompanyService companyService;
    @Autowired
    ContractDao contractDao;
    @Autowired
    DeviceService deviceService;

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
        }

        String contractTypeName = contractDao.getContractTypeName(contractInfo.getContractTypeID());
        contractInfoResponse.setContractTypeName(contractTypeName);

        DevType devType = contractDao.getDevType(contractInfo.getDevTypeID());
        contractInfoResponse.setDevName(devType.getDevName());
        contractInfoResponse.setDevTypeName(devType.getDevType());

        contractInfoResponse.setContractStatus(contractInfo.getContractStatus());
        contractInfoResponse.setCreateTime(contractInfo.getCreateTime());
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
        /**
         * 获取调拨公司的信息
         */
        CompanyInfo dstCpy = companyService.findCompanyInfoByCompanyName(contractRequest.getCompanyName());

        String companyID = dstCpy.getCompanyID();
        String shopID = null;
        //如果派发的公司为4s店，则赋值给shopID
        if(dstCpy.getCompanylevel().equals(CompanyInfo.Companylevel.DISCPY.ordinal())){
            shopID = dstCpy.getCompanyID();
        }
        try {
            contractDao.updateContractByDistribute(contractRequest.getContractID(),companyID, shopID);
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
        renewMoneyList.add(halfMoney*6);

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
        String contractID = IDList.get(0);

        // 包装
        ContractInfo contractInfo = contractDao.findContractInfoByContractID(contractID);
        UnpaidContractInfoResponse contractInfoResponse = new UnpaidContractInfoResponse();
        contractInfoResponse.setContractTypeName(contractDao.getContractTypeName(contractInfo.getContractTypeID()));
        contractInfoResponse.setDellFee(contractInfo.getDellFee());
        contractInfoResponse.setDevName(contractDao.getDevType(contractInfo.getDevTypeID()).getDevName());
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

        return contractID;
    }

    @Override
    public List<String> getAllContractType() {
        List<String> contractTypeNameList = contractDao.getAllContractType();
        return contractTypeNameList;
    }
}

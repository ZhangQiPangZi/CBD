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
import com.cbd.cbdcommoninterface.request.DistributeContractRequest;
import com.cbd.cbdcommoninterface.request.PageContractConditionRequest;
import com.cbd.cbdcommoninterface.request.PageRequest;
import com.cbd.cbdcommoninterface.response.ContractInfoResponse;
import com.cbd.cbdcommoninterface.response.PageContractListResponse;
import com.cbd.cbdcommoninterface.response.PageDevListResponse;
import com.cbd.cbdcommoninterface.response.PageResponse;
import com.cbd.cbdcommoninterface.utils.PageUtils;
import com.cbd.cbdcontract.dao.ContractDao;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

public class ContractServiceImpl implements ContractService {
    @Autowired
    CompanyService companyService;
    @Autowired
    ContractDao contractDao;
    @Autowired
    DeviceService deviceService;

    @Override
    public List<String> findContractTypeNameByCompanyID(String companyID) {
        return null;
    }

    @Override
    public PageResponse findContractListByCondition(PageContractConditionRequest contractConditionRequest) {
        /**
         * 给dto赋值，作为查询条件
         * 要放在startPage前面，防止分页查询的sql不是想要的那个
         */
        CompanyInfo cpyInfo = companyService.findCompanyInfoByCompanyID(contractConditionRequest.getCompanyID());
        ContractConditionDto contractConditionDto = new ContractConditionDto();
        contractConditionDto.setCompanyID(contractConditionRequest.getCompanyID());
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
        PageHelper.startPage(pageNum, pageSize);

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

            contractListResponseList.add(temp);
        }

        PageInfo<PageContractListResponse> devListResponsePageInfo = new PageInfo<>(contractListResponseList);

        return PageUtils.getPageResponse(devListResponsePageInfo);
    }

    @Override
    public ContractInfoResponse findContractInfoByContractID(String contractID) {
        ContractInfo contractInfo = contractDao.findContractInfoByContractID(contractID);
        ContractInfoResponse contractInfoResponse = new ContractInfoResponse();
        contractInfoResponse.setContractID(contractID);

        CompanyInfo companyInfo = companyService.findCompanyInfoByCompanyID(contractInfo.getCompanyID());
        contractInfoResponse.setContractCompanyName(companyInfo.getCompanyName());
        CompanyInfo shopInfo = companyService.findCompanyInfoByCompanyID(contractInfo.getShopID());
        contractInfoResponse.setShopName(shopInfo.getCompanyName());

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
        contractInfoResponse.setPartyAPersonName(contractInfo.getPartyAPersonID());
        contractInfoResponse.setServerFee(contractInfo.getServerFee());
        contractInfoResponse.setServerYears(contractInfo.getServerYears());

        return contractInfoResponse;
    }

    @Override
    public Boolean distributeContractByContractIDAndCompanyName(DistributeContractRequest contractRequest) {
        return true;
    }

    @Override
    public List<Float> getRenewMoneyByContractID(String contractID) {
        return null;
    }
}

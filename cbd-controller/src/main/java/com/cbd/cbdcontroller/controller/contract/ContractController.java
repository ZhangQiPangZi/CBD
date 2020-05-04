package com.cbd.cbdcontroller.controller.contract;

import com.cbd.cbdcommoninterface.cbd_interface.contract.ContractService;
import com.cbd.cbdcommoninterface.request.AddContractRequest;
import com.cbd.cbdcommoninterface.request.DistributeContractRequest;
import com.cbd.cbdcommoninterface.request.PageContractConditionRequest;
import com.cbd.cbdcommoninterface.response.ContractInfoResponse;
import com.cbd.cbdcommoninterface.response.PageContractListResponse;
import com.cbd.cbdcommoninterface.response.PageResponse;
import com.cbd.cbdcommoninterface.response.UnpaidContractInfoResponse;
import com.cbd.cbdcommoninterface.result.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/contract")
@Api("合同接口")
@Slf4j
@CrossOrigin
public class ContractController {
    @Autowired
    ContractService contractService;

    @ApiOperation("获取当前公司下的合同类别列表")
    @RequestMapping(value = "/findContractTypeNameByCompanyID", method = RequestMethod.POST)
    public Result<List<String>> findContractTypeNameByCompanyID(@RequestParam(value = "companyID") String companyID){
        return Result.success(contractService.findContractTypeNameByCompanyID(companyID));
    }

    @ApiOperation("根据指定条件获取对应的合同列表(五条件)")
    @RequestMapping(value = "/findContractListByCondition", method = RequestMethod.POST)
    public Result<PageResponse> findContractListByCondition(@RequestBody PageContractConditionRequest contractConditionRequest){
        return Result.success(contractService.findContractListByCondition(contractConditionRequest));
    }

    @ApiOperation("根据合同ID获取合同详细信息")
    @RequestMapping(value = "/findContractInfoByContractID", method = RequestMethod.POST)
    public Result<ContractInfoResponse> findContractInfoByContractID(@RequestParam(value = "contractID") String contractID){
        return Result.success(contractService.findContractInfoByContractID(contractID));
    }

    @ApiOperation("获取当前公司下可派发的合同列表")
    @RequestMapping(value = "/findUsingContractListByCompanyID", method = RequestMethod.POST)
    public Result<List<PageContractListResponse>> findUsingContractListByCompanyID(@RequestParam(value = "companyID") String companyID){
        return Result.success(contractService.findUsingContractListByCompanyID(companyID));
    }

    @ApiOperation("根据合同ID和公司名进行合同派发")
    @RequestMapping(value = "/distributeContractByContractIDAndCompanyName", method = RequestMethod.POST)
    public Result<Boolean> distributeContractByContractIDAndCompanyName(@RequestBody DistributeContractRequest contractRequest){
        return Result.success(contractService.distributeContractByContractIDAndCompanyName(contractRequest));
    }

    @ApiOperation("根据合同ID获得不同时长续费金额(三种)")
    @RequestMapping(value = "/getRenewMoneyByContractID", method = RequestMethod.POST)
    public Result<List<Float>> getRenewMoneyByContractID(@RequestParam(value = "contractID") String contractID){
        return Result.success(contractService.getRenewMoneyByContractID(contractID));
    }

    @ApiOperation("获取之前未支付的合同信息")
    @RequestMapping(value = "/getUnPaidContractInfoByCompanyID", method = RequestMethod.POST)
    public Result<UnpaidContractInfoResponse> getUnPaidContractInfoByCompanyID(@RequestParam(value = "companyID") String companyID){
        return Result.success(contractService.getUnPaidContractInfoByCompanyID(companyID));
    }

    @ApiOperation("新建合同，支付之前做, 返回contractID")
    @RequestMapping(value = "/addContract", method = RequestMethod.POST)
    public Result<String> addContract(@RequestBody AddContractRequest addContractRequest){
        return Result.success(contractService.addContract(addContractRequest));
    }





}

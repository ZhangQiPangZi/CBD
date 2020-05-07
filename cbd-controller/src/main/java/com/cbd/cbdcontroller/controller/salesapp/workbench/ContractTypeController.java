package com.cbd.cbdcontroller.controller.salesapp.workbench;

import com.cbd.cbdcommoninterface.cbd_interface.contract.ContractService;
import com.cbd.cbdcommoninterface.cbd_interface.device.DeviceService;
import com.cbd.cbdcommoninterface.request.PageContractConditionRequest;
import com.cbd.cbdcommoninterface.response.ContractInfoResponse;
import com.cbd.cbdcommoninterface.response.DevInfoResponse;
import com.cbd.cbdcommoninterface.response.PageResponse;
import com.cbd.cbdcommoninterface.result.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author: Monster
 * @date: 2020/4/21 15:07
 * @Description
 */
@RestController
@Slf4j
@RequestMapping(value = "/contractType")
@Api(value = "选择合同类别")
@CrossOrigin
public class ContractTypeController {
    @Autowired
    private ContractService contractService;
    @Autowired
    private DeviceService deviceService;

    /**
     * @Description 实际上的参数仅需要companyID
     * @param contractConditionRequest
     * @return
     */
    @RequestMapping(value = "/contract-list",method = RequestMethod.POST)
    @ApiOperation("返回合同类型列表")
    @ResponseBody
    public Result<PageResponse> findContractList(@RequestBody PageContractConditionRequest contractConditionRequest){
        return Result.success(contractService.findContractListByCondition(contractConditionRequest));
    }

    /**
     * @Description 客户从合同列表中选择适合自己的合同 根据合同Id 查看该合同的详细信息
     * @param contractID
     * @return
     */
    @RequestMapping(value = "/contract-info",method = RequestMethod.GET)
    @ApiOperation("获取合同的详细信息")
    @ResponseBody
    public Result<ContractInfoResponse> findContractInfo(@RequestParam String contractID){
        return Result.success(contractService.findContractInfoByContractID(contractID));
    }

    /**
     * @Description 返回该合同可选的所有设备的list
     * @param devName
     * @param companyID
     * @return
     */
    @RequestMapping(value = "/find-devInfo-list",method = RequestMethod.GET)
    @ApiOperation("获取某合同可选的所有设备列表")
    @ResponseBody
    public Result<List<DevInfoResponse>> findDevInfoList(@RequestParam String devName,@RequestParam String companyID){
        return Result.success(deviceService.findDevInfoListByDevNameAndCompanyID(devName,companyID));
    }


}

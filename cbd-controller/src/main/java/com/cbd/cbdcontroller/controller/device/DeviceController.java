package com.cbd.cbdcontroller.controller.device;

import com.cbd.cbdcommoninterface.cbd_interface.device.DeviceService;
import com.cbd.cbdcommoninterface.request.*;
import com.cbd.cbdcommoninterface.response.*;

import com.cbd.cbdcommoninterface.result.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/device")
@Api("设备接口")
@Slf4j
@CrossOrigin
public class DeviceController {
    @Autowired
    private DeviceService deviceService;

    @ApiOperation("新增设备")
    @RequestMapping(value = "/addDeviceInfo", method = RequestMethod.POST)
    public Result<Boolean> addDeviceInfo(@RequestBody AddDeviceRequest addDeviceRequest){
        return Result.success(deviceService.addDeviceInfo(addDeviceRequest));
    }

    @ApiOperation("获取当前公司下的设备厂家列表")
    @RequestMapping(value = "/findAllDevFactoryNameByCompanyID", method = RequestMethod.POST)
    public Result<List<String>> findAllDevFactoryNameByCompanyID(@RequestParam(value = "companyID") String companyID){
        return Result.success(deviceService.findAllDevFactoryNameByCompanyID(companyID));
    }

    @ApiOperation("根据指定条件获取对应的设备列表(五条件)")
    @RequestMapping(value = "/findDevListByCondition", method = RequestMethod.POST)
    public Result<PageResponse> findDevListByCondition(@RequestBody PageDevConditionRequest pageDevConditionRequest){
        return Result.success(deviceService.findDevListByCondition(pageDevConditionRequest));
    }

    @ApiOperation("根据设备ID获取设备详细信息")
    @RequestMapping(value = "/findDevInfoByDevID", method = RequestMethod.POST)
    public Result<DevInfoResponse> findDevInfoByDevID(@RequestParam(value = "devID") String devID){
        return Result.success(deviceService.findDevInfoByDevID(devID));
    }
    @ApiOperation("获取当前公司下设备名列表及对应的数量")
    @RequestMapping(value = "/findDevNameListAndNumsByCompanyID", method = RequestMethod.POST)
    public Result<List<DevNameNumsResponse>> findDevNameListAndDevNumsByCompanyID(@RequestParam(value = "companyID") String companyID){
        return Result.success(deviceService.findDevNameListAndDevNumsByCompanyID(companyID));
    }
    @ApiOperation("根据设备ID和公司名进行单个设备调拨")
    @RequestMapping(value = "/allocationDeviceByDevIDAndCompanyName", method = RequestMethod.POST)
    public Result<Boolean> allocationDeviceByDevIDAndCompanyName(@RequestBody AllocationDevRequest allocationDevRequest){
        return Result.success(deviceService.allocationDeviceByDevIDAndCompanyName(allocationDevRequest));
    }
    @ApiOperation("根据设备名及数量和公司名进行设备批量调拨")
    @RequestMapping(value = "/allocationDevicesByDevNameAndNumsAndCompanyName", method = RequestMethod.POST)
    public Result<Boolean> allocationBatchDeviceByDevNameAndDevNumsAndCompanyName(@RequestBody AllocationBathDevRequest allocationBathDevRequest){
        return Result.success(deviceService.allocationBatchDeviceByDevNameAndDevNumsAndCompanyName(allocationBathDevRequest));
    }
    @ApiOperation("根据设备ID获得设备调拨记录列表")
    @RequestMapping(value = "/findAllocationRecordByDevID", method = RequestMethod.POST)
    public Result<List<AllocationRecordResponse>> findAllocationRecordByDevID(@RequestParam(value = "devID") String devID){
        return Result.success(deviceService.findAllocationRecordByDevID(devID));
    }
    @ApiOperation("根据设备ID获取生产厂家联系方式")
    @RequestMapping(value = "/findDeviceContactByDevID", method = RequestMethod.POST)
    public Result<DevReturnResponse> findDeviceContactByDevID(@RequestParam(value = "devID") String devID){
        return Result.success(deviceService.findDeviceContactByDevID(devID));
    }

    @ApiOperation("根据设备ID确认设备返厂")
    @RequestMapping(value = "/confirmDeviceMessageByMesID", method = RequestMethod.POST)
    public Result<Boolean> confirmDeviceMessageByMesID(@RequestParam(value = "mesID") String mesID){
        return Result.success(deviceService.confirmDeviceMessageByMesID(mesID));
    }

    @ApiOperation("逻辑删除设备记录，确认设备返厂时用")
    @RequestMapping(value = "/delReturnDeviceByDevID", method = RequestMethod.POST)
    public Result<Boolean> delReturnDeviceByDevID(@RequestParam(value = "devID") String devID){
        return Result.success(deviceService.delReturnDeviceByDevID(devID));
    }

    @ApiOperation("新增合同设备调拨消息")
    @RequestMapping(value = "/addContractDeviceMessage", method = RequestMethod.POST)
    public Result<Boolean> addContractDeviceMessage(@RequestBody AddContractDevMesRequest contractDevMesRequest){
        return Result.success(deviceService.addContractDeviceMessage(contractDevMesRequest));
    }

    @ApiOperation("判断此消息是否确认")
    @RequestMapping(value = "/judgeConfirmMessage", method = RequestMethod.POST)
    public Result<Boolean> judgeConfirmMessage(@RequestParam(value = "mesID") String mesID){
        return Result.success(deviceService.judgeConfirmMessage(mesID));
    }

    @ApiOperation("获取分类后的消息，mesStatus可以不传，代表查询所有消息")
    @RequestMapping(value = "/findMessageByManagerIDAndMessageStatus", method = RequestMethod.POST)
    public Result<PageResponse> findMessageByManagerIDAndMessageStatus(@RequestBody GetMessageRequest messageRequest){
        return Result.success(deviceService.findMessageByManagerIDAndMessageStatus(messageRequest));
    }

}

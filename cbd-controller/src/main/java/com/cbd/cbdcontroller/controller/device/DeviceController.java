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

    @ApiOperation("判断设备类型是否收录")
    @RequestMapping(value = "/judgeDevType", method = RequestMethod.POST)
    public Result<Boolean> judgeDevType(@RequestBody DevNameRequest devNameRequest){
        Boolean flag = false;
        if (deviceService.findDevTypeByDevName(devNameRequest.getDevName()) != null){
            flag = true;
        }
        return Result.success(flag);
    }

    @ApiOperation("获取当前公司下的设备厂家列表")
    @RequestMapping(value = "/findAllDevFactoryNameByCompanyID", method = RequestMethod.POST)
    public Result<List<String>> findAllDevFactoryNameByCompanyID(@RequestBody CompanyIDRequest companyIDRequest){
        return Result.success(deviceService.findAllDevFactoryNameByCompanyID(companyIDRequest.getCompanyID()));
    }

    @ApiOperation("根据指定条件获取对应的设备列表(五条件)")
    @RequestMapping(value = "/findDevListByCondition", method = RequestMethod.POST)
    public Result<PageResponse> findDevListByCondition(@RequestBody PageDevConditionRequest pageDevConditionRequest){
        return Result.success(deviceService.findDevListByCondition(pageDevConditionRequest));
    }

    @ApiOperation("根据设备ID获取设备详细信息")
    @RequestMapping(value = "/findDevInfoByDevID", method = RequestMethod.POST)
    public Result<DevInfoResponse> findDevInfoByDevID(@RequestBody DevIDRequest devIDRequest){
        return Result.success(deviceService.findDevInfoByDevID(devIDRequest.getDevID()));
    }
    @ApiOperation("获取当前公司下设备名列表及对应的数量")
    @RequestMapping(value = "/findDevNameListAndNumsByCompanyID", method = RequestMethod.POST)
    public Result<List<DevNameNumsResponse>> findDevNameListAndDevNumsByCompanyID(@RequestBody CompanyIDRequest companyIDRequest){
        return Result.success(deviceService.findDevNameListAndDevNumsByCompanyID(companyIDRequest.getCompanyID()));
    }
    @ApiOperation("根据设备ID和公司名进行单个设备调拨")
    @RequestMapping(value = "/allocationDeviceByDevIDAndCompanyName", method = RequestMethod.POST)
    public Result<Boolean> allocationDeviceByDevIDAndCompanyName(@RequestBody AllocationDevRequest allocationDevRequest){
        return Result.success(deviceService.allocationDeviceByDevIDAndCompanyName(allocationDevRequest));
    }
    @ApiOperation("判断此设备是否可以调拨，即状态为入库")
    @RequestMapping(value = "/judgePermitDevice", method = RequestMethod.POST)
    public Result<PermitDeviceResponse> judgePermitDevice(@RequestBody DevIDRequest devIDRequest){
        return Result.success(deviceService.judgePermitDevice(devIDRequest.getDevID()));
    }
    @ApiOperation("根据设备名及数量和公司名进行设备批量调拨")
    @RequestMapping(value = "/allocationDevicesByDevNameAndNumsAndCompanyName", method = RequestMethod.POST)
    public Result<Boolean> allocationBatchDeviceByDevNameAndDevNumsAndCompanyName(@RequestBody AllocationBathDevRequest allocationBathDevRequest){
        return Result.success(deviceService.allocationBatchDeviceByDevNameAndDevNumsAndCompanyName(allocationBathDevRequest));
    }
    @ApiOperation("根据设备ID获得设备调拨记录列表")
    @RequestMapping(value = "/findAllocationRecordByDevID", method = RequestMethod.POST)
    public Result<List<AllocationRecordResponse>> findAllocationRecordByDevID(@RequestBody DevIDRequest devIDRequest){
        return Result.success(deviceService.findAllocationRecordByDevID(devIDRequest.getDevID()));
    }
    @ApiOperation("根据设备ID获取生产厂家联系方式")
    @RequestMapping(value = "/findDeviceContactByDevID", method = RequestMethod.POST)
    public Result<DevReturnResponse> findDeviceContactByDevID(@RequestBody DevIDRequest devIDRequest){
        return Result.success(deviceService.findDeviceContactByDevID(devIDRequest.getDevID()));
    }

    @ApiOperation("根据消息ID确认消息并进行设备的调拨")
    @RequestMapping(value = "/confirmDeviceMessageByMesID", method = RequestMethod.POST)
    public Result<Boolean> confirmDeviceMessageByMesID(@RequestBody MesIDRequest mesIDRequest){
        return Result.success(deviceService.confirmDeviceMessageByMesID(mesIDRequest.getMesID()));
    }

    @ApiOperation("逻辑删除设备记录，确认设备返厂时用")
    @RequestMapping(value = "/delReturnDeviceByDevID", method = RequestMethod.POST)
    public Result<Boolean> delReturnDeviceByDevID(@RequestBody DevIDRequest devIDRequest){
        return Result.success(deviceService.delReturnDeviceByDevID(devIDRequest.getDevID()));
    }

    @ApiOperation("判断此消息是否确认")
    @RequestMapping(value = "/judgeConfirmMessage", method = RequestMethod.POST)
    public Result<Boolean> judgeConfirmMessage(@RequestBody MesIDRequest mesIDRequest){
        return Result.success(deviceService.judgeConfirmMessage(mesIDRequest.getMesID()));
    }

    @ApiOperation("获取分类后的消息，mesStatus可以不传，代表查询所有消息")
    @RequestMapping(value = "/findMessageByManagerIDAndMessageStatus", method = RequestMethod.POST)
    public Result<PageResponse> findMessageByManagerIDAndMessageStatus(@RequestBody GetMessageRequest messageRequest){
        return Result.success(deviceService.findMessageByManagerIDAndMessageStatus(messageRequest));
    }

    @ApiOperation("获取所有设备名,车佰度平台管理员访问")
    @RequestMapping(value = "/getAllDevName", method = RequestMethod.POST)
    public Result<List<String>> getAllDevName(){
        return Result.success(deviceService.getAllDevName());
    }

}

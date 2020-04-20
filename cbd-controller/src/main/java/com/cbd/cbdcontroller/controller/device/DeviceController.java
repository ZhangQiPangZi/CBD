package com.cbd.cbdcontroller.controller.device;

import com.cbd.cbdcommoninterface.cbd_interface.device.DeviceService;
import com.cbd.cbdcommoninterface.pojo.device.DeviceInfo;
import com.cbd.cbdcontroller.annotation.Login;
import com.cbd.cbdcontroller.annotation.Permission;
import com.cbd.cbdcontroller.result.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/device")
@Api("设备管理")
@Slf4j
@CrossOrigin
public class DeviceController {
    @Autowired
    private DeviceService deviceService;

    @ApiOperation("测试 获取设备信息")
    @RequestMapping(value = "/testDeviceInfo",method = RequestMethod.POST)
    @ResponseBody
    @Login
    @Permission("device:query")
    public Result<DeviceInfo> testGetDevInfo(){
        return Result.success(deviceService.testGetDevInfo("dddsdazz"));
    }

    @ApiOperation("测试 Redis")
    @RequestMapping(value = "/testRedis",method = RequestMethod.POST)
    @ResponseBody
    public Result<String> testRedis(){
        return Result.success(deviceService.testRedis());
    }


}

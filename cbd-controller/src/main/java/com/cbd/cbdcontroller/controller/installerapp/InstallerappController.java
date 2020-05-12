package com.cbd.cbdcontroller.controller.installerapp;

import com.cbd.cbdcommoninterface.cbd_interface.installerapp.common.UpdateTimeService;
import com.cbd.cbdcommoninterface.cbd_interface.installerapp.completed.CompletedListService;
import com.cbd.cbdcommoninterface.cbd_interface.installerapp.user.InstallerUserService;
import com.cbd.cbdcommoninterface.cbd_interface.installerapp.waitingtask.InstallOrderService;
import com.cbd.cbdcommoninterface.cbd_interface.installerapp.waitingtask.RemoveOrderService;
import com.cbd.cbdcommoninterface.cbd_interface.installerapp.waitingtask.RepairOrderService;
import com.cbd.cbdcommoninterface.cbd_interface.installerapp.waitingtask.TaskListService;
import com.cbd.cbdcommoninterface.pojo.installerapp.user.InstallerInfosDO;
import com.cbd.cbdcommoninterface.request.PageRequest;
import com.cbd.cbdcommoninterface.request.installerapp.waitingtask.RemoveQuery;
import com.cbd.cbdcommoninterface.response.PageResponse;
import com.cbd.cbdcommoninterface.result.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author: Monster
 * @date: 2020/5/10 9:46
 * @Description
 */
@RestController
@RequestMapping("/installer-app")
@Api("安装工接口")
@Slf4j
@CrossOrigin
public class InstallerappController {
    @Autowired
    private UpdateTimeService updateTimeService;
    @Autowired
    private CompletedListService completedListService;
    @Autowired
    private InstallerUserService installerUserService;
    @Autowired
    private InstallOrderService installOrderService;
    @Autowired
    private RemoveOrderService removeOrderService;
    @Autowired
    private RepairOrderService repairOrderService;
    @Autowired
    private TaskListService taskListService;

    @RequestMapping(value = "/update-order-time",method = RequestMethod.GET)
    @ApiOperation("改变预约时间")
    @ResponseBody
    public int updateOrderTime(@RequestParam String orderTime,@RequestParam Integer orderId){
        return updateTimeService.updateOrderTime(orderTime,orderId);
    }

    @RequestMapping(value = "/get-list",method = RequestMethod.POST)
    @ApiOperation("获取已完成的订单")
    @ResponseBody
    public Result<PageResponse> getList(@RequestParam Integer installerId, @RequestParam(required = false) Integer orderTypeCode, @RequestBody PageRequest pageRequest){
        return Result.success(completedListService.getList(installerId,orderTypeCode,pageRequest));
    }

    @RequestMapping(value = "/installer-user-info",method = RequestMethod.GET)
    @ApiOperation("用户信息")
    @ResponseBody
    public InstallerInfosDO getUserInfo(@RequestParam String phoneNumber){
        return installerUserService.getUserInfo(phoneNumber);
    }

    @RequestMapping(value = "/install-complete",method = RequestMethod.GET)
    @ApiOperation("安装订单完成")
    @ResponseBody
    public int installOrderComplete(@RequestParam Integer installerId,@RequestParam Integer orderId){
        return installOrderService.installOrderComplete(installerId,orderId);
    }

    @RequestMapping(value = "/remove-dev",method = RequestMethod.POST)
    @ApiOperation("拆除设备")
    @ResponseBody
    public int removeDev(@RequestBody RemoveQuery query){
        return removeOrderService.removeDev(query);
    }

    @RequestMapping(value = "/remove-complete",method = RequestMethod.GET)
    @ApiOperation("拆除订单完成")
    @ResponseBody
    public int removeOrderComplete(@RequestParam Integer orderId){
        return removeOrderService.removeOrderComplete(orderId);
    }

    @RequestMapping(value = "/change",method = RequestMethod.POST)
    @ApiOperation("设备更换")
    @ResponseBody
    public int changeDev(@RequestBody RemoveQuery query){
        return repairOrderService.changeDev(query);
    }

    @RequestMapping(value = "/order-complete",method = RequestMethod.GET)
    @ApiOperation("维修订单完成")
    @ResponseBody
    public int orderComplete(@RequestParam Integer flag,@RequestParam Integer orderId,@RequestParam String phoneNumber,@RequestParam Integer installerId){
        return repairOrderService.orderComplete(flag,orderId,phoneNumber,installerId);
    }

    @RequestMapping(value = "/get-task-list",method = RequestMethod.POST)
    @ApiOperation("获取待处理订单列表")
    @ResponseBody
    public Result<PageResponse> getTaskList(@RequestParam Integer installerId, @RequestParam(required = false) Integer orderTypeCode,@RequestBody PageRequest pageRequest){
        return Result.success(taskListService.getTaskList(installerId,orderTypeCode,pageRequest));
    }
}

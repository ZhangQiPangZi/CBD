package com.cbd.cbdcontroller.controller.installerapp.waitingtask;

import com.cbd.cbdcommoninterface.cbd_interface.installerapp.waitingtask.RepairOrderService;
import com.cbd.cbdcommoninterface.request.installerapp.waitingtask.RemoveQuery;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author: Monster
 * @date: 2020/4/21 19:31
 * @Description
 */
@RestController
@Slf4j
@RequestMapping("/repair")
@Api(value = "维修订单处理")
@CrossOrigin
public class RepairOrderController {

    @Autowired
    private RepairOrderService repairOrderService;

    @RequestMapping(value = "/change",method = RequestMethod.POST)
    @ApiOperation("设备更换")
    @ResponseBody
    public int changeDev(@RequestBody RemoveQuery query){
        return repairOrderService.changeDev(query);
    }

    @RequestMapping(value = "/order-complete",method = RequestMethod.GET)
    @ApiOperation("订单完成")
    @ResponseBody
    public int orderComplete(@RequestParam(required = false) Integer devId,@RequestParam(required = false) Integer simId,@RequestParam Integer orderId,@RequestParam(required = false) String phoneNumber){
        return repairOrderService.orderComplete(devId,simId,orderId,phoneNumber);
    }
}

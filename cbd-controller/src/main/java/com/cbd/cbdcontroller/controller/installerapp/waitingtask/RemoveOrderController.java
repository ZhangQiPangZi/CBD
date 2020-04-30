package com.cbd.cbdcontroller.controller.installerapp.waitingtask;

import com.cbd.cbdcommoninterface.cbd_interface.installerapp.waitingtask.RemoveOrderService;
import com.cbd.cbdcommoninterface.request.installerapp.waitingtask.RemoveQuery;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author: Monster
 * @date: 2020/4/21 19:29
 * @Description
 */
@RestController
@Slf4j
@RequestMapping("/remove")
@Api(value = "拆除订单处理")
@CrossOrigin
public class RemoveOrderController {

    @Autowired
    private RemoveOrderService removeOrderService;

    @RequestMapping(value = "/remove-dev",method = RequestMethod.POST)
    @ApiOperation("拆除设备")
    @ResponseBody
    public int removeDev(@RequestBody RemoveQuery query){
        return removeOrderService.removeDev(query);
    }

    @RequestMapping(value = "/remove-complete",method = RequestMethod.GET)
    @ApiOperation("订单完成")
    @ResponseBody
    public int removeOrderComplete(@RequestParam Integer orderId){
        return removeOrderService.removeOrderComplete(orderId);
    }
}

package com.cbd.cbdcontroller.controller.installerapp.common;

import com.cbd.cbdcommoninterface.cbd_interface.installerapp.common.UpdateTimeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author: Monster
 * @date: 2020/4/22 0:08
 * @Description
 */
@RestController
@Slf4j
@RequestMapping("/update-time")
@Api(value = "工程师改变预约时间")
@CrossOrigin
public class UpdateTimeController {

    @Autowired
    private UpdateTimeService updateTimeService;

    @RequestMapping(value = "/update-order-time",method = RequestMethod.GET)
    @ApiOperation("改变预约时间")
    @ResponseBody
    public int updateOrderTime(@RequestParam String orderTime,@RequestParam Integer orderId){
        return updateTimeService.updateOrderTime(orderTime,orderId);
    }
}

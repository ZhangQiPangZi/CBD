package com.cbd.cbdcontroller.controller.installerapp.waitingtask;

import com.cbd.cbdcommoninterface.cbd_interface.installerapp.waitingtask.InstallOrderService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author: Monster
 * @date: 2020/4/21 19:30
 * @Description
 */
@RestController
@Slf4j
@RequestMapping("/install")
@Api(value = "安装订单处理")
@CrossOrigin
public class InstallOrderController {
    @Autowired
    private InstallOrderService installOrderService;

    @RequestMapping(value = "/install-complete",method = RequestMethod.GET)
    @ApiOperation("安装订单完成")
    @ResponseBody
    public int installOrderComplete(@RequestParam Integer devId,@RequestParam Integer simId,@RequestParam Integer orderId){
        return installOrderService.installOrderComplete(devId,simId,orderId);
    }
}

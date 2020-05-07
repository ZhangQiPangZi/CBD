package com.cbd.cbdcontroller.controller.salesapp.workbench;

import com.cbd.cbdcommoninterface.cbd_interface.salesapp.workbench.InputInfoService;
import com.cbd.cbdcommoninterface.request.salesapp.workbench.OrderInfoQuery;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author: Monster
 * @date: 2020/4/21 15:08
 * @Description
 */
@RestController
@Slf4j
@RequestMapping(value = "/input-info")
@Api(value = "用户信息录入")
@CrossOrigin
public class InputInfoController {
    @Autowired
    private InputInfoService inputInfoService;
    /**
     * 录入信息后 将生成的订单id返回给前端 后期指派工程师会用到
     * @param query
     * @return
     */
    @RequestMapping(value = "confirm",method = RequestMethod.POST)
    @ApiOperation(value = "用户信息录入")
    @ResponseBody
    public int confirmInputInfo(@RequestBody OrderInfoQuery query){
        return inputInfoService.confirmInputInfo(query);
    }
}

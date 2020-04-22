package com.cbd.cbdcontroller.controller.installerapp.completed;

import com.cbd.cbdcommoninterface.cbd_interface.installerapp.completed.CompletedListService;
import com.cbd.cbdcommoninterface.pojo.installerapp.waitingtask.OrderInfoDO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author: Monster
 * @date: 2020/4/22 15:58
 * @Description
 */
@RestController
@Slf4j
@RequestMapping("/completed-list")
@Api(value = "已完成订单展示")
@CrossOrigin
public class CompletedListController {
    @Autowired
    private CompletedListService completedListService;

    @RequestMapping(value = "/get-list",method = RequestMethod.GET)
    @ApiOperation("获取已完成的订单")
    @ResponseBody
    public List<OrderInfoDO> getList(@RequestParam Integer installerId, @RequestParam(required = false) Integer orderTypeCode){
        return completedListService.getList(installerId,orderTypeCode);
    }
}

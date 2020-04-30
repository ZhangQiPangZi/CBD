package com.cbd.cbdcontroller.controller.installerapp.completed;

import com.cbd.cbdcommoninterface.cbd_interface.installerapp.completed.CompletedListService;
import com.cbd.cbdcommoninterface.pojo.installerapp.waitingtask.OrderInfoDO;
import com.cbd.cbdcommoninterface.request.PageRequest;
import com.cbd.cbdcommoninterface.response.PageResponse;
import com.cbd.cbdcommoninterface.result.Result;
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

    @RequestMapping(value = "/get-list",method = RequestMethod.POST)
    @ApiOperation("获取已完成的订单")
    @ResponseBody
    public Result<PageResponse> getList(@RequestParam Integer installerId, @RequestParam(required = false) Integer orderTypeCode,@RequestBody PageRequest pageRequest){
        return Result.success(completedListService.getList(installerId,orderTypeCode,pageRequest));
    }
}

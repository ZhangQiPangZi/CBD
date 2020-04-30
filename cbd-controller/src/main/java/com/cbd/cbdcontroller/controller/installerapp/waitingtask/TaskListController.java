package com.cbd.cbdcontroller.controller.installerapp.waitingtask;

import com.cbd.cbdcommoninterface.cbd_interface.installerapp.waitingtask.TaskListService;
import com.cbd.cbdcommoninterface.pojo.installerapp.waitingtask.OrderInfoDO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author: Monster
 * @date: 2020/4/21 19:32
 * @Description
 */
@RestController
@RequestMapping("/task-list")
@Slf4j
@Api(value = "待处理订单列表")
@CrossOrigin
public class TaskListController {
    @Autowired
    private TaskListService taskListService;

    @RequestMapping(value = "/get-task-list",method = RequestMethod.GET)
    @ApiOperation("获取待处理订单列表")
    @ResponseBody
    public List<OrderInfoDO> getTaskList(@RequestParam Integer installerId, @RequestParam(required = false) Integer orderTypeCode){
        return taskListService.getTaskList(installerId,orderTypeCode);
    }
}

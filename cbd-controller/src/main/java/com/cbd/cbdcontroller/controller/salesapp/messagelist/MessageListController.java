package com.cbd.cbdcontroller.controller.salesapp.messagelist;

import com.cbd.cbdcommoninterface.cbd_interface.salesapp.messagelist.MessageListService;
import com.cbd.cbdcommoninterface.pojo.salesapp.messagelist.OrderInfoDO;
import com.cbd.cbdcommoninterface.response.salesapp.messagelist.OrderInfoVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author: Monster
 * @date: 2020/4/21 15:47
 * @Description
 */
@RestController
@Slf4j
@RequestMapping(value = "/list")
@Api(value = "消息列表")
@CrossOrigin
public class MessageListController {
    @Autowired
    private MessageListService messageListService;

    @RequestMapping(value = "/get-list",method = RequestMethod.GET)
    @ApiOperation("获取订单列表")
    @ResponseBody
    public List<OrderInfoDO> getList(@RequestParam(required = false) Integer orderTypeCode){
        return messageListService.getList(orderTypeCode);
    }

    @RequestMapping(value = "/processed-count",method = RequestMethod.GET)
    @ApiOperation("已指派订单数量")
    @ResponseBody
    public int processedCount(){
        return messageListService.processedCount();
    }

    @RequestMapping(value = "/pending-count",method = RequestMethod.GET)
    @ApiOperation("待指派订单数量")
    @ResponseBody
    public int pendingCount(){
        return messageListService.pendingCount();
    }

    @RequestMapping(value = "/get-order-info",method = RequestMethod.GET)
    @ApiOperation("获取订单详细信息")
    @ResponseBody
    public OrderInfoVO getOrderInfo(@RequestParam Integer id){
        return messageListService.getOrderInfo(id);
    }

    @RequestMapping(value = "/reassign",method = RequestMethod.GET)
    @ApiOperation("重新指派工程师")
    @ResponseBody
    public int reAssign(@RequestParam Integer id){
        return messageListService.reAssign(id);
    }
}

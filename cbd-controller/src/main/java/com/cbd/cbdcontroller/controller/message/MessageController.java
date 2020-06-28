package com.cbd.cbdcontroller.controller.message;

import com.cbd.cbdcommoninterface.cbd_interface.device.DeviceService;
import com.cbd.cbdcommoninterface.cbd_interface.message.EmailService;
import com.cbd.cbdcommoninterface.cbd_interface.message.MessageService;
import com.cbd.cbdcommoninterface.request.GetMessageRequest;
import com.cbd.cbdcommoninterface.request.MesIDRequest;
import com.cbd.cbdcommoninterface.request.PageMsgRequest;
import com.cbd.cbdcommoninterface.request.ReceiverIDRequest;
import com.cbd.cbdcommoninterface.response.ChatAlloteResponse;
import com.cbd.cbdcommoninterface.response.ChatInfoResponse;
import com.cbd.cbdcommoninterface.response.PageResponse;
import com.cbd.cbdcommoninterface.result.Result;
import com.cbd.cbdcommoninterface.utils.ChatMessage;
import com.cbd.cbdcontroller.controller.websocket.ChatServer;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/message")
@Api("消息接口")
@Slf4j
@CrossOrigin
public class MessageController {
    @Autowired
    ChatServer chatServer;
    @Autowired
    MessageService messageService;
    @Autowired
    EmailService emailService;

    @ApiOperation("测试邮箱")
    @RequestMapping(value = "/stringEmaile",method = RequestMethod.GET)
    public String sendStringEmail(){
        emailService.sendStringEmail("zhangqi_qiuzhi@qq.com","你好","我发的你收到了吗？");
        return "发送成功";
    }

    @ApiOperation("拉取未确认消息条数,业务消息")
    @RequestMapping(value = "/pullUnConfirmMsgCounts", method = RequestMethod.POST)
    public Result<Integer> pullUnConfirmMsgCounts(@RequestBody ReceiverIDRequest receiverIDRequest){
        return Result.success(messageService.pullUnConfirmMsgCounts(receiverIDRequest.getReceiverID()));
    }

    @ApiOperation("获取所有发送消息且客服未读的客户列表，\n" +
                  "一。客服上线拉取\n" +
                  "二。websockt监听调用")
    @RequestMapping(value = "/getUnreadCustomerList", method = RequestMethod.POST)
    public Result<PageResponse> getUnreadCustomerList(@RequestBody PageMsgRequest pageMsgRequest){
        return Result.success(messageService.getUnreadCustomerList(pageMsgRequest));
    }

    @ApiOperation("拉取未读消息列表，客服聊天消息")
    @RequestMapping(value = "/pullUnreadMsgList", method = RequestMethod.POST)
    public Result<List<String>> pullUnreadMsgList(@RequestBody ReceiverIDRequest receiverIDRequest){
        return Result.success(messageService.pullUnreadMsgList(receiverIDRequest.getSenderID(),receiverIDRequest.getReceiverID()));
    }

    @ApiOperation("拉取未读消息列表，业务消息")
    @RequestMapping(value = "/pullUnreadBussinessMsgList", method = RequestMethod.POST)
    public Result<PageResponse> pullUnreadBussinessMsgList(@RequestBody PageMsgRequest pageMsgRequest){
        return Result.success(messageService.pullUnreadBussinessMsgList(pageMsgRequest));
    }

    @ApiOperation("拉取已读消息列表，业务消息")
    @RequestMapping(value = "/pullReadBussinessMsgList", method = RequestMethod.POST)
    public Result<PageResponse> pullReadBussinessMsgList(@RequestBody PageMsgRequest pageMsgRequest){
        return Result.success(messageService.pullReadBussinessMsgList(pageMsgRequest));
    }

    @ApiOperation("确认消息")
    @RequestMapping(value = "/confirmMessage", method = RequestMethod.POST)
    public Result<Boolean> confirmMessage(@RequestBody MesIDRequest mesIDRequest){
        return Result.success(messageService.confirmMessage(mesIDRequest.getMesID()));
    }

    @ApiOperation("获取专属客服,传过来客户ID")
    @RequestMapping(value = "/alloteService", method = RequestMethod.POST)
    public Result<ChatAlloteResponse> alloteService(@RequestBody ReceiverIDRequest receiverIDRequest){
        return Result.success(messageService.alloteService(receiverIDRequest.getSenderID()));
    }

    @ApiOperation("拉取聊天历史消息")
    @RequestMapping(value = "/pullHistoryMsg", method = RequestMethod.POST)
    public Result<List<ChatMessage>> pullHistoryMsg(@RequestBody ReceiverIDRequest receiverIDRequest){
        return Result.success(messageService.pullHistoryMsg(receiverIDRequest.getSenderID(), receiverIDRequest.getReceiverID()));
    }
}

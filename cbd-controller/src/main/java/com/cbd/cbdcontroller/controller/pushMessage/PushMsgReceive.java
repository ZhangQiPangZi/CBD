package com.cbd.cbdcontroller.controller.pushMessage;

import com.alibaba.fastjson.JSON;
import com.cbd.cbdcommoninterface.cbd_interface.message.MessageService;
import com.cbd.cbdcommoninterface.utils.BusinessType;
import com.cbd.cbdcommoninterface.utils.ChatMessage;
import com.cbd.cbdcontroller.controller.websocket.ChatServer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class PushMsgReceive {
    @Autowired
    MessageService messageService;

    /**
     * 推送业务消息
     * @param message
     */
    @RabbitListener(queues = BusinessType.CBD_BUSINESS_QUEUE)
    public void pushMsg(String message){
        //json格式转化
        message = message.replaceAll("\\\\","");
        ChatMessage chatMessage = JSON.parseObject(message, ChatMessage.class);

        //查出业务消息未读条数
        Integer unReadCounts = messageService.pullUnConfirmMsgCounts(chatMessage.getReceiverId());
        chatMessage.setContent(chatMessage.getContent()+"您还有未读消息"+unReadCounts+"条，请尽快查看并处理！！！");
        log.info("业务消息，内容：{}",chatMessage.getContent());

        //推送消息
        ChatServer.sendMessage(JSON.toJSON(chatMessage).toString(), chatMessage.getReceiverId());
    }

}

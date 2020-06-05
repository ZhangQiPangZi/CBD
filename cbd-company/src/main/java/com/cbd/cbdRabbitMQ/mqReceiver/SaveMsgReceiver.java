package com.cbd.cbdRabbitMQ.mqReceiver;

import com.alibaba.fastjson.JSON;
import com.cbd.cbdcommoninterface.utils.BusinessType;
import com.cbd.cbdcommoninterface.utils.ChatMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class SaveMsgReceiver {

    @RabbitListener(queues = BusinessType.SAVE_MESSAGE_QUEUE)
    public void saveMessage(String message){
        //json格式转化
        message = message.replaceAll("\\\\","");
        message = message.substring(1,message.length()-1);
        ChatMessage chatMessage = JSON.parseObject(message, ChatMessage.class);

    }
}

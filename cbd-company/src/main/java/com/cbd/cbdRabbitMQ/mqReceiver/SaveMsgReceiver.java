package com.cbd.cbdRabbitMQ.mqReceiver;

import com.alibaba.fastjson.JSON;
import com.cbd.cbdcommoninterface.cbd_interface.message.MessageService;
import com.cbd.cbdcommoninterface.result.CodeMsg;
import com.cbd.cbdcommoninterface.result.GlobalException;
import com.cbd.cbdcommoninterface.utils.BusinessType;
import com.cbd.cbdcommoninterface.utils.ChatMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
public class SaveMsgReceiver {
    @Autowired
    private MessageService messageService;

    @RabbitListener(queues = BusinessType.SAVE_MESSAGE_QUEUE)
    @Transactional(rollbackFor = Exception.class)
    public void saveMessage(String message){
        //json格式转化
        message = message.replaceAll("\\\\","");
        ChatMessage chatMessage = JSON.parseObject(message, ChatMessage.class);

        log.info("save msg:{}",message);
        try{
            messageService.saveMessage(chatMessage);
        }catch (Exception e){
            log.info("保存信息错误:{}", e.toString());
            throw new GlobalException(CodeMsg.SERVER_ERROR);
        }
    }
}

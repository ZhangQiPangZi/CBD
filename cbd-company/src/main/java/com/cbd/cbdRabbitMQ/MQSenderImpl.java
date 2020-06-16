package com.cbd.cbdRabbitMQ;

import com.cbd.cbdcommoninterface.cbd_interface.MQ.MQSender;
import com.cbd.cbdcommoninterface.utils.BusinessMessage;
import com.cbd.cbdcommoninterface.utils.JsonUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class MQSenderImpl implements MQSender {

    @Autowired
    AmqpTemplate amqpTemplate;

    @Override
    public void send(BusinessMessage message){

        String context = JsonUtils.objectToJson(message.getContext());
        log.info( "开始发送消息，类型为{}", message.getBusinessType());
        amqpTemplate.convertAndSend(message.getBusinessType(),context);
}

}

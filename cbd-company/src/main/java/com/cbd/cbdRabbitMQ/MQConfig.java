package com.cbd.cbdRabbitMQ;

import com.cbd.cbdcommoninterface.utils.BusinessType;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class MQConfig {
    @Bean
    public Queue saveMessageQueue(){
        return new Queue(BusinessType.SAVE_MESSAGE_QUEUE, true);
    }

    @Bean
    public Queue businessQueue(){
        return new Queue(BusinessType.CBD_BUSINESS_QUEUE, true);
    }
}

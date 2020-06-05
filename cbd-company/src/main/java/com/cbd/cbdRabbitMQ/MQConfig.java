package com.cbd.cbdRabbitMQ;

import com.cbd.cbdcommoninterface.utils.BusinessType;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class MQConfig {
    @Bean
    public Queue userMessageInfoQueue() {
        return new Queue(BusinessType.USER_MESSAGE_QUEUE, true);
    }
}

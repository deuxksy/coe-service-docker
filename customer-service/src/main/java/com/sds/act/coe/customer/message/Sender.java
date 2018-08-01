package com.sds.act.coe.customer.message;


import com.sds.act.coe.customer.config.MessageQueueConfig;
import org.springframework.amqp.rabbit.core.RabbitMessagingTemplate;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

@Component
@Lazy
public class Sender {

    RabbitMessagingTemplate template;

    public Sender(RabbitMessagingTemplate template) {
        this.template = template;
    }

    public void send(String message) {
        template.convertAndSend(MessageQueueConfig.QUEUE_NAME, message);
    }
}

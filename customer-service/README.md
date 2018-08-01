# Customer Service

## Description

본 프로젝트는 Microservice를 위한 Sample Project 입니다.

## Require Software

- [RabbitMQ setup](./RabbitMQREADME.md)


## 설정방법

1. Dependency를 추가한다. 
```xml
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-amqp</artifactId>
    </dependency>
``` 

2. application.yml에 rabbitmq 설정 정보를 추가한다.
```yaml
spring:
  rabbitmq:
    host: localhost
    port: 5672
    username: username
    password: password
```

3. Create a Message Sender
```java
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
```

4. Create a Message Receiver - [Email Service](https://github.com/SDSACT/coe-email-service/blob/master/README.md)
```java
    import com.sds.act.coe.customer.config.MessageQueueConfig;
    import org.springframework.amqp.rabbit.annotation.RabbitListener;
    import org.springframework.beans.factory.annotation.Autowired;
    import org.springframework.stereotype.Component;
    
    @Component
    public class Receiver {
        private Mailer mailer;
        
        @Autowired
        public Receiver(Mailer mailer) {
            this.mailer = mailer;
        }
        
        @RabbitListener(queues = MessageQueueConfig.QUEUE_NAME)
        public void processMessage(String email) {
            System.out.println("get Message:" + email);
            mailer.sendMail(email);
        }
    }
```


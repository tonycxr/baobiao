package com.sungcor.baobiao.utils;

import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
@RabbitListener(queues = "directQueue")//监听队列名称
public class MQReciever {

    @RabbitHandler
    public void process(String message){
        System.out.println("接收到的消息是："+ message);
    }
}

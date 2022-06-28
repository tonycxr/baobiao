package com.sungcor.baobiao.controller;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SendMessageController {


    @Autowired
    private RabbitTemplate rabbitTemplate;


    @GetMapping("/sendMessage")
    public String sendMessage(){
        //将消息携带路由键值
        rabbitTemplate.convertAndSend("directExchange", "directRouting", "发送消息！");
        return "ok";
    }


}


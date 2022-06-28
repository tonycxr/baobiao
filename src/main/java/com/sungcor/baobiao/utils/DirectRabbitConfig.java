package com.sungcor.baobiao.utils;


import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DirectRabbitConfig {


    /**
     * 定义交换机
     **/
    @Bean
    public DirectExchange directExchange(){
        /**
         * 交换机名称
         * 持久性标志：是否持久化,默认是 true 即声明一个持久的 exchange,该exchange将在服务器重启后继续运行
         * 自动删除标志：是否自动删除，默认为 false, 如果服务器想在 exchange不再使用时删除它，则设置为 true
         **/
        return new DirectExchange("directExchange", true, false);
    }

    /**
     * 定义队列
     **/
    @Bean
    public Queue directQueue(){
        /**
         * name：队列名称
         * durable：是否持久化,默认是 true,持久化队列，会被存储在磁盘上，当消息代理重启时仍然存在
         * exclusive：是否排他，默认为 false，true则表示声明了一个排他队列（该队列将仅由声明者连接使用），如果连接关闭，则队列被删除。此参考优先级高于durable
         * autoDelete：是否自动删除， 默认是 false，true则表示当队列不再使用时，服务器删除该队列
         **/
        return new Queue("directQueue",true);
    }


    /**
     * 队列和交换机绑定
     * 设置路由键：directRouting
     **/
    @Bean
    Binding bindingDirect(){
        return BindingBuilder.bind(directQueue()).to(directExchange()).with("directRouting");
    }




}

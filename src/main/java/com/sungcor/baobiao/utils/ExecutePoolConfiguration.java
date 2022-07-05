package com.sungcor.baobiao.utils;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

/**
 * 线程池配置
 *
 * @author
 *
 */
@Configuration
public class ExecutePoolConfiguration {

    @Bean(name = "threadPoolTaskExecutor")
    public ThreadPoolTaskExecutor threadPoolTaskExecutor() {
        ThreadPoolTaskExecutor pool = new ThreadPoolTaskExecutor();
        pool.setKeepAliveSeconds(300);
        pool.setCorePoolSize(80); // 核心线程池数
        pool.setMaxPoolSize(20); // 最大线程
        pool.setQueueCapacity(1000);
        pool.setThreadNamePrefix("ProductPool");// 队列容量
        pool.setRejectedExecutionHandler(new java.util.concurrent.ThreadPoolExecutor.CallerRunsPolicy());
        pool.initialize();
        // 队列满，线程被拒绝执行策略
        return pool;
    }


}

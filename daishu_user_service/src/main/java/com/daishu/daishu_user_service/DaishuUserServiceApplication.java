package com.daishu.daishu_user_service;

import com.alibaba.dubbo.config.spring.context.annotation.EnableDubbo;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableDubbo
public class DaishuUserServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(DaishuUserServiceApplication.class, args);
    }

}

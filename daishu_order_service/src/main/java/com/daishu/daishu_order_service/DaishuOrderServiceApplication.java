package com.daishu.daishu_order_service;

import com.alibaba.dubbo.config.spring.context.annotation.EnableDubbo;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableDubbo
public class DaishuOrderServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(DaishuOrderServiceApplication.class, args);
    }

}

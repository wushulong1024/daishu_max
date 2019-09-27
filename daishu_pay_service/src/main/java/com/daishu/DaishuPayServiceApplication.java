package com.daishu;

import com.alibaba.dubbo.config.spring.context.annotation.EnableDubbo;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableDubbo
public class DaishuPayServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(DaishuPayServiceApplication.class, args);
    }

}

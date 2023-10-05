package com.zhou;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.zhou.mapper")
public class ZhouApplication {
    public static void main(String[] args) {
        SpringApplication.run(ZhouApplication.class,args);
    }
}

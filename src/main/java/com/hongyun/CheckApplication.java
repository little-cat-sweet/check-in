package com.hongyun;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
@MapperScan("com.hongyun.mapper")
public class CheckApplication {

    public static void main(String[] args) {
        SpringApplication.run(CheckApplication.class, args);
        System.out.println("start successfully !");
    }

}

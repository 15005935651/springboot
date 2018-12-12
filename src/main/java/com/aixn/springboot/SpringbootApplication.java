package com.aixn.springboot;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication(scanBasePackages = {"com.aixn.springboot"})
@MapperScan("com.aixn.springboot.dao")
public class SpringbootApplication {



    public static void main(String[] args) {
        SpringApplication.run(SpringbootApplication.class, args);
    }
}

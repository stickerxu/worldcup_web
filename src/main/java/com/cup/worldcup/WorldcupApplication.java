package com.cup.worldcup;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@MapperScan(basePackages = {"com.cup.worldcup.mapper"})
public class WorldcupApplication {

    public static void main(String[] args) {
        SpringApplication.run(WorldcupApplication.class, args);
    }
}

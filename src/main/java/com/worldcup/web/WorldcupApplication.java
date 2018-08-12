package com.worldcup.web;

import com.worldcup.web.config.MethonInterceptor;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.ArrayList;
import java.util.List;

@SpringBootApplication
@MapperScan(basePackages = {"com.worldcup.web.mapper"})
public class WorldcupApplication {

    public static void main(String[] args) {
        SpringApplication.run(WorldcupApplication.class, args);
    }

    @Configuration
    @PropertySource("classpath:application.properties")
    class WorldcupMvcConfig implements WebMvcConfigurer {

        @Autowired
        private MethonInterceptor methonInterceptor;

        @Override
        public void addInterceptors(InterceptorRegistry registry) {
            registry.addInterceptor(methonInterceptor).addPathPatterns(filterPath());
        }
        private List<String> filterPath() {
            List<String> list = new ArrayList<>();
            list.add("/breakwall/**");
            list.add("/logout");
            list.add("/user/**");
            list.add("/adm/**");
            return list;
        }

        @Override
        public void addViewControllers(ViewControllerRegistry registry) {

        }
    }
}

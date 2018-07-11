package com.cup.worldcup;

import com.cup.worldcup.config.MethonInterceptor;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.ArrayList;
import java.util.List;

@SpringBootApplication
@MapperScan(basePackages = {"com.cup.worldcup.mapper"})
public class WorldcupApplication {

    public static void main(String[] args) {
        SpringApplication.run(WorldcupApplication.class, args);
    }

    @Configuration
    class WorldcupMvcConfig implements WebMvcConfigurer {

        @Autowired
        private MethonInterceptor methonInterceptor;

        @Override
        public void addResourceHandlers(ResourceHandlerRegistry registry) {
            registry.addResourceHandler("/static/**").addResourceLocations("/static/");
        }

        @Override
        public void addInterceptors(InterceptorRegistry registry) {
            registry.addInterceptor(methonInterceptor).addPathPatterns("/**").excludePathPatterns(excludePath());
        }
        private List<String> excludePath() {
            List<String> list = new ArrayList<>();
            list.add("/");
            list.add("/login");
            list.add("/registry");
            list.add("/loginSub");
            list.add("/login/findPass");
            list.add("/login/findPassNext");
            list.add("/login/findPassNextTwo");
            list.add("/login/findPassSub");
            list.add("/img/icon/lakers-icon.ico");
            return list;
        }
    }
}

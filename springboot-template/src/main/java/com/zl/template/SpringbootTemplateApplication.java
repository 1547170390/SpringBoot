package com.zl.template;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan(basePackages = "com.zl.template.mapper")
public class SpringbootTemplateApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringbootTemplateApplication.class, args);
    }

}

package com.cbd.cbdcontroller;

import com.alibaba.dubbo.spring.boot.annotation.EnableDubboConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.ImportResource;


@EnableDubboConfiguration
@ImportResource(locations = {"classpath:dubbo.xml"})
@SpringBootApplication(exclude= {DataSourceAutoConfiguration.class})
public class CbdControllerApplication extends SpringBootServletInitializer {

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
        return builder.sources(CbdControllerApplication.class);
    }

    public static void main(String[] args) {
        SpringApplication.run(CbdControllerApplication.class, args);
    }

}

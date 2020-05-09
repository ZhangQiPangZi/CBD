package com.cbd.cbddevice;

import com.alibaba.dubbo.spring.boot.annotation.EnableDubboConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.ImportResource;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@EnableDubboConfiguration
@ImportResource(locations = {"classpath:dubbo.xml"})
@SpringBootApplication
@EnableTransactionManagement
public class CbdDeviceApplication extends SpringBootServletInitializer {

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
        return builder.sources(CbdDeviceApplication.class);
    }
    public static void main(String[] args) {
        SpringApplication.run(CbdDeviceApplication.class, args);
    }

}

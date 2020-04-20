package com.cbd.cbddevice;

import com.alibaba.dubbo.spring.boot.annotation.EnableDubboConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ImportResource;

@EnableDubboConfiguration
@ImportResource(locations = {"classpath:dubbo.xml"})
@SpringBootApplication
public class CbdDeviceApplication {

    public static void main(String[] args) {
        SpringApplication.run(CbdDeviceApplication.class, args);
    }

}

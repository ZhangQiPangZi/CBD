package com.cbd.installerapp;

import com.alibaba.dubbo.spring.boot.annotation.EnableDubboConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ImportResource;

@EnableDubboConfiguration
@ImportResource(locations = {"classpath:dubbo.xml"})
@SpringBootApplication
public class CbdInstallerApplication {

    public static void main(String[] args) {
        SpringApplication.run(CbdInstallerApplication.class, args);
    }

}

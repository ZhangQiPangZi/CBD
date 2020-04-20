package com.cbd.cbdredis;

import com.alibaba.dubbo.spring.boot.annotation.EnableDubboConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.ImportResource;

@EnableDubboConfiguration
@ImportResource(locations = {"classpath:dubbo.xml"})
@SpringBootApplication(exclude= {DataSourceAutoConfiguration.class})
public class CbdRedisApplication {

    public static void main(String[] args) {
        SpringApplication.run(CbdRedisApplication.class, args);
    }

}

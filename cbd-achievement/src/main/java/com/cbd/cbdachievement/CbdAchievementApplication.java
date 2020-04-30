package com.cbd.cbdachievement;

import com.alibaba.dubbo.spring.boot.annotation.EnableDubboConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ImportResource;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@EnableDubboConfiguration
@ImportResource(locations = {"classpath:dubbo.xml"})
@SpringBootApplication
@EnableTransactionManagement
public class CbdAchievementApplication {

    public static void main(String[] args) {
        SpringApplication.run(CbdAchievementApplication.class, args);
    }

}

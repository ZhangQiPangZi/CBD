package com.cbd.cbdcontroller.qwe.authentication;

import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author shy_black
 * @date 2020/5/31 14:47
 * @Description:
 *
 *
 *   访问路径配置类
 *   可以理解成做简单访问过滤的，转发到相应的视图页面
 */
public class WebMvcConfig implements WebMvcConfigurer {

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/login").setViewName("login");
        registry.addViewController("/").setViewName("index");
        registry.addViewController("/index").setViewName("index");
    }


}

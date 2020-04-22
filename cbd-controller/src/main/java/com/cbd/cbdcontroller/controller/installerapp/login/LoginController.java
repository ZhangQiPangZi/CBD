package com.cbd.cbdcontroller.controller.installerapp.login;

import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author: Monster
 * @date: 2020/4/21 19:28
 * @Description
 */
@RestController
@Slf4j
@RequestMapping("/login")
@Api(value = "登录")
@CrossOrigin
public class LoginController {
    //TODO 这里调用张磊的登录接口
}

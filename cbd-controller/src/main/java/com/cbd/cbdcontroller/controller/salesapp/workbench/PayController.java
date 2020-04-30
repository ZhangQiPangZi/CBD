package com.cbd.cbdcontroller.controller.salesapp.workbench;

import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author: Monster
 * @date: 2020/4/21 15:06
 * @Description
 */
@RestController
@Slf4j
@RequestMapping(value = "/pay")
@Api(value = "支付")
@CrossOrigin
public class PayController {
    //TODO 微信支付



    //TODO 支付宝支付
}

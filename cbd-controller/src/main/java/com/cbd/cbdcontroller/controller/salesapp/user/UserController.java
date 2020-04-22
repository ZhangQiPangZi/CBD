package com.cbd.cbdcontroller.controller.salesapp.user;

import com.cbd.cbdcommoninterface.cbd_interface.salesapp.user.UserService;
import com.cbd.cbdcommoninterface.pojo.salesapp.user.SalesInfoDO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author: Monster
 * @date: 2020/4/20 20:42
 * @Description
 */
@RestController
@Slf4j
@RequestMapping("/user")
@Api(value = "用户信息")
@CrossOrigin
public class UserController {
    @Autowired
    private UserService userService;

    @RequestMapping(value = "/user-info",method = RequestMethod.GET)
    @ResponseBody
    @ApiOperation("用户信息")
    public SalesInfoDO getUserInfo(@RequestParam String phoneNumber){
        return userService.getUserInfo(phoneNumber);
    }
}

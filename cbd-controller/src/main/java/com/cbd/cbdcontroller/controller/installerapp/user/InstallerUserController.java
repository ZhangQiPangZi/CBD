package com.cbd.cbdcontroller.controller.installerapp.user;

import com.cbd.cbdcommoninterface.cbd_interface.installerapp.user.InstallerUserService;
import com.cbd.cbdcommoninterface.pojo.installerapp.user.InstallerInfoDO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author: Monster
 * @date: 2020/4/21 19:29
 * @Description
 */
@RestController
@Slf4j
@RequestMapping("/installer-user")
@CrossOrigin
@Api(value = "用户信息")
public class InstallerUserController {

    @Autowired
    private InstallerUserService installerUserService;

    @RequestMapping(value = "/installer-user-info",method = RequestMethod.GET)
    @ApiOperation("用户信息")
    @ResponseBody
    public InstallerInfoDO getUserInfo(@RequestParam String phoneNumber){
        return installerUserService.getUserInfo(phoneNumber);
    }

}

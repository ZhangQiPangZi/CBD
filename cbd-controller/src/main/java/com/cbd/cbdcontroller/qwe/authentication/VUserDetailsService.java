package com.cbd.cbdcontroller.qwe.authentication;


import com.cbd.cbdcommoninterface.cbd_interface.user.ILoginService;
import com.cbd.cbdcommoninterface.cbd_interface.user.IPowerService;
import com.cbd.cbdcommoninterface.cbd_interface.user.IRoleDefineService;
import com.cbd.cbdcommoninterface.pojo.leipojo.power;
import com.cbd.cbdcommoninterface.pojo.leipojo.role;
import com.cbd.cbdcommoninterface.pojo.leipojo.user;
import com.cbd.cbdcommoninterface.result.CodeMsg;
import com.cbd.cbdcommoninterface.result.GlobalException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author shy_black
 * @date 2020/5/31 14:30
 * @Description:
 */

@Slf4j
@Component
public class VUserDetailsService implements UserDetailsService {
    @Autowired
    ILoginService loginService;

    @Autowired
    IRoleDefineService roleDefineService;

    @Autowired
    IPowerService powerService;

    /**
     *
     * @param username
     * @return
     * @throws UsernameNotFoundException
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        log.info("username = " + username);


        List<role> roleList = null;
        List<power> powerList = null;
        //获取用户详情
        user userInfo = loginService.findUserByPhoneNum(username);
        try {
            //角色列表，获取该用户所有角色
             roleList = roleDefineService.getUserRoleByID(userInfo.getID());
            if (roleList.size() > 0) {
                log.info("角色列表：roleList ：" + roleList.toString());
            }
            //权限列表，获取role_power中status为1的数据 且power中status为1的数据
             powerList = powerService.getPowerListByUserID(userInfo.getID());

            if (powerList.size() > 0) {
                log.info("权限列表：powerList ：" + powerList.toString());
            }
        }catch (Exception e) {
            throw new GlobalException(CodeMsg.SERVER_ERROR);
        }

        return new VUserDetails(userInfo,roleList,powerList);

    }
}

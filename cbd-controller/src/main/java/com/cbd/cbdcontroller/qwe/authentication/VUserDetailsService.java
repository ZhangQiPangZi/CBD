package com.cbd.cbdcontroller.qwe.authentication;


import com.cbd.cbdcommoninterface.cbd_interface.user.ILoginService;
import com.cbd.cbdcommoninterface.cbd_interface.user.IPowerService;
import com.cbd.cbdcommoninterface.cbd_interface.user.IRoleDefineService;
import com.cbd.cbdcommoninterface.pojo.leipojo.power;
import com.cbd.cbdcommoninterface.pojo.leipojo.role;
import com.cbd.cbdcommoninterface.pojo.leipojo.user;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.List;

/**
 * @author shy_black
 * @date 2020/5/31 14:30
 * @Description:
 */
public class VUserDetailsService implements UserDetailsService {
    @Autowired
    ILoginService loginService;

    @Autowired
    IRoleDefineService roleDefineService;

    @Autowired
    IPowerService powerService;

    /**
     *
     * @param phoneNum
     * @return
     * @throws UsernameNotFoundException
     */
    @Override
    public UserDetails loadUserByUsername(String phoneNum) throws UsernameNotFoundException {

        //获取用户详情
        user userInfo = loginService.findUserByPhoneNum(phoneNum);
        //角色列表
        List<role> roleList = roleDefineService.getUserRoleByID(userInfo.getID());
        //权限列表
        List<power> powerList = powerService.getPowerListByUserID(userInfo.getID());

        return new VUserDetails(userInfo,roleList,powerList);

    }
}

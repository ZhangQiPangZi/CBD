package com.cbd.cbdcontroller.qwe.test;


import com.cbd.cbdcommoninterface.pojo.leipojo.power;
import com.cbd.cbdcommoninterface.pojo.leipojo.role;
import com.cbd.cbdcommoninterface.pojo.leipojo.user;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author shy_black
 * @date 2020/5/31 13:40
 * @Description:
 *
 * 为security所需内容提供便利
 */
@Service
public class SecurityDataService {

    @Autowired
    private userDao userDao;

    @Autowired
    private roleDao roleDao;

    @Autowired
    private powerDao powerDao;

    public user findUserByPhoneNum(String phone){
        return new user();
    }

    public List<role> findSRoleListBySPermissionUrl(String url) {
        return null;
    }

    public  List<power> findSPermissionListBySPermissionUrl(String url) {
        return null;
    }

}

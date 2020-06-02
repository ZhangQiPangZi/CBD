package com.black.lei.service.Impl;


import com.black.lei.dao.powerDao;
import com.black.lei.dao.roleDao;
import com.black.lei.dao.userDao;
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
    private com.black.lei.dao.userDao userDao;

    @Autowired
    private com.black.lei.dao.roleDao roleDao;

    @Autowired
    private com.black.lei.dao.powerDao powerDao;

    public user findUserByPhoneNum(String phoneNum){
        return userDao.findByPhone(phoneNum);
    }
    public List<role> findSRoleListBySPermissionUrl(String url) {
        return roleDao.getRoleListByPowerUrl(url);
    }

    public  List<power> findSPermissionListBySPermissionUrl(String url) {
        return null;
    }

}

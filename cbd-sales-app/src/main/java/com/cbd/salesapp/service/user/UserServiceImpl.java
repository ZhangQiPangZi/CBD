package com.cbd.salesapp.service.user;

import com.cbd.cbdcommoninterface.cbd_interface.salesapp.user.UserService;
import com.cbd.cbdcommoninterface.pojo.salesapp.user.SalesInfoDO;
import com.cbd.salesapp.dao.user.UserDao;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author: Monster
 * @date: 2020/4/20 20:45
 * @Description
 */
public class UserServiceImpl implements UserService {
    @Autowired
    private UserDao userDao;

    @Override
    public SalesInfoDO getUserInfo(String phoneNum){
        return userDao.getUserInfo(phoneNum);
    }
}

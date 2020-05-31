package com.cbd.installerapp.service.user;

import com.cbd.cbdcommoninterface.cbd_interface.installerapp.user.InstallerUserService;
import com.cbd.cbdcommoninterface.pojo.installerapp.user.InstallerInfosDO;
import com.cbd.installerapp.dao.user.InstallerUserDao;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author: Monster
 * @date: 2020/4/21 19:49
 * @Description
 */
public class InstallerUserServiceImpl implements InstallerUserService {
    @Autowired
    private InstallerUserDao installerUserDao;
    @Override
    public InstallerInfosDO getUserInfo(String phoneNumber){
        return installerUserDao.getUserInfo(phoneNumber);
    }
}

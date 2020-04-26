package com.cbd.cbdcommoninterface.cbd_interface.installerapp.user;

import com.cbd.cbdcommoninterface.pojo.installerapp.user.InstallerInfoDO;

/**
 * @author: Monster
 * @date: 2020/4/21 19:47
 * @Description
 */
public interface InstallerUserService {

    /**
     * 查看工程师的个人信息
     * @param phoneNumber
     * @return
     */
    InstallerInfoDO getUserInfo(String phoneNumber);
}
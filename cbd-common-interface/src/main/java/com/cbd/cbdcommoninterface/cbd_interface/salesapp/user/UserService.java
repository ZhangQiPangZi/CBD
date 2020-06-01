package com.cbd.cbdcommoninterface.cbd_interface.salesapp.user;

import com.cbd.cbdcommoninterface.pojo.salesapp.user.SalesInfoDO;

/**
 * @author: Monster
 * @date: 2020/4/20 20:44
 * @Description
 */
public interface UserService {
    /**
     * 根据用户的电话号码查询用户的详细信息
     * @param phoneNum
     * @return
     */
    SalesInfoDO getUserInfo(String phoneNum);
}

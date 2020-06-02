package com.cbd.cbdcommoninterface.cbd_interface.user;


import com.cbd.cbdcommoninterface.pojo.leipojo.power;
import com.cbd.cbdcommoninterface.pojo.leipojo.role;
import com.cbd.cbdcommoninterface.pojo.leipojo.user;
import com.cbd.cbdcommoninterface.response.leiVo.InstallerVo;
import com.cbd.cbdcommoninterface.response.leiVo.LoginVo;

import java.util.List;

/**
 * @author shy_black
 * @date 2020/4/22 10:09
 * @Description:
 */
public interface ILoginService {

    public user checkLogin(LoginVo loginVo);

    InstallerVo installerLogin(LoginVo loginVo);

    user findUserByPhoneNum(String phoneNum);

    //    public user findUserByPhoneNum(String phone){
    //        return new user();
    //    }
//     List<role> findSRoleListBySUserId(int sUserId);

    List<role> findSRoleListBySPermissionUrl(String url);

     List<power> findSPermissionListBySUserId(int userID);

     List<power> findSPermissionListBySPermissionUrl(String url);






    }

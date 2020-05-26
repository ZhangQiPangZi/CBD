package com.cbd.cbdcommoninterface.cbd_interface.user;


import com.cbd.cbdcommoninterface.pojo.leipojo.user;
import com.cbd.cbdcommoninterface.response.leiVo.InstallerVo;
import com.cbd.cbdcommoninterface.response.leiVo.LoginVo;

/**
 * @author shy_black
 * @date 2020/4/22 10:09
 * @Description:
 */
public interface ILoginService {

    public user checkLogin(LoginVo loginVo);

    InstallerVo installerLogin(LoginVo loginVo);

}

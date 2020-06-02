package com.black.lei.service.Impl;


import com.black.lei.dao.powerDao;
import com.black.lei.dao.roleDao;
import com.cbd.cbdcommoninterface.cbd_interface.user.ILoginService;
import com.cbd.cbdcommoninterface.pojo.leipojo.power;
import com.cbd.cbdcommoninterface.pojo.leipojo.role;
import com.cbd.cbdcommoninterface.pojo.leipojo.user;
import com.cbd.cbdcommoninterface.response.leiVo.InstallerVo;
import com.cbd.cbdcommoninterface.response.leiVo.LoginVo;
import com.cbd.cbdcommoninterface.result.CodeMsg;
import com.cbd.cbdcommoninterface.result.GlobalException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author shy_black
 * @date 2020/4/22 10:00
 * @Description:一点
 */
@Slf4j
@Service
public class loginService implements ILoginService {

    @Resource
    private com.black.lei.dao.userDao userDao;

    @Resource
    roleDao roleDao;

    @Resource
    powerDao powerDao;

    @Override
    public user checkLogin(LoginVo loginVo) {
        user userInfo = userDao.findByPhone(loginVo.getPhoneNum());
        if (userInfo == null) {
            log.info("数据库中没有该员工");
            throw new GlobalException(CodeMsg.STAFF_NAME_ERROR);
        } else {
            log.info("该用户密码 ==", userInfo.getPassword());
            if (!userInfo.getPassword().equals(loginVo.getPassword())) {
                throw new GlobalException(CodeMsg.PASSWORD_ERROR);
            }
        }


        //获取用户ID，userName，phoneNum，companyID，status，email，userType--from user表
        //companyName--from company_info表
        //

        return userInfo;
    }

    public user findUserByPhoneNum(String phoneNum) {
        return userDao.findByPhone(phoneNum);

    }

//    @Override
//    public List<role> findSRoleListBySUserId(int userID) {
//        return roleDao.getRoleInfoByUserID(userID);
//    }

    @Override
    public List<role> findSRoleListBySPermissionUrl(String url) {
        return roleDao.findSRoleListBySPermissionUrl(url);

    }

    @Override
    public List<power> findSPermissionListBySUserId(int userID) {
        return null;
    }

    @Override
    public List<power> findSPermissionListBySPermissionUrl(String url) {
        return powerDao.findSPermissionListBySPermissionUrl(url);
    }


    @Override
    public InstallerVo installerLogin(LoginVo loginVo) {

        InstallerVo IV = new InstallerVo();

        IV = userDao.findInstallerByPhone(loginVo.getPhoneNum(), loginVo.getPassword());

        return IV;
    }
}

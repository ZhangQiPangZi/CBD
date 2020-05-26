package com.black.lei.service.Impl;


import com.black.lei.beans.user;
import com.black.lei.exception.GlobalException;
import com.black.lei.result.CodeMsg;
import com.black.lei.service.ILoginService;
import com.black.lei.vo.InstallerVo;
import com.black.lei.vo.LoginVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

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

    @Override
    public user checkLogin(LoginVo loginVo) {
        user userInfo = userDao.findByPhone(loginVo.getPhoneNum());
        if(userInfo == null) {
            log.info("数据库中没有该员工");
            throw new GlobalException(CodeMsg.STAFF_NAME_ERROR);
        }else{
            log.info("该用户密码 ==",userInfo.getPassword());
            if(!userInfo.getPassword().equals(loginVo.getPassword()) ) {
                throw new GlobalException(CodeMsg.PASSWORD_ERROR);
            }
        }



        //获取用户ID，userName，phoneNum，companyID，status，email，userType--from user表
        //companyName--from company_info表
        //

        return userInfo;
    }




    @Override
    public InstallerVo installerLogin(LoginVo loginVo) {

        InstallerVo IV = new InstallerVo();

        IV = userDao.findInstallerByPhone(loginVo.getPhoneNum(),loginVo.getPassword());

        return IV;
    }
}

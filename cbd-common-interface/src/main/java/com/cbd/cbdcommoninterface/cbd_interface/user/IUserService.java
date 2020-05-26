package com.cbd.cbdcommoninterface.cbd_interface.user;


import com.cbd.cbdcommoninterface.pojo.leipojo.user;
import com.cbd.cbdcommoninterface.response.leiVo.*;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author shy_black
 * @date 2020/4/22 10:08
 * @Description:
 */
@Service
public interface IUserService {



    String getPowerResource(String ID, String strName);

    List<user> findAllUser(String companyID);

    UserBaseInfoAndPowerInfoVo login(String phoneNum, String password);

    Integer updateUserInfo(UpdateUserVo updateUserVo);

    Integer hasFindbyPhone(String phoneNum);

    Integer isPasswordCorrect(String phoneNum, String password);

    PageResult findAllUserByPage(PageRequest pageRequest);

//    PageInfo<UserResponseVo> getAllUserPageInfo(PageRequest pageRequest);

    PageResult findUserByPhoneNumOrByUserName(PageRequest pageRequest, String key);

    PageResult findCarOwer(PageRequest pageRequest, Integer userType);

    Integer addUserInfo(AddUserVo addUserVo);

    //InstallerVo findInstallerInfoByPhoneNum(String phoneNum);

}

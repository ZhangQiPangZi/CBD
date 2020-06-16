package com.cbd.cbdcommoninterface.cbd_interface.user;


import com.cbd.cbdcommoninterface.pojo.leipojo.user;
import com.cbd.cbdcommoninterface.response.PageResponse;
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

    UserBaseInfoAndPowerInfoVo login(String phoneNum);

    Integer updateUserInfo(UpdateUserVo updateUserVo);

    Integer hasFindbyPhone(String phoneNum);

    Integer isPasswordCorrect(String phoneNum, String password);

    PageResponse findAllUserByPage(LPageRequest LPageRequest);

//    PageInfo<UserResponseVo> getAllUserPageInfo(LPageRequest pageRequest);

    PageResponse findUserByPhoneNumOrByUserName(LPageRequest LPageRequest, String key);

    PageResponse findCarOwer(LPageRequest LPageRequest, Integer userType);

    Integer addUserInfo(AddUserVo addUserVo);

    Integer addInstallerInfo(AddUserVo addUserVo);

    String findUserCPYIDByUserID(Integer userID);

    user findUserInfoByID(String ID);

    //InstallerVo findInstallerInfoByPhoneNum(String phoneNum);

}

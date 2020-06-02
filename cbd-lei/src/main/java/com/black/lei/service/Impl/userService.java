package com.black.lei.service.Impl;


import com.black.lei.dao.user_installerDao;
import com.cbd.cbdcommoninterface.cbd_interface.user.IUserService;
import com.cbd.cbdcommoninterface.pojo.leipojo.role;
import com.cbd.cbdcommoninterface.pojo.leipojo.user;
import com.cbd.cbdcommoninterface.response.PageResponse;
import com.cbd.cbdcommoninterface.response.leiVo.*;
import com.cbd.cbdcommoninterface.result.CodeMsg;
import com.cbd.cbdcommoninterface.result.GlobalException;
import com.cbd.cbdcommoninterface.utils.PageUtils;
import com.cbd.cbdcommoninterface.utils.RandomInstallerUtil;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author shy_black
 * @date 2020/4/22 10:10
 * @Description:
 */
@Slf4j
@Service
public class userService implements IUserService {

    @Resource
    private com.black.lei.dao.userDao userDao;

    @Resource
    private com.black.lei.dao.roleDao roleDao;

    @Resource
    private com.black.lei.dao.role_userDao role_userDao;

    @Resource
    private com.black.lei.dao.car_infoDao car_infoDao;

    @Resource
    private user_installerDao user_installerDao;

    /**
     * 获取登录用户角色权限
     *
     * @param ID
     * @param strName
     * @return
     * @author wcj
     */
    @Override
    public String getPowerResource(String ID, String strName) {
        Map<String, String> paramMap = new HashMap<>();
        paramMap.put("ID", ID);
        paramMap.put("strName", strName);
        //获取用户权限资源
        List<Map<String, String>> powerResourceList = userDao.getPowerResource(paramMap);

        String strPowerResource = "";
        for (Map<String, String> map : powerResourceList) {

            String strPageLinker = map.get("strPageLinker");

            strPowerResource = strPowerResource + strPageLinker + ",";
        }

        return strPowerResource;
    }


    /**
     * 获取公司所有人员
     *
     * @param companyID
     * @return
     */
    @Override
    public List<user> findAllUser(String companyID) {
        List<user> list = userDao.findAllUser(companyID);
        return list;
    }


    @Override
    public Integer hasFindbyPhone(String phoneNum) {
        //查看数据库是否有该用户
        // 返回为1则有，为null则没有该用户
        return userDao.hasFindByPhone(phoneNum);
    }

    @Override
    public Integer isPasswordCorrect(String phoneNum, String password) {
        //验证，密码是否正确
        return userDao.isPasswordCorrect(phoneNum, password);
    }

    @Override
    public UserBaseInfoAndPowerInfoVo login(String phoneNum, String password) {

        //获取基本信息
        UserBaseInfoAndPowerInfoVo URV = userDao.login(phoneNum, password);

        //获取角色信息
        if (URV == null) {
            return URV;
        }
        Integer ID = URV.getID();
        //List<Integer> RolesID = role_userDao.getRolesByUserID(ID);
        List<role> RolesID = role_userDao.getRolesByUserID(ID);


        List<role> roleList = new ArrayList<>();

//        for (Integer i : RolesID) {
//            roleList.add(roleDao.getRoleInfoByRoleID(i));
//        }

        //TODO 根据角色查询权限

        URV.setRoleList(roleList);

        return URV;
    }

    //根据手机号查询员工信息


    //    @Override
    public boolean saveCreatePersonInfo(user addUserInfo) {
        boolean flag = userDao.createUser(addUserInfo);
        return flag;
    }

    /**
     * 修改员工信息
     *
     * @param updateUserVo
     * @return
     * @author wcj
     */
    @Transactional
    @Override
    public Integer updateUserInfo(UpdateUserVo updateUserVo) {
        log.info("开始执行更新SQL");
        //这里不可以直接使用userRequset
        user userInfo = new user();

        userInfo.setID(updateUserVo.getID());
        userInfo.setUserName(updateUserVo.getUserName());
        userInfo.setPhoneNum(updateUserVo.getPhoneNum());
        userInfo.setEmail(updateUserVo.getEmail());
        userInfo.setSex(updateUserVo.getSex());

        userInfo.setUserType(updateUserVo.getUserType());
        userInfo.setStatus(updateUserVo.getStatus());

//        userInfo.setCompanyID(userDao.getCompanyIDByCompanyName(updateUserVo.getCompanyName()));

//        userInfo.setPassword(updateUserVo.getPassword());

        //TODO 添加手机号重复校验
        //如果Phone不重复，向表中添加数据
        if ( userDao.findCountByPhone(updateUserVo.getPhoneNum()) > 1 ) {
            throw new GlobalException(CodeMsg.PHONENUM_DUPLICATE);
        }


        log.info("手机号不重复，开始修改员工信息");
//        log.info("userInfo新信息为：" + userInfo.toString());
        Integer res = userDao.updateUserInfo(userInfo);

        //更新role_user表中的数据，先删除，在新增
        role_userDao.deleteUserRoles(userInfo.getID());

        for(Integer i : updateUserVo.getRoleList()) {
            role_userDao.addRole_User(i,userInfo.getID());
        }


        return res;

    }

////    @Override
//    public boolean hasFindByPhone(String strPhone) {
//        return userDao.hasFindByPhone(strPhone);
//    }
//
//    /**
//     * 根据手机号查询用户
//     * @param
//     * @return
//     */
////    @Override
//    public user findByPhone(String phoneNum) {
//        return userDao.findByPhone(phoneNum);
//    }
//
//    /**
//     * 根据姓名查询用户是否存在
//     * @param userName
//     * @return
//     */
//    public boolean hasFindByUserName(String userName) {
//        return userDao.hasFindByUserName(userName);
//    }


    /**
     * 根据姓名查询用户
     *
     * @param
     * @return
     */
//    @Override
//    public user findByUserName(String userName) {
//        return userDao.findByUserName(userName);
//    }


//    @Override
//    public user findById(String strCarOwnerID) {
//        return null;
//    }
    @Override
    public PageResponse findAllUserByPage(PageRequest pageRequest) {

        int pageNum = pageRequest.getPageNum();
        int pageSize = pageRequest.getPageSize();
        log.info("pageNum = " + pageNum, "pageSize = " + pageSize);

        LftAndRgtVo lftAndRgtVo = car_infoDao.getLftAndRgt(pageRequest.getCompanyID());
        String lft = lftAndRgtVo.getLft();
        String rgt = lftAndRgtVo.getRgt();


        Page page = PageHelper.startPage(pageNum, pageSize);
        List<UserResponseVo> userList = userDao.findAllUserInfoByPage(lft, rgt);
        PageInfo<UserResponseVo> userResponseVoPageInfo = new PageInfo<>(userList);
        userResponseVoPageInfo.setPageNum(page.getPageNum());
        userResponseVoPageInfo.setPageSize(page.getPageSize());
        //TODO 根据得到的userList中修改公司名称
        //取出companyID，调用companyInfoDao，根据companyID查询父公司信息，填入公司名称

        return PageUtils.getPageResponse(userResponseVoPageInfo);
    }


    @Override
    public PageResponse findUserByPhoneNumOrByUserName(PageRequest pageRequest, String key) {
        return PageUtils.getPageResponse(getUserByPhoneOByUserName(pageRequest, key));
    }


    public PageInfo<UserResponseVo> getUserByPhoneOByUserName(PageRequest pageRequest, String key) {
        int pageNum = pageRequest.getPageNum();
        int pageSize = pageRequest.getPageSize();
        PageHelper.startPage(pageNum, pageSize);
        LftAndRgtVo lftAndRgtVo = car_infoDao.getLftAndRgt(pageRequest.getCompanyID());
        String lft = lftAndRgtVo.getLft();
        String rgt = lftAndRgtVo.getRgt();

        PageHelper.startPage(pageNum, pageSize);
        List<UserResponseVo> userList = userDao.findUserByPhoneNumOrByUserName(lft, rgt, key);
        return new PageInfo<>(userList);
    }

    @Override
    public PageResponse findCarOwer(PageRequest pageRequest, Integer userType) {
        return PageUtils.getPageResponse(getCarOwer(pageRequest, userType));
    }

    public PageInfo<UserResponseVo> getCarOwer(PageRequest pageRequest, Integer userType) {
        int pageNum = pageRequest.getPageNum();
        int pageSize = pageRequest.getPageSize();

        LftAndRgtVo lftAndRgtVo = car_infoDao.getLftAndRgt(pageRequest.getCompanyID());
        String lft = lftAndRgtVo.getLft();
        String rgt = lftAndRgtVo.getRgt();

        PageHelper.startPage(pageNum, pageSize);
        List<UserResponseVo> userList = userDao.findCarOwerByUserType(lft,rgt,userType);
        return new PageInfo<>(userList);
    }


    @Transactional
    @Override
    public Integer addUserInfo(AddUserVo addUserVo) {

        //userName,phoneNum,companyID,status,email,userType,sex ,roleList

        AddUserVo userInfo = new AddUserVo();
        userInfo.setUserName(addUserVo.getUserName());
        userInfo.setPhoneNum(addUserVo.getPhoneNum());
        //从数据库获得typeName对应的id及statusName对应的id，公司名称对应的公司id存入user中

        userInfo.setUserType(addUserVo.getUserType());
        userInfo.setStatus(addUserVo.getStatus());
        userInfo.setCompanyID(addUserVo.getCompanyID());
        userInfo.setEmail(addUserVo.getEmail());
        userInfo.setSex(addUserVo.getSex());



        //如果Phone不重复，向表中添加数据
        if (null != userDao.findByPhone(addUserVo.getPhoneNum())) {
            throw new GlobalException(CodeMsg.PHONENUM_DUPLICATE);
        }
        //log.info(userInfo.toString());

        if (userDao.insert(userInfo) == 0) {
            return 0;
        }

        log.info("添加user表成功！");


        //向数据库role_user添加角色
        int userID = userDao.findIDByPhoneNum(userInfo.getPhoneNum());
        for (Integer roleID : addUserVo.getRoleList()) {

            role_userDao.addRole_User(roleID, userID);

        }

        log.info("添加角色表成功！");

        return 1;
    }

    @Transactional
    @Override
    public Integer addInstallerInfo(AddUserVo addUserVo) {

        Integer success1 = addUserInfo(addUserVo);
        if (success1 == 0) {
            log.info("添加安装工失败");
            return success1;
        }
        //向user_installer表插数据
        Integer installer_id = userDao.findIDByPhoneNum(addUserVo.getPhoneNum());
        RandomInstallerUtil RIU = new RandomInstallerUtil();

        Integer success2 = user_installerDao.
                addInstallerInfo(installer_id,
                        RIU.getInstallerVo().getLevel(),
                        RIU.getInstallerVo().getLongitude(),
                        RIU.getInstallerVo().getLatitude());

        return success2;
    }


//    public List<user> getUserListByUserType(int type) {return null;}
}


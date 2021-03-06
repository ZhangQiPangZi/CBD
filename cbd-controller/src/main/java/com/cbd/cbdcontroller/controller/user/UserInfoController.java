package com.cbd.cbdcontroller.controller.user;


import com.cbd.cbdcommoninterface.cbd_interface.user.ICompanyInfoService;
import com.cbd.cbdcommoninterface.cbd_interface.user.IRoleDefineService;
import com.cbd.cbdcommoninterface.cbd_interface.user.IUserService;
import com.cbd.cbdcommoninterface.response.PageResponse;
import com.cbd.cbdcommoninterface.response.leiVo.*;
import com.cbd.cbdcommoninterface.result.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.List;

/**
 * @author shy_black
 * @date 2020/4/22 9:25
 * @Description: 功能：
 * 1.展示人员详情列表-------------  √
 * 2.根据人员姓名/电话查询员工----  √
 * 3.添加员工--------------------  √
 * 4.修改员工信息----------------  待改进
 * 6.查询公司下属车主信息---------  √
 */
@Slf4j
@Api("人员管理")
@RestController
@RequestMapping("/user")
public class UserInfoController {

    @Autowired
    private IUserService userService;

    @Autowired
    private IRoleDefineService roleDefineService;

    @Autowired
    private ICompanyInfoService companyInfoService;

    /**
     * 人员管理页面---角色准入：管理人员
     * 展示人员详情列表-------------Y
     * <p>
     * 功能详情：1。当用户登录后，（管理员以上权限）跳转到人员管理页面
     * 2。展示该用户所属公司的所有员工及公司下属车主
     *
     * @param
     * @return
     */
    @ApiOperation(value = "人员管理主页-分页获取", httpMethod = "POST")
    @RequestMapping(value = "/userInfo", method = RequestMethod.POST)
    public Result<PageResponse> showUserList(@RequestBody LPageRequest LPageRequest) {

        log.info("请求参数为：" + LPageRequest.toString());

        PageResponse userList = userService.findAllUserByPage(LPageRequest);

        log.info("返回参数为：" + userList.toString());
        return Result.success(userList);
    }

    /**
     * 添加员工------角色准入：管理人员
     *
     * @param addUserVo
     * @return
     */
    @ApiOperation(value = "新增员工信息", httpMethod = "POST")
    @RequestMapping(value = "/userInfo/addUser", method = RequestMethod.POST)

    public Result<String> addUser(HttpServletRequest req,
                                  @RequestBody AddUserVo addUserVo) throws ServletException, IOException {

        req.setCharacterEncoding("utf-8");//必须写在第一位，因为采用这种方式去读取数据，否则数据会出错。
//设置这样方式去读。这样中文就能够读取出来了，但是需要注意。表单的发送方式必须是method='post'

        //非安装工程师
        // userType==3时,即为安装工程师
        if (addUserVo.getUserType() != 3) {
            log.info("开始录入员工----非安装工程师信息");
            Integer success = userService.addUserInfo(addUserVo);
            if (success == 1) {
                return Result.success(addUserVo.getUserName() + "录入成功");
            } else {
                return Result.success(addUserVo.getUserName() + "信息录入失败，请重试");
            }
        } else {
            //安装工程师
            log.info("录入安装工信息");
            Integer success = userService.addInstallerInfo(addUserVo);
            if (success == 1) {
                return Result.success(addUserVo.getUserName() + "录入成功");
            } else {
                return Result.success(addUserVo.getUserName() + "信息录入失败，请重试");
            }
        }

    }


    //这里update语句在navicat上跑没问题，但在mybatis上跑就没反应
    //使用JSONObject接受json没有问题，但是用userrequest接受，id为null
    //解决方法，在UserRequest的ID属性上加jsonProperty注解，指定属性名
    @ApiOperation(value = "根据ID修改员工信息", httpMethod = "POST")
    @RequestMapping(value = "/userInfo/updateUser", method = RequestMethod.POST)
    public Result<String> updateUser(@RequestBody UpdateUserVo updateUserVo) {
        log.info("1");
        //{"ID":1007,"userName":"武磊","phoneNum":"15909118817","password":"1234567",
        // "companyID":"22","status":"1","userType":"0","remark":"null"}
        log.info("userrequest信息为" + updateUserVo.toString());
        Integer success = userService.updateUserInfo(updateUserVo);
        log.info("执行更新SQL结束,success的值为" + success);
        if (success == 1) {
            return Result.success("修改员工信息成功");
        } else {
            log.info("修改员工信息失败，请稍后重试");
            return Result.success("修改员工信息失败，请稍后重试");
        }

    }

    @ApiOperation(value = "根据手机或姓名查找员工,请求方法为POST，" +
            "但是需要在网址的url后边加上key，像GET那样", httpMethod = "POST")
    @RequestMapping(value = "/findUser", method = RequestMethod.POST)
    public Result<PageResponse> findUser(@RequestBody LPageRequest LPageRequest,
                                         @RequestParam String key) {
        return Result.success(userService.findUserByPhoneNumOrByUserName(LPageRequest, key));

    }

    @ApiOperation(value = "根据角色类型查找人员"
            , httpMethod = "POST")
    @RequestMapping(value = "/findUserByUserType", method = RequestMethod.POST)
    public Result<PageResponse> findCarOwer(@RequestBody LPageRequest LPageRequest,
                                            @RequestParam Integer userType) {
        //0为车主
        return Result.success(userService.findCarOwer(LPageRequest, userType));
    }

    @ApiOperation(value = "返回数据库所有角色id-role", httpMethod = "POST")
    @RequestMapping(value = "/getRoleDefine", method = RequestMethod.POST)
    public Result<List<RoleResponseVo>> findrole() {
        return Result.success(roleDefineService.getRoleDefines());
    }


//    //安装工信息查询
//    @RequestMapping(value = "/findInstallerInfo" ,method = RequestMethod.POST)
//    public Result<InstallerVo> findInstallerInfo(@RequestParam String phoneNum) {
//
//        InstallerVo installerVo = userService.findInstallerInfoByPhoneNum(phoneNum);
//
//        if (installerVo == null) {
//            return Result.error(CodeMsg.SERVER_ERROR);
//        }
//
//        return Result.success(installerVo);
//
//    }


}
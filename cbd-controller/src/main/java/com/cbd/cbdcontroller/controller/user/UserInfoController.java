package com.cbd.cbdcontroller.controller.user;


import com.cbd.cbdcommoninterface.cbd_interface.user.ICompanyInfoService;
import com.cbd.cbdcommoninterface.cbd_interface.user.IUserService;
import com.cbd.cbdcommoninterface.response.PageResponse;
import com.cbd.cbdcommoninterface.response.leiVo.AddUserVo;
import com.cbd.cbdcommoninterface.response.leiVo.PageRequest;
import com.cbd.cbdcommoninterface.response.leiVo.PageResult;
import com.cbd.cbdcommoninterface.response.leiVo.UpdateUserVo;
import com.cbd.cbdcommoninterface.result.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
    @ApiOperation(value = "人员管理主页-分页获取",httpMethod = "POST")
    @RequestMapping(value = "/userInfo", method = RequestMethod.POST)
    public Result<PageResponse> showUserList(@RequestBody PageRequest pageRequest) {
        PageResponse userList = userService.findAllUserByPage(pageRequest);
        return Result.success(userList);
    }
    /**
     * 添加员工------角色准入：管理人员
     *
     * @param addUserVo
     * @return
     */
    @ApiOperation(value = "新增员工信息",httpMethod = "POST")
    @RequestMapping(value = "/userInfo/addUser", method = RequestMethod.POST)

    public Result<String> addUser(@RequestBody AddUserVo addUserVo) {

        Integer success = userService.addUserInfo(addUserVo);
        if (success == 1) {
            return Result.success(addUserVo.getUserName() + "录入成功");
        } else {
            return Result.success(addUserVo.getUserName() + "信息录入失败，请重试");
        }
    }



    //这里update语句在navicat上跑没问题，但在mybatis上跑就没反应
    //使用JSONObject接受json没有问题，但是用userrequest接受，id为null
    //解决方法，在UserRequest的ID属性上加jsonProperty注解，指定属性名
    @ApiOperation(value = "根据ID修改员工信息",httpMethod = "POST")
    @RequestMapping(value = "/userInfo/updateUser",method = RequestMethod.POST)
    public  Result<String> updateUser(@RequestBody UpdateUserVo updateUserVo) {
        log.info("1");
        //{"ID":1007,"userName":"武磊","phoneNum":"15909118817","password":"1234567",
        // "companyID":"22","status":"1","userType":"0","remark":"null"}
        log.info("userrequest信息为"+updateUserVo.toString());
        Integer success = userService.updateUserInfo(updateUserVo);
        log.info("执行更新SQL结束,success的值为"+success);
        if(success == 1) {
            return Result.success("修改员工信息成功");
        }else {
            log.info("修改员工信息失败，请稍后重试");
            return Result.success("修改员工信息失败，请稍后重试");
        }

    }
    @ApiOperation(value = "根据手机或姓名查找员工,请求方法为POST，" +
            "但是需要在网址的url后边加上key，像GET那样",httpMethod = "POST")
    @RequestMapping(value = "/findUser",method = RequestMethod.POST)
    public Result<PageResponse> findUser(@RequestBody PageRequest pageRequest,
                                       @RequestParam String key) {
        return Result.success(userService.findUserByPhoneNumOrByUserName(pageRequest,key));

    }
    @ApiOperation(value = "查找当前管理员所在公司下的下属车主（一个按钮，点了以后人员列表就只显示车主）"
                    ,httpMethod = "POST")
    @RequestMapping(value = "/findCarOwer",method = RequestMethod.POST)
    public Result<PageResponse> findCarOwer(@RequestBody PageRequest pageRequest,
                                          @RequestParam Integer userType) {
        //0为车主
        return Result.success(userService.findCarOwer(pageRequest,userType));
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
package com.cbd.cbdcontroller.controller.user;


import com.cbd.cbdcommoninterface.cbd_interface.user.ICompanyInfoService;
import com.cbd.cbdcommoninterface.cbd_interface.user.ILoginService;
import com.cbd.cbdcommoninterface.cbd_interface.user.IUserService;
import com.cbd.cbdcommoninterface.response.leiVo.InstallerVo;
import com.cbd.cbdcommoninterface.response.leiVo.LoginVo;
import com.cbd.cbdcommoninterface.response.leiVo.UserBaseInfoAndPowerInfoVo;
import com.cbd.cbdcommoninterface.result.CodeMsg;
import com.cbd.cbdcommoninterface.result.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

//import com.black.lei.service.Impl.MyUserDetailService;

/**
 * 自测结果：可以登录查询userInfo，companyID只能找到用户自己的公司id
 * 待改进：增加companyService功能：查找当前公司的所有父级公司
 */

@Api("用户登录接口")
@RestController
@Slf4j
@RequestMapping("/cbd")
public class WEBLoginController {

    @Autowired
    private IUserService userService;

//    @Autowired
//    private MyUserDetailService myUserDetailService;

    @Autowired
    private ICompanyInfoService companyInfoService;

    @Autowired
    private ILoginService loginService;


    @ApiOperation(value="安装工专属登录",httpMethod = "POST")
    @RequestMapping("/installerlogin")
    public Result<InstallerVo> login(@RequestBody LoginVo loginVo) {

        InstallerVo installer = new InstallerVo();
        log.info("开始身份验证");

        if(userService.hasFindbyPhone(loginVo.getPhoneNum()) == null)  {
            return Result.error(CodeMsg.PHONENUM_MISS_ERROR);
        }
        if(userService.isPasswordCorrect(loginVo.getPhoneNum(),loginVo.getPassword()) == null) {
            return Result.error(CodeMsg.PASSWORD_ERROR);
        }

        //获取用户基本信息
        installer = loginService.installerLogin(loginVo);

        //获取用户的角色信息及权限信息
        if(installer == null) {
            log.info("获取用户信息失败");
            return Result.error(CodeMsg.PHONENUM_PASSWORD_INCORRECT_ERROR);
        }




        log.info("获取用户信息成功");

        return Result.success(installer);
    }

    /**
     * 自测结果：可以登录查询userInfo，companyID只能找到用户自己的公司id
     * @param loginVo
     * @return
     */
    @ApiOperation(value = "用户登录验证",httpMethod = "POST")
    @RequestMapping("/login")
    public Result<UserBaseInfoAndPowerInfoVo> login(HttpServletRequest req,
                                                    @RequestBody LoginVo loginVo) {
        log.info("开始身份验证");

        if(userService.hasFindbyPhone(loginVo.getPhoneNum()) == null)  {
            return Result.error(CodeMsg.PHONENUM_MISS_ERROR);
        }
        if(userService.isPasswordCorrect(loginVo.getPhoneNum(),loginVo.getPassword()) == null) {
            return Result.error(CodeMsg.PASSWORD_ERROR);
        }
        //获取用户基本信息
        UserBaseInfoAndPowerInfoVo URV = userService.login(loginVo.getPhoneNum(),loginVo.getPassword());

        //获取用户的角色信息及权限信息
        if(URV == null) {
            log.info("获取用户信息失败");
            return Result.error(CodeMsg.PHONENUM_PASSWORD_INCORRECT_ERROR);
        }

        HttpSession session = req.getSession();

        session.setAttribute("CurrentUserInfo",URV);

        log.info("获取用户信息成功");
        return Result.success(URV);


//        user userInfo = loginService.checkLogin(loginVo);

        //获取当前用户权限资源
        //String strPowerResource = userService.getPowerResource(userInfo.getID().toString(),userInfo.getUserName());

        //获取当前公司信息
//        company_info companyInfo = companyInfoService.findById(userInfo.getCompanyID());
        //获取当前公司上级公司
        //String parentCompany = companyInfoService.getParentCompanyByCompanyID(userInfo.getCompanyID());

        //获取公司类型
//        int companyType = companyInfo.getCompanyType();

        //返回数据前台
        //jsonObject.put("strPowerResource", strPowerResource);//资源权限
//        jsonObject.put("userInfo", userInfo);//用户信息
//
//        jsonObject.put("companyType", companyType);//公司类型 0:平台总公司   1:4S店和维修店   2:担保公司  3:企业车辆管理
//        //jsonObject.put("affiliatedCompany", parentCompany);//所属公司  0:admin 1:宇盟总平台   2:车百度总平台
//        jsonObject.put("companyName", companyInfo.getCompanyName());//公司名称
//        jsonObject.put("success", success);
//        jsonObject.put("msg", msg);
//
//        //将信息保存到session中
//        session.setAttribute("userInfo", userInfo);//将用户信息放入session中在webpage中使用
//        session.setAttribute("companyType", companyType);
//        session.setAttribute("companyName", companyInfo.getCompanyName());
//        session.setAttribute("success", success);
//        session.setAttribute("msg", msg);
//        session.setMaxInactiveInterval(24 * 60 * 60);
//
//        return jsonObject;
//        return Result.success(userInfo);

    }
    //获取上级公司id

//    private String getParentCompany(String strCompanyID) {
//        List<String> parentCompanyID = companyInfoService.getParentCompanyByCompanyID(strCompanyID);
//        if (parentCompanyID.isEmpty() || parentCompanyID.size() == 0 || parentCompanyID.get(0).isEmpty()) {
//            return "3";
//        }
//        if (parentCompanyID.get(0).equals("c5bbb69ad203419c8603c41cec7ac600")
//                || strCompanyID.equals("c5bbb69ad203419c8603c41cec7ac600")) {
//            return "2";
//        } else if (parentCompanyID.get(0).equals("24100477e79d4b749a595f69bff249c3")
//                || strCompanyID.equals("24100477e79d4b749a595f69bff249c3")) {
//            return "1";
//        } else {
//            return getParentCompany(parentCompanyID.get(0));
//        }
//    }

//    /**
//     * 修改密码
//     * @param request
//     * @return
//     */
//    @RequestMapping(value="/ModifyPassword")
//    @ResponseBody
//    public JSONObject ModifyPassword(HttpServletRequest request){
//        JSONObject json = new JSONObject();
//        boolean success = false;
//        HttpSession session = request.getSession();
//        user userInfo = (user) session.getAttribute("userInfo");
//        String password = userInfo.getPassword();
//        String OldPassword = request.getParameter("RootOldPassword");
//        String NewPassword = request.getParameter("RootNewPassword");
//        if(password.equals(OldPassword)){
//            userInfo.setPassword(NewPassword);
//            success = userService.update(userInfo);
//        }
//        json.put("success", success);
//        return json;
//    }


}

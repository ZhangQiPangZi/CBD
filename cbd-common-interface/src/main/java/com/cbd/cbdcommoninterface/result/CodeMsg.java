package com.cbd.cbdcommoninterface.result;

import com.cbd.cbdcommoninterface.utils.ExpPrefixUtil;

import java.io.Serializable;

public class CodeMsg implements Serializable {

    private String code;
    private String msg;

    /** 通用异常 **/
    public static final CodeMsg UNKNOW_ERROR = new CodeMsg(ExpPrefixUtil.ComExpPrefix+"00", "未知错误");
    public static final CodeMsg ERROR_404 = new CodeMsg(ExpPrefixUtil.ComExpPrefix+"01", "没有该接口");
    public static final CodeMsg SERVER_ERROR = new CodeMsg(ExpPrefixUtil.ComExpPrefix+"02", "服务端异常");
    public static final CodeMsg NO_PERMISSION = new CodeMsg(ExpPrefixUtil.ComExpPrefix+"03", "无权限访问");
    public static final CodeMsg UNLOGIN = new CodeMsg(ExpPrefixUtil.ComExpPrefix+"04", "未登录");

    /** 设备异常 **/
    public static final CodeMsg OUT_OF_STOCK = new CodeMsg(ExpPrefixUtil.DevExpPrefix+"00", "库存不足");


    /**用户使用异常**/

    /** 公司异常 **/
    public static final CodeMsg CPY_LEVEL_ERROR = new CodeMsg(ExpPrefixUtil.CpyExpPrefix+"00", "公司级别错误");


    public static final CodeMsg STAFF_NAME_ERROR = new CodeMsg("1012", "用户名错误！");
    public static final CodeMsg PASSWORD_ERROR = new CodeMsg("1013", "密码错误！");
    public static final CodeMsg PHONENUM_DUPLICATE = new CodeMsg("1014", "手机号码不能重复！");
    public static final CodeMsg ROLE_MSG_GET_ERROR = new CodeMsg("1015", "获取全局角色信息失败，请联系维护人员！");
    public static final CodeMsg INPUT_ERROR = new CodeMsg("1016", "JSON输入格式不匹配！");
    public static final CodeMsg NULL_TRACK_MSG_ERROR = new CodeMsg("1017", "该设备该时间段轨迹信息为空！");
    public static final CodeMsg PHONENUM_PASSWORD_INCORRECT_ERROR = new CodeMsg("1018","用户名与密码不匹配！");
    public static final CodeMsg USER_NOT_EXIT_ERROR = new CodeMsg("1019","用户不存在！");
    public static final CodeMsg PAY_ERROR = new CodeMsg("1020","支付失败！");
    public static final CodeMsg SIGNVERIFIED_ERROR = new CodeMsg("1020","验证商户失败！");
    public static final CodeMsg AUTH_REEOR = new CodeMsg("1021","权限不足！");
    public static final CodeMsg DIDNT_LOGIN_ERROR = new CodeMsg("1022","用户未登录！");
    public static final CodeMsg SESSION_OUT_OF_TIME_ERROR = new CodeMsg("1023","用户会话过期！");

    public static final CodeMsg EMPTY_DEVID_ERROR = new CodeMsg("1024","设备未找到！");
    public static final CodeMsg EMPTY_COMPANY_ERROR = new CodeMsg("1025","未找到该公司！");
    public static final CodeMsg EMPTY_CAR_ERROR = new CodeMsg("1026","该公司下未找到车辆！");




    /**用户及定位异常**/
//    public static final CodeMsg SERVER_ERROR = new CodeMsg(1001, "服务端异常,请刷新页面");
    public static final CodeMsg OUT_OF_SIM_STOCK = new CodeMsg(ExpPrefixUtil.DevExpPrefix+"01", "SIM卡库存不足");

    private CodeMsg(String code, String msg) {

        this.code = code;
        this.msg = msg;
    }

    public String getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }

}

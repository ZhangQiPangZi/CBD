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

    public static final CodeMsg STAFF_NAME_ERROR = new CodeMsg("1012", "用户名错误！");
    public static final CodeMsg PASSWORD_ERROR = new CodeMsg("1013", "密码错误！");
    public static final CodeMsg PHONENUM_DUPLICATE = new CodeMsg("1014", "手机号码不能重复！");
    public static final CodeMsg ROLE_MSG_GET_ERROR = new CodeMsg("1015", "获取全局角色信息失败，请联系维护人员！");
    public static final CodeMsg INPUT_ERROR = new CodeMsg("1016", "JSON输入格式不匹配！");
    public static final CodeMsg NULL_TRACK_MSG_ERROR = new CodeMsg("1017", "该设备该时间段轨迹信息为空！");
    public static final CodeMsg PHONENUM_PASSWORD_INCORRECT_ERROR = new CodeMsg("1018","用户名与密码不匹配！");
    public static final CodeMsg PHONENUM_MISS_ERROR = new CodeMsg("1019","未找到该用户！");
    public static final CodeMsg PAY_ERROR = new CodeMsg("1020","支付失败！");
    public static final CodeMsg SIGNVERIFIED_ERROR = new CodeMsg("1020","验证商户失败！");







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

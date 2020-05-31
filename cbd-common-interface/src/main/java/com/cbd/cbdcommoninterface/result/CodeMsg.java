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

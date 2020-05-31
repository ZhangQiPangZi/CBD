package com.cbd.cbdcommoninterface.result;

import java.io.Serializable;

public class Result<T> implements Serializable {
    private String code;
    private String msg;
    private T data;

//    用户外部不能实例化Result，只能获取成功或失败
    private Result(T data){
        this.code = "0";
        this.msg = "success";
        this.data = data;
    }

    private Result(CodeMsg codeMsg){
        if (codeMsg == null) {
            return;
        }
        this.code = codeMsg.getCode();
        this.msg = codeMsg.getMsg();
    }

    private Result(CodeMsg codeMsg, Exception e){
        if (codeMsg == null) {
            return;
        }
        this.code = codeMsg.getCode();
        this.msg = e.toString();
    }

//    成功时候的调用,只需传入数据即可
    public static <T>Result<T> success(T data){
        return new Result(data);
    }

//    失败时候的调用，只需传入对应的异常
    public static <T>Result<T> error(CodeMsg codeMsg){
        return new Result(codeMsg);
    }

    public static <T>Result<T> error(CodeMsg codeMsg, Exception e){
        return new Result(codeMsg,e);
    }
    public String getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }

    public T getData() {
        return data;
    }
}

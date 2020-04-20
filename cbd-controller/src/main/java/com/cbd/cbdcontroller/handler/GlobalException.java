package com.cbd.cbdcontroller.handler;


import com.cbd.cbdcontroller.result.CodeMsg;

public class GlobalException extends RuntimeException{
    private CodeMsg codeMsg;

    public GlobalException(CodeMsg codeMsg) {
        this.codeMsg = codeMsg;
    }

    public CodeMsg getCodeMsg() {
        return codeMsg;
    }
}

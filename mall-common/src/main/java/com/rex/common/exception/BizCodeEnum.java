package com.rex.common.exception;

public enum BizCodeEnum {
    UNKNOWN_EXCEPTION(10000,"System Error"),
    VALID_EXCEPTION(10001,"Parameter Validation Error"),
    PRODUCT_UP_EXCEPTION(11000,"商品上架异常");

    private final int code;
    private final String msg;

    BizCodeEnum(int code, String msg){
        this.code = code;
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }
}

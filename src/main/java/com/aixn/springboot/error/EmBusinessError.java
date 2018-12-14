package com.aixn.springboot.error;

/**
 * @author zxx
 * @date 2018/12/13 11:41
 */
public enum EmBusinessError implements CommonError {

    //通用错误类型00001
    PARAMETER_VALIDATION_ERROR(00001,"参数错误"),
    //未知错误
    UNKNOWN_ERROR(00002,"未知错误"),

    USER_LOGIN_FAIL(00003,"用户手机号或密码不正确"),

    //10000开头为用户相关错误定义
    USER_NOT_EXIST(10001,"用户不存在"),
    USER_NOT_LOGIN(10002,"用户未登录"),

    //20000为商品信息错误
    ITEM_NOT_EXIT(20001,"商品不存在"),

    //30000为交易信息错误
    STOCK_NOT_ENOUGH(30001,"商品数量不足");

    private int errCode;
    private String errMsg;


     EmBusinessError(int errCode,String errMsg){
        this.errCode=errCode;
        this.errMsg=errMsg;
    }

    @Override
    public int getErrCode() {
        return this.errCode;
    }

    @Override
    public String getErrMsg() {
        return this.errMsg;
    }

    @Override
    public CommonError setErrMsg(String errMsg) {
        this.errMsg=errMsg;
        return this;
    }
}

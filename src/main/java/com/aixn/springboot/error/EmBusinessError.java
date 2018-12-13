package com.aixn.springboot.error;

/**
 * @author zxx
 * @date 2018/12/13 11:41
 */
public enum EmBusinessError implements CommonError {

    //通用错误类型00001
    PARAMETER_VALIDATION_ERROR(00001,"参数错误"),

    //10000开头为用户相关错误定义
    USER_NOT_EXIST(10001,"用户不存在"),

    //未知错误
    UNKNOWN_ERROR(00002,"未知错误"),

    USER_LOGIN_FAIL(00003,"用户手机号或密码不正确");

    private int errCode;
    private String errMsg;


    private EmBusinessError(int errCode,String errMsg){
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

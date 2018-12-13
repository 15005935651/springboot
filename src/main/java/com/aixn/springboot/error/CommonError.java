package com.aixn.springboot.error;

/**
 * @author zxx
 * @date 2018/12/13 11:37
 */
public interface CommonError {
    public int getErrCode();
    public String getErrMsg();
    public CommonError setErrMsg(String errMsg);
}

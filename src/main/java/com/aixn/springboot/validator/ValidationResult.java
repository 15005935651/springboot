package com.aixn.springboot.validator;

import org.apache.commons.lang3.StringUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * @author zxx
 * @date 2018/12/15 20:30
 */
public class ValidationResult {
    //检验结果是否有错
    private boolean hasErrors = false;
    //存放错误的map
    private Map<String, String> errMsgMap = new HashMap<>();

    public boolean isHasErrors() {
        return hasErrors;
    }

    public void setHasErrors(boolean hasErrors) {
        this.hasErrors = hasErrors;
    }

    public Map<String, String> getErrMsgMap() {
        return errMsgMap;
    }

    public void setErrMsgMap(Map<String, String> errMsgMap) {
        this.errMsgMap = errMsgMap;
    }

    //通过格式化字符串信息获取错误结果的错误方法
    public String getErrMsg() {
        return StringUtils.join(errMsgMap.values().toArray(), ",");
    }
}

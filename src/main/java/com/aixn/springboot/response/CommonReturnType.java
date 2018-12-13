package com.aixn.springboot.response;

/**
 * @author zxx
 * @date 2018/12/13 10:41
 */
public class CommonReturnType {
    //返回状态，success或者fail;
    private String status;
    //若status==success,则data返回给前端json
    //若status==fail,则data内使用通用的错误码格式
    private Object data;


    //定义一个通用的创建方法
    public static CommonReturnType create(Object result){
        return CommonReturnType.create(result,"success");
    }

    public static CommonReturnType create(Object result, String success) {
        CommonReturnType commonReturnType = new CommonReturnType();
        commonReturnType.setStatus(success);
        commonReturnType.setData(result);
        return commonReturnType;
    }


    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }





}

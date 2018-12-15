package com.aixn.springboot.intercept;

import com.aixn.springboot.error.BusinessException;
import com.aixn.springboot.error.EmBusinessError;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


/**
 * @author zxx
 * @date 2018/12/15 22:52
 */
public class SysInterceptor extends HandlerInterceptorAdapter {

    @Override
    public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o) throws Exception {

        Boolean isLogin = (Boolean)httpServletRequest.getSession().getAttribute("IS_LOGIN");

        if(isLogin==null||!isLogin){
            throw new BusinessException(EmBusinessError.USER_NOT_LOGIN);
        }


        return true;
    }

    //    试图渲染之后执行
    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {

    }

    //    在请求处理之后,视图渲染之前执行
    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {

    }

}

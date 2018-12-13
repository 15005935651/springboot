package com.aixn.springboot.exception;


import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author zxx
 * @date 2018/12/12 16:43
 */

@ControllerAdvice
public class ExceptionHandler {
    private static final String ERROR_VIEW = "thymeleaf/error";

    /**
     * 捕获全局异常
     * @param httpServletRequest 请求
     * @param e 异常
     * @return 异常处理
     */
    @org.springframework.web.bind.annotation.ExceptionHandler(value = Exception.class)
    public Object errorHandle(HttpServletRequest httpServletRequest
            , Exception e) throws Exception{
        e.printStackTrace();
        ModelAndView mav = new ModelAndView();
        mav.addObject("Exception",e);
        mav.addObject("url",httpServletRequest.getRequestURI());
        mav.setViewName(ERROR_VIEW);
        return mav;
    }

}

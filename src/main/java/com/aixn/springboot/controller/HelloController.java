package com.aixn.springboot.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author zxx
 * @date 2018/12/12 21:35
 */
@Controller
public class HelloController extends BaseController {


    @RequestMapping("/hello")
    public String sayHello() {
        return "/thymeleaf/getotp.html";

    }

}

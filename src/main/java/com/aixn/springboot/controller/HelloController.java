package com.aixn.springboot.controller;

import com.aixn.springboot.dao.UserDOMapper;
import com.aixn.springboot.dataobject.UserDO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author zxx
 * @date 2018/12/12 21:35
 */
@RestController
public class HelloController {

    @Autowired
    private UserDOMapper userDOMapper;



    @RequestMapping("/hello")
    private String sayHello() {
        UserDO userDO = userDOMapper.selectByPrimaryKey(1);
        if(userDO == null){
            return "用户不存在";
        }else {
            return userDO.getName();
        }

    }

}

package com.aixn.springboot.service;

import com.aixn.springboot.service.model.UserModel;

/**
 * @author zxx
 * @date 2018/12/12 22:32
 */
public interface UserService {
    //通过用户id获取用户对象
    UserModel getUserById(Integer id);
}

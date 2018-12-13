package com.aixn.springboot.service;

import com.aixn.springboot.error.BusinessException;
import com.aixn.springboot.service.model.UserModel;

/**
 * @author zxx
 * @date 2018/12/12 22:32
 */
public interface UserService {
    //通过用户id获取用户对象
    UserModel getUserById(Integer id);

    //用户注册
    void register(UserModel userModel) throws BusinessException;


    /**
     * 用户登录
     * @param telephone 用户注册手机号
     * @param encryptPassword 用户加密后的密码
     * @throws BusinessException
     */
    UserModel validateLogin(String telephone, String encryptPassword) throws BusinessException;
}

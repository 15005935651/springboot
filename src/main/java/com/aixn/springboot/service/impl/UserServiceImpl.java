package com.aixn.springboot.service.impl;

import com.aixn.springboot.dao.UserDOMapper;
import com.aixn.springboot.dao.UserPasswordDOMapper;
import com.aixn.springboot.dataobject.UserDO;
import com.aixn.springboot.dataobject.UserPasswordDO;
import com.aixn.springboot.error.BusinessException;
import com.aixn.springboot.error.EmBusinessError;
import com.aixn.springboot.service.UserService;
import com.aixn.springboot.service.model.UserModel;
import com.aixn.springboot.validator.ValidationResult;
import com.aixn.springboot.validator.ValidatorImpl;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author zxx
 * @date 2018/12/12 22:32
 */
@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserDOMapper userDOMapper;
    @Autowired
    private UserPasswordDOMapper userPasswordDOMapper;

    @Autowired
    private ValidatorImpl validator;

    @Override
    public UserModel getUserById(Integer id) {
        //通过userDOMapper获取对于dataObject
        UserDO userDO = userDOMapper.selectByPrimaryKey(id);

        if (userDO == null) {
            return null;
        }
        //通过用户id获取用户加密密码
        UserPasswordDO userPasswordDO = userPasswordDOMapper.selectByUserId(userDO.getId());

        return convertFromDataObject(userDO, userPasswordDO);
    }

    /**
     * 用户注册
     *
     * @param userModel 用户模型
     */
    @Override
    @Transactional
    public void register(UserModel userModel) throws BusinessException {
        if (userModel == null) {
            throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR);
        }
//        if (StringUtils.isEmpty(userModel.getName()) || userModel.getAge() == null
//                || userModel.getGender() == null || StringUtils.isEmpty(userModel.getTelephone())) {
//            throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR);
//        }
        //入参校验
        ValidationResult result = validator.validate(userModel);
        if(result.isHasErrors()){
            throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR,result.getErrMsg());
        }
        //实现object->model
        UserDO userDO = convertFromModel(userModel);
        try {
            userDOMapper.insertSelective(userDO);
        } catch (DuplicateKeyException e) {
            throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR, "该手机已注册");
        }

        UserPasswordDO userPasswordDO = convertPasswordFromModel(userModel);
        userPasswordDOMapper.insertSelective(userPasswordDO);
    }

    /**
     * 用户登录
     *
     * @param telephone       手机号
     * @param encryptPassword 密码
     */
    @Override
    @Transactional
    public UserModel validateLogin(String telephone, String encryptPassword) throws BusinessException {
        //通过用户手机获取用户信息
        UserDO userDO = userDOMapper.selectByTelephone(telephone);
        System.out.println(userDO.getName());
        if (userDO == null) {
            throw new BusinessException(EmBusinessError.USER_LOGIN_FAIL);
        }

        UserPasswordDO userPasswordDO = userPasswordDOMapper.selectByUserId(userDO.getId());



        UserModel userModel = convertFromDataObject(userDO, userPasswordDO);
        //检验密码
        if (!StringUtils.equals(encryptPassword, userModel.getEncrptPassword())) {
            throw new BusinessException(EmBusinessError.USER_LOGIN_FAIL);
        }
        return userModel;
    }

    /**
     * 转换为模型数据
     *
     * @param userDO         用户POJO
     * @param userPasswordDO 用户密码POJO
     * @return
     */
    private UserModel convertFromDataObject(UserDO userDO, UserPasswordDO userPasswordDO) {
        if (userDO == null) {
            return null;
        }
        UserModel userModel = new UserModel();
        BeanUtils.copyProperties(userDO, userModel);
        if (userPasswordDO != null) {
            userModel.setEncrptPassword(userPasswordDO.getEncrptPassword());
        }
        return userModel;
    }

    /**
     * 用户模型转为用户object
     *
     * @param userModel
     * @return
     */
    private UserDO convertFromModel(UserModel userModel) {
        if (userModel == null) {
            return null;
        }
        UserDO userDO = new UserDO();
        BeanUtils.copyProperties(userModel, userDO);
        return userDO;
    }

    /**
     * 用户模型转为用户密码object
     *
     * @param userModel
     * @return
     */
    private UserPasswordDO convertPasswordFromModel(UserModel userModel) {
        if (userModel == null) {
            return null;
        }
        UserPasswordDO userPasswordDO = new UserPasswordDO();
        BeanUtils.copyProperties(userModel, userPasswordDO);
        userPasswordDO.setId(userModel.getId());
        return userPasswordDO;

    }

}

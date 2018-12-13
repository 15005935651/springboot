package com.aixn.springboot.service.impl;

import com.aixn.springboot.dao.UserDOMapper;
import com.aixn.springboot.dao.UserPasswordDOMapper;
import com.aixn.springboot.dataobject.UserDO;
import com.aixn.springboot.dataobject.UserPasswordDO;
import com.aixn.springboot.service.UserService;
import com.aixn.springboot.service.model.UserModel;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
    @Override
    public UserModel getUserById(Integer id) {
        //通过userDOMapper获取对于dataObject
        UserDO userDO = userDOMapper.selectByPrimaryKey(id);

        if(userDO == null){
            return null;
        }
        //通过用户id获取用户加密密码
        UserPasswordDO userPasswordDO = userPasswordDOMapper.selectByUserId(userDO.getId());

        return convertFromDataObject(userDO,userPasswordDO);
    }

    /**
     * 转换为模型数据
     * @param userDO 用户POJO
     * @param userPasswordDO 用户密码POJO
     * @return
     */
    private UserModel convertFromDataObject(UserDO userDO, UserPasswordDO userPasswordDO){
        if(userDO == null){
            return null;
        }
        UserModel userModel = new UserModel();
        BeanUtils.copyProperties(userDO,userModel);
        if(userPasswordDO != null){
            userModel.setEncrptPassword(userPasswordDO.getEncrptPassword());
        }
        return userModel;
    }

}

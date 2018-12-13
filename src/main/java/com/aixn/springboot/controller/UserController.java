package com.aixn.springboot.controller;

import com.aixn.springboot.controller.viewobject.UserVO;
import com.aixn.springboot.error.BusinessException;
import com.aixn.springboot.error.EmBusinessError;
import com.aixn.springboot.response.CommonReturnType;
import com.aixn.springboot.service.UserService;
import com.aixn.springboot.service.model.UserModel;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.Random;

/**
 * @author zxx
 * @date 2018/12/12 22:28
 */
@Controller("user")
@RequestMapping("/user")
public class UserController extends BaseController{

    @Autowired
    private UserService userService;

    @Autowired
    private HttpServletRequest httpServletRequest;

    @RequestMapping("/get")
    @ResponseBody
    public CommonReturnType getUser(@RequestParam(name="id")Integer id) throws BusinessException {
        //调用service服务获取用户信息并返回给前端
        UserModel userModel = userService.getUserById(id);

        if(userModel==null){
            throw new BusinessException(EmBusinessError.USER_NOT_EXIST);
        }
        //核心领域的模型用户对象转换为可控UI使用的viewobject
        UserVO userVO = convertFromModel(userModel);

        return CommonReturnType.create(userVO);
    }

    //用户获取OTP短信接口
    @RequestMapping("/getOTP")
    @ResponseBody
    public CommonReturnType getOTP(@RequestParam(name="telephone")String telephone){
        //根据一定规则生成OTP验证码
        Random random = new Random();
        int randomInt = random.nextInt(99999);
        randomInt += 10000;
        String optCord = String.valueOf(randomInt);

        //将OTP验证码同用户手机号关联
        httpServletRequest.getSession().setAttribute(telephone,optCord);

        //将OTP验证码通过短信通道给用户
        System.out.println("手机号："+telephone+"验证码："+optCord);
        return CommonReturnType.create(null);
    }





    /**
     * 核心领域的模型用户对象转换为可控UI使用的viewobject
     * @param userModel 模型用户
     * @return UI用户
     */
    private UserVO convertFromModel(UserModel userModel){
        UserVO userVO = new UserVO();
        if(userModel == null){
            return null;
        }
        BeanUtils.copyProperties(userModel,userVO);
        return userVO;
    }

}

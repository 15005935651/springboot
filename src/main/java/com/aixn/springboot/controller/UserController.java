package com.aixn.springboot.controller;

import com.aixn.springboot.controller.viewobject.UserVO;
import com.aixn.springboot.error.BusinessException;
import com.aixn.springboot.error.EmBusinessError;
import com.aixn.springboot.response.CommonReturnType;
import com.aixn.springboot.service.UserService;
import com.aixn.springboot.service.model.UserModel;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import sun.misc.BASE64Encoder;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Random;

/**
 * @author zxx
 * @date 2018/12/12 22:28
 */
@Controller("user")
@RequestMapping("/user")
@CrossOrigin(allowCredentials = "true",allowedHeaders = "*")
public class UserController extends BaseController{

    @Autowired
    private UserService userService;

    @Autowired
    private HttpServletRequest httpServletRequest;

    /**
     * 用户查询
     * @param id 用户id
     * @return 用户VO对象
     * @throws BusinessException
     */
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



    /**
     * /用户获取OTP短信接口
     * @param telephone 用户电话号码
     * @return 验证成功
     */
    @RequestMapping(value = "/getOtp",method = {RequestMethod.POST},consumes = CONTENT_TYPE_FORMED)
    @ResponseBody
    public CommonReturnType getOTP(@RequestParam(name="telephone")String telephone){
        //根据一定规则生成OTP验证码
        Random random = new Random();
        int randomInt = random.nextInt(99999);
        randomInt += 10000;
        String optCode = String.valueOf(randomInt);

        //将OTP验证码同用户手机号关联
        httpServletRequest.getSession().setAttribute(telephone,optCode);

        //将OTP验证码通过短信通道给用户
        System.out.println("手机号："+telephone+"验证码："+optCode);
        return CommonReturnType.create(null);
    }



    /**
     * 注册用户
     * @param telephone 电话
     * @param optCode 验证码
     * @param name 姓名
     * @param gender 性别
     * @param age 年龄
     * @param password 密码
     * @return 注册成功
     * @throws BusinessException 自定义异常
     */
    @RequestMapping(value = "/register",method = {RequestMethod.POST},consumes = CONTENT_TYPE_FORMED)
    @ResponseBody
    public CommonReturnType register(@RequestParam(name="telephone")String telephone,
                                     @RequestParam(name="otpCode")String optCode,
                                     @RequestParam(name="name")String name,
                                     @RequestParam(name="gender")Byte gender,
                                     @RequestParam(name="age")Integer age,
                                     @RequestParam(name="password")String password) throws BusinessException, UnsupportedEncodingException, NoSuchAlgorithmException {
        //验证手机号和对应的验证码相符
        String inSessionOtp = (String)this.httpServletRequest.getSession().getAttribute(telephone);
        if(!com.alibaba.druid.util.StringUtils.equals(optCode,inSessionOtp)){
           throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR,"验证码不正确");
        }

        //用户的注册流程
        UserModel userModel = new UserModel();
        userModel.setName(name);
        userModel.setGender(gender);
        userModel.setAge(age);
        userModel.setTelephone(telephone);
        userModel.setRegisterMode("ByPhone");
        userModel.setEncrptPassword(this.encodeByMd5(password));

        userService.register(userModel);
        String newStr = "注册成功";
        return CommonReturnType.create(newStr);

    }

    @RequestMapping(value = "/login",method = {RequestMethod.POST},consumes = CONTENT_TYPE_FORMED)
    @ResponseBody
    public CommonReturnType login(@RequestParam(name="telephone")String telephone,
                                   @RequestParam(name="password")String password) throws BusinessException, UnsupportedEncodingException, NoSuchAlgorithmException {
        //入参校验
        if(StringUtils.isEmpty(telephone) || StringUtils.isEmpty(password)){
            throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR);
        }
        //用户登录服务，校验登录是否合法
        UserModel userModel = userService.validateLogin(telephone,this.encodeByMd5(password));

        //将登录凭证加入的用户登录session中
        this.httpServletRequest.getSession().setAttribute("IS_LOGIN",true);
        this.httpServletRequest.getSession().setAttribute("LOGIN_USER",userModel);
        System.out.println("登录成功");
        return CommonReturnType.create(null);
    }



    /**
     * 加密注册时用户密码
     * @param password 密码
     * @return 加密密文
     * @throws NoSuchAlgorithmException 异常
     * @throws UnsupportedEncodingException 异常
     */
    private String encodeByMd5(String password) throws NoSuchAlgorithmException, UnsupportedEncodingException {
        //确定计算方法
        MessageDigest md5 = MessageDigest.getInstance("MD5");
        BASE64Encoder base64Encoder = new BASE64Encoder();
        //加密字符串
        return base64Encoder.encode(md5.digest(password.getBytes("utf-8")));

    }

    /**
     * 核心领域的模型用户对象转换为可控UI使用的viewobject
     * @param userModel 模型用户
     * @return UI用户object
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

package com.aixn.springboot.controller;

import com.aixn.springboot.controller.viewobject.OrderVO;
import com.aixn.springboot.error.BusinessException;
import com.aixn.springboot.error.EmBusinessError;
import com.aixn.springboot.response.CommonReturnType;
import com.aixn.springboot.service.OrderService;
import com.aixn.springboot.service.model.OrderModel;
import com.aixn.springboot.service.model.UserModel;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

/**
 * @author zxx
 * @date 2018/12/14 16:19
 */
@Controller
@RequestMapping("/order")
public class OrderController extends BaseController {

    @Autowired
    private OrderService orderService;

    @Autowired
    private HttpServletRequest httpServletRequest;

    /**
     * 登录后的用户下单
     * @param itemId 商品id
     * @param amount 下单数量
     * @return 下单信息
     * @throws BusinessException 未登录异常
     */
    @RequestMapping("/orderCreate")
    @ResponseBody
    public CommonReturnType createOrder(@RequestParam(name="itemId")Integer itemId,
                                  @RequestParam(name="amount")Integer amount) throws BusinessException {
        Boolean isLogin = (Boolean)httpServletRequest.getSession().getAttribute("IS_LOGIN");

        if(isLogin==null||!isLogin){
            throw new BusinessException(EmBusinessError.USER_NOT_LOGIN);
        }

        UserModel userModel= (UserModel) httpServletRequest.getSession().getAttribute("LOGIN_USER");

        OrderModel orderModel =orderService.createOrder(userModel.getId(),itemId,amount);

        OrderVO orderVO = this.convertFromModel(orderModel);

        System.out.println("下单成功");

        return CommonReturnType.create(orderVO);
    }

    private OrderVO convertFromModel(OrderModel orderModel) {

        if(orderModel==null){
            return null;
        }
        OrderVO orderVO = new OrderVO();
        BeanUtils.copyProperties(orderModel,orderVO);
        orderVO.setItemPrice(orderModel.getItemPrice().doubleValue());
        orderVO.setOrderPrice(orderModel.getOrderPrice().doubleValue());
        return orderVO;

    }
}

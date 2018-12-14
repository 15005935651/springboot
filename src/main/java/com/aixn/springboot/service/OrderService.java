package com.aixn.springboot.service;

import com.aixn.springboot.error.BusinessException;
import com.aixn.springboot.service.model.OrderModel;

/**
 * @author zxx
 * @date 2018/12/14 15:25
 */
public interface OrderService {
    OrderModel createOrder(Integer userId,Integer itemId,Integer amount) throws BusinessException;
}

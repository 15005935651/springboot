package com.aixn.springboot.service.impl;

import com.aixn.springboot.dao.OrderDOMapper;
import com.aixn.springboot.dataobject.OrderDO;
import com.aixn.springboot.error.BusinessException;
import com.aixn.springboot.error.EmBusinessError;
import com.aixn.springboot.service.ItemService;
import com.aixn.springboot.service.OrderService;
import com.aixn.springboot.service.UserService;
import com.aixn.springboot.service.model.ItemModel;
import com.aixn.springboot.service.model.OrderModel;
import com.aixn.springboot.service.model.UserModel;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Random;

/**
 * @author zxx
 * @date 2018/12/14 15:29
 */
@Service
public class OrderServiceImpl implements OrderService {


    @Autowired
    private ItemService itemService;
    @Autowired
    private UserService userService;
    @Autowired
    private OrderDOMapper orderDOMapper;

    @Override
    @Transactional
    public OrderModel createOrder(Integer userId, Integer itemId, Integer amount) throws BusinessException {
        //1.检验下单状态，商品是否存在，检验用户合法，购买数量是否正确
        ItemModel itemModel = itemService.getItemById(itemId);
        if (itemModel == null) {
            throw new BusinessException(EmBusinessError.ITEM_NOT_EXIT);
        }
        UserModel userModel = userService.getUserById(userId);
        if (userModel == null) {
            throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR, "用户信息不正确");
        }
        if (amount < 0 || amount > 99) {
            throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR, "数量信息不正确");
        }
        //2.落单减库存

        boolean result = itemService.decreaseStock(itemId,amount);
        if(!result){
            throw new BusinessException(EmBusinessError.STOCK_NOT_ENOUGH);
        }


        //3.订单入库
        OrderModel orderModel = new OrderModel();
        orderModel.setUserId(userId);
        orderModel.setItemId(itemId);
        orderModel.setAmount(amount);
        orderModel.setItemPrice(itemModel.getPrice());
        orderModel.setOrderPrice(itemModel.getPrice().multiply(new BigDecimal(amount)));


        Random random = new Random();
        int ends = random.nextInt(999);
        String newStr = String.format("%02d", ends);
        orderModel.setId("20181212" + newStr);

        OrderDO orderDO = this.convertFromOrderModel(orderModel);
        orderDOMapper.insertSelective(orderDO);

        //4.返回前端

        System.out.println(orderModel.getItemPrice());
        return orderModel;
    }

    private OrderDO convertFromOrderModel(OrderModel orderModel) {
        if (orderModel == null) {
            return null;
        }
        OrderDO orderDO = new OrderDO();
        BeanUtils.copyProperties(orderModel, orderDO);
        orderDO.setItemPrice(orderModel.getItemPrice().doubleValue());
        orderDO.setOrderPrice(orderModel.getOrderPrice().doubleValue());
        return orderDO;
    }
}

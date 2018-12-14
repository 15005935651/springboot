package com.aixn.springboot.service;

import com.aixn.springboot.error.BusinessException;
import com.aixn.springboot.service.model.ItemModel;

import java.util.List;

/**
 * @author zxx
 * @date 2018/12/13 20:13
 */
public interface ItemService {
    //创建商品
    ItemModel createItem(ItemModel itemModel);

    //商品列表浏览
    List<ItemModel> listItem();

    //商品详情浏览
    ItemModel getItemById(Integer id);

    //库存扣减
    boolean decreaseStock(Integer itemId, Integer amount) throws BusinessException;
}

package com.aixn.springboot.service.impl;

import com.aixn.springboot.dao.ItemDoMapper;
import com.aixn.springboot.dao.ItemStockDoMapper;
import com.aixn.springboot.dataobject.ItemDo;
import com.aixn.springboot.dataobject.ItemStockDo;
import com.aixn.springboot.error.BusinessException;
import com.aixn.springboot.service.ItemService;
import com.aixn.springboot.service.model.ItemModel;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author zxx
 * @date 2018/12/13 20:18
 */
@Service
public class ItemServiceImpl implements ItemService {

    @Autowired
    private ItemDoMapper itemDoMapper;

    @Autowired
    private ItemStockDoMapper itemStockDoMapper;

    @Override
    @Transactional
    public ItemModel createItem(ItemModel itemModel) {
        //校验入参

        //转换Model->dataObject
        ItemDo itemDo = this.convertFromModel(itemModel);
        //写入数据库
        itemDoMapper.insertSelective(itemDo);
        itemModel.setId(itemDo.getId());
        ItemStockDo itemStockDo = this.convertFromItemStockModel(itemModel);
        itemStockDoMapper.insertSelective(itemStockDo);
        //返回完成的对象
        return this.getItemById(itemModel.getId());
    }

    @Override
    public List<ItemModel> listItem() {

        List<ItemDo> itemDoList = itemDoMapper.listItem();

        List<ItemModel> itemModelList = itemDoList.stream().map(itemDo -> {
            ItemStockDo itemStockDo = itemStockDoMapper.selectByItemId(itemDo.getId());
            ItemModel itemModel = this.convertFromObject(itemDo, itemStockDo);
            return itemModel;
        }).collect(Collectors.toList());

        return itemModelList;
    }

    @Override
    public ItemModel getItemById(Integer id) {
        ItemDo itemDo = itemDoMapper.selectByPrimaryKey(id);
        if (itemDo == null) {
            return null;
        }
        //操作获得库存的数量
        //！！！！！返回为空指针
        ItemStockDo itemStockDo = itemStockDoMapper.selectByItemId(itemDo.getId());

        if (itemStockDo == null) {
            System.out.println("商品不存在");
        }

        //将dataObject->Model
        ItemModel itemModel = convertFromObject(itemDo, itemStockDo);
        return itemModel;
    }

    /**
     * 库存扣减
     * @param itemId 用户
     * @param amount 数量
     * @return 布尔
     * @throws BusinessException 异常
     */
    @Override
    @Transactional
    public boolean decreaseStock(Integer itemId, Integer amount) throws BusinessException {
        int affectedRow = itemStockDoMapper.decreaseStock(itemId,amount);
        if(affectedRow>0){
            //更新库存成功
            return true;
        }else {
            //更新库存失败
            return false;
        }
    }


    private ItemDo convertFromModel(ItemModel itemModel) {
        if (itemModel == null) {
            return null;
        }
        ItemDo itemDo = new ItemDo();
        BeanUtils.copyProperties(itemModel, itemDo);
        itemDo.setPrice(itemModel.getPrice().doubleValue());
        return itemDo;
    }


    private ItemStockDo convertFromItemStockModel(ItemModel itemModel) {
        if (itemModel == null) {
            return null;
        }
        ItemStockDo itemStockDo = new ItemStockDo();
        BeanUtils.copyProperties(itemModel, itemStockDo);
        itemStockDo.setItemId(itemModel.getId());
        itemStockDo.setStock(itemModel.getStock());
        return itemStockDo;

    }

    private ItemModel convertFromObject(ItemDo itemDo, ItemStockDo itemStockDo) {
        ItemModel itemModel = new ItemModel();
        BeanUtils.copyProperties(itemDo, itemModel);
        itemModel.setPrice(new BigDecimal(itemDo.getPrice()));
        itemModel.setStock(itemStockDo.getStock());

        return itemModel;
    }


}

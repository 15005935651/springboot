package com.aixn.springboot.controller;


import com.aixn.springboot.controller.viewobject.ItemVO;
import com.aixn.springboot.error.BusinessException;
import com.aixn.springboot.error.EmBusinessError;
import com.aixn.springboot.response.CommonReturnType;
import com.aixn.springboot.service.ItemService;
import com.aixn.springboot.service.model.ItemModel;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author zxx
 * @date 2018/12/13 20:49
 */

@Controller("item")
@RequestMapping("/item")
public class ItemController extends BaseController {

    @Autowired
    private ItemService itemService;

    /**
     * 添加商品
     *
     * @param title       名称
     * @param description 描述
     * @param price       价格
     * @param stock       库存
     * @param imgUrl      图片地址
     * @return 该商品
     */
    @RequestMapping("/createItem")
    @ResponseBody
    public CommonReturnType create(@RequestParam(name = "title") String title,
                                   @RequestParam(name = "description") String description,
                                   @RequestParam(name = "price") BigDecimal price,
                                   @RequestParam(name = "stock") Integer stock,
                                   @RequestParam(name = "imgUrl") String imgUrl) {
        ItemModel itemModel = new ItemModel();
        itemModel.setDescription(description);
        itemModel.setTitle(title);
        itemModel.setImgUrl(imgUrl);
        itemModel.setPrice(price);
        itemModel.setStock(stock);

        ItemModel itemModelForReturn = itemService.createItem(itemModel);

        ItemVO itemVO = convertFromModel(itemModelForReturn);

        System.out.println("商品添加成功");

        return CommonReturnType.create(itemVO);

    }

    /**
     * 获取指定id商品
     *
     * @param id 商品id
     * @return 该id商品
     */
    @RequestMapping("/getItem")
    @ResponseBody
    public CommonReturnType getItem(@RequestParam(name = "id") Integer id) throws BusinessException {
        ItemModel itemModel = itemService.getItemById(id);
        if(itemModel==null){
            throw new BusinessException(EmBusinessError.ITEM_NOT_EXIT);
        }
        ItemVO itemVO = convertFromModel(itemModel);
        return CommonReturnType.create(itemVO);
    }

    /**
     * 获取所有商品列表
     *
     * @return 商品列表
     */
    @RequestMapping(value = "/list")
    @ResponseBody
    public CommonReturnType listItem() {
        List<ItemModel> list = itemService.listItem();
        List<ItemVO> itemVOList = list.stream().map(itemModel -> {
            ItemVO itemVO = this.convertFromModel(itemModel);
            return itemVO;
        }).collect(Collectors.toList());
        return CommonReturnType.create(itemVOList);
    }


    private ItemVO convertFromModel(ItemModel itemModel) {
        if (itemModel == null) {
            return null;
        }
        ItemVO itemVO = new ItemVO();
        BeanUtils.copyProperties(itemModel, itemVO);
        return itemVO;

    }
}

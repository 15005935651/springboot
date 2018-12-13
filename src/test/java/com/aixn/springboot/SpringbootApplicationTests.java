package com.aixn.springboot;

import com.aixn.springboot.controller.viewobject.ItemVO;
import com.aixn.springboot.response.CommonReturnType;
import com.aixn.springboot.service.ItemService;
import com.aixn.springboot.service.model.ItemModel;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.bind.annotation.RequestParam;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SpringbootApplicationTests {

    @Test
    public void contextLoads() {
    }

    @Test
    public void go(){
        getItem(1);

    }

    @Autowired
    private ItemService itemService;

    public CommonReturnType getItem(@RequestParam(name="id")Integer id){
        ItemModel itemModel = itemService.getItemById(id);
        ItemVO itemVO = convertFromModel(itemModel);
        System.out.println(itemModel.getId()+itemModel.getDescription());
        return CommonReturnType.create(itemVO);
    }

    private ItemVO convertFromModel(ItemModel itemModel) {
        if(itemModel==null){
            return null;
        }
        ItemVO itemVO = new ItemVO();
        BeanUtils.copyProperties(itemModel,itemVO);
        return itemVO;

    }

}

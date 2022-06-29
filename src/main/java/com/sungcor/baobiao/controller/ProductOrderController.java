package com.sungcor.baobiao.controller;


import com.sungcor.baobiao.entity.Result;
import com.sungcor.baobiao.service.IProductOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class ProductOrderController {

    @Autowired
    private IProductOrderService productOrderService;

    @GetMapping("/getgoods")
    public Result getGoods(){
        return productOrderService.getgoods();
    }

    @PostMapping("/buyProductByThread")
    public void buyProductByThread(@RequestBody Map map){
        productOrderService.buyByThread(map);
    }

    @PostMapping("/buyProduct")
    public Result buyProduct(@RequestBody Map map){
        return productOrderService.buy(map);
    }
}

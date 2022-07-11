package com.sungcor.baobiao.controller;


import com.sungcor.baobiao.entity.Result;
import com.sungcor.baobiao.service.IProductOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.concurrent.ExecutionException;

@RestController
public class ProductOrderController {

    @Autowired
    private IProductOrderService productOrderService;

    @GetMapping("/getgoods")
    public Result getGoods(){
        return productOrderService.getgoods();
    }

    @PostMapping("/buyProductByThread")
    public void buyProductByThread(@RequestBody Map map)  throws InterruptedException{
        productOrderService.buyByThread(map);
    }

    @PostMapping("/buyProduct")
    public void buyProduct(@RequestBody Map map) throws Exception{
        productOrderService.buy(map);
    }

    @GetMapping("/simulateException")
    public void simuExce(){
        productOrderService.simulateException();
    }

    @PostMapping("/pay")
    public void payMoney(@RequestBody Map map){
//        productOrderService.pay();
    }
}

package com.sungcor.baobiao.service.Impl;


import com.sungcor.baobiao.entity.Customer;
import com.sungcor.baobiao.entity.Product;
import com.sungcor.baobiao.entity.Result;
import com.sungcor.baobiao.mapper.ProductOrderMapper;
import com.sungcor.baobiao.service.IProductOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class ProductOrderServiceImpl implements IProductOrderService {

    @Autowired
    private ProductOrderMapper productOrderMapper;

    @Override
    public Result buyByThread(Map map) {
        return null;
    }

    @Override
    public Result buy(Map map) {
        String userId = map.get("userId").toString();
        String productId = map.get("productId").toString();
        Integer count = (Integer) map.get("count");
        if (userId == null || productId == null || count == null) {
            return Result.error("未获取到参数");
        }
        try {
            Customer customer = productOrderMapper.getTheCustomer(userId);
            Product product = productOrderMapper.getTheProduct(productId);
            if (customer == null && product == null) {
                return Result.error("库存不足");
            }
            if (count > 0 && count <= product.getCount()) {
                return pay(customer, product, count);
            }
            return Result.error("顾客或商品信息有误");
        } catch (Exception e) {
            e.printStackTrace();
            return Result.error(e.toString());
        }
    }

    @Override
    public Result pay(Customer customer, Product product, Integer count) throws InterruptedException {
        Integer needMoney = count * product.getValue();
        if (customer.getBalance() >= needMoney) {
            customer.setBalance(customer.getBalance() - needMoney);
            product.setCount(product.getCount() - count);

            productOrderMapper.updateCustomer(customer);
            productOrderMapper.updateProduct(product);
            return Result.ok("抢购成功");
        }
        return Result.error("余额不足");
    }
}

class productThread implements Runnable{
    private int total=100;

    public void run() {
        while (true){
            synchronized (this){
                if(total>0){
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                }
            }
        }
    }
}

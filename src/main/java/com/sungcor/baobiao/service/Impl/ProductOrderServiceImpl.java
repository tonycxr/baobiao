package com.sungcor.baobiao.service.Impl;

import com.sungcor.baobiao.entity.Customer;
import com.sungcor.baobiao.entity.Product;
import com.sungcor.baobiao.entity.Result;
import com.sungcor.baobiao.mapper.ProductOrderMapper;
import com.sungcor.baobiao.service.IProductOrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.Serializable;
import java.util.Map;
import java.util.concurrent.*;

@Service
@Slf4j
public class ProductOrderServiceImpl implements IProductOrderService {

    @Autowired
    private ProductOrderMapper productOrderMapper;

    @Resource(name = "threadPoolTaskExecutor")
    private ThreadPoolTaskExecutor threadPoolTaskExecutor;

    static ExecutorService executorService = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors() * 2);

    @Override
    public void sleep(int i) {
        try {
            Thread.sleep(i);
            System.out.println("休眠" + i + "毫秒");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void buyByThread(Map map) {
        threadPoolTaskExecutor.execute(() -> buy(map));
    }

    @Override
    public Result buy(Map map) {
        String userId = map.get("userId").toString();
        String productId = map.get("productId").toString();
        Integer count = (Integer) map.get("count");
        if (userId == null || productId == null || count == null) {
            return Result.error("未获取到参数");
        }
        synchronized (this) {
            try {
                Customer customer = productOrderMapper.getTheCustomer(userId);
                Product product = productOrderMapper.getTheProduct(productId);
                if (customer == null || product == null) {
                    return Result.error("顾客或商品信息有误");
                }
                if (count > 0 && count <= product.getCount()) {
                    return pay(customer, product, count);

                }
                return Result.error("库存不足");
            } catch (Exception e) {
                e.printStackTrace();
                return Result.error(e.toString());
            }
        }
    }

    @Override
    public Result pay(Customer customer, Product product, Integer count){
        Integer needMoney = count * product.getValue();
        Future<Serializable> future = executorService.submit(() -> {
            if (customer.getBalance() >= needMoney) {
                Long l = (long) (60000 * Math.random());
                log.info("付款要花" + l / 1000 + "秒");
                TimeUnit.SECONDS.sleep(l/1000);
                customer.setBalance(customer.getBalance() - needMoney);
                product.setCount(product.getCount() - count);
                productOrderMapper.updateCustomer(customer);
                productOrderMapper.updateProduct(product);
            }
            return Result.error("余额不足");
        });
        try {
            future.get(30, TimeUnit.SECONDS);
            return Result.ok("抢购成功");
        } catch (InterruptedException | ExecutionException | TimeoutException e) {
            future.cancel(true);
            return Result.error("付款超时");
        }
    }

    @Override
    public Result getgoods() {
        productOrderMapper.getGoods();
        return Result.ok("补货成功");
    }
}

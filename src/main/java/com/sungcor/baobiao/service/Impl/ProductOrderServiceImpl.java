package com.sungcor.baobiao.service.Impl;

import com.sungcor.baobiao.entity.Customer;
import com.sungcor.baobiao.entity.Product;
import com.sungcor.baobiao.entity.Result;
import com.sungcor.baobiao.mapper.ProductOrderMapper;
import com.sungcor.baobiao.service.IProductOrderService;
import com.sungcor.baobiao.utils.CustomException;
import com.sungcor.baobiao.utils.ThreadRecord;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.text.NumberFormat;
import java.util.*;
import java.util.concurrent.*;

@Service
@Slf4j
public class ProductOrderServiceImpl implements IProductOrderService {

    @Autowired
    private ProductOrderMapper productOrderMapper;

    @Resource(name = "threadPoolTaskExecutor")
    private ThreadPoolTaskExecutor threadPoolTaskExecutor;

    @Override
    public void sleep(int i) {
        try {
            Thread.sleep(i);
            System.out.println("休眠" + i + "毫秒");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public Map getTheDetail(Map map) {
        String userId = map.get("userId").toString();
        String productId = map.get("productId").toString();
        Integer count = (Integer) map.get("count");
        if (userId != null && productId != null && count != null) {
            Customer customer = productOrderMapper.getTheCustomer(userId);
            Product product = productOrderMapper.getTheProduct(productId);
            Map detail = new HashMap();
            detail.put("customer", customer);
            detail.put("product", product);
            detail.put("count", count);
            return detail;
        } else {
            return null;
        }
    }

    public boolean JudgeToPurchase(Map map) {
        try {
            boolean canBuy = buy(map);
            if (canBuy) {
//                        Long l = (long) (60000 * Math.random());
//                        log.info("付款要花" + l / 1000 + "秒");
//                        Thread.sleep(l);
//                    Future future = threadPoolTaskExecutor.submit(() -> {
                return pay(map);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
            return false;
        }
        return false;
    }

    @Override
    public void buyByThread(Map map) {
        // 建立ExecutorService线程池，threadNum个线程可以同时访问

            for (int i = 0; i < 20; i++) {
                threadPoolTaskExecutor.submit(() -> {
                if (JudgeToPurchase(map)) {
                    updateDatabase(map);
                }
                });
            }

    }
//        for (int i = 1; i <= 20; i++) {
//            Future future = threadPoolTaskExecutor.submit(() -> {
//                theWholePurchase(map);
//            });
//            try {
//                future.get(30, TimeUnit.SECONDS);
//            } catch (InterruptedException | ExecutionException | TimeoutException e) {
//                future.cancel(true);
//                log.info("付款超时");
////            throw new CustomException("付款超时");
//            }
//        }


    @Transactional(rollbackFor = {CustomException.class, ArithmeticException.class})
    public void simulateException() {
        Product cvbn5 = productOrderMapper.getTheProduct("cvbn5");
        cvbn5.setCount(600);
        productOrderMapper.updateProduct(cvbn5);
        int i = 1 / 0;
    }

    @Override
//    @Transactional(rollbackFor = {CustomException.class})
    public Boolean buy(Map map) throws InterruptedException {
        Map detail = getTheDetail(map);
        if (detail == null) {
            return false;
        }
        Customer customer = (Customer) detail.get("customer");
        Product product = (Product) detail.get("product");
        Integer count = (Integer) detail.get("count");
        if (customer != null && product != null) {
            if (count > 0 && count <= product.getCount()) {
                if (customer.getBalance() >= count * product.getValue()) {
                    return true;
                }
//                       throw new CustomException("余额不足");
            }
//                throw new CustomException("顾客或商品信息有误");
        }
//            throw new CustomException("未获取到参数");
        return false;
    }

    @Override
    public Result getgoods() {
        productOrderMapper.getGoods();
        productOrderMapper.getMoney();
        return Result.ok("重置成功");
    }

    @Override
//    @Transactional(rollbackFor = {CustomException.class})
    public boolean pay(Map map) throws InterruptedException {
//        Map detail = getTheDetail(map);
//        Customer customer = (Customer) detail.get("customer");
//        Product product = (Product) detail.get("product");
//        Integer count = (Integer) detail.get("count");
        Long l = (long) (60000 * Math.random());
        log.info("付款要花" + l / 1000 + "秒");
        if(l<=30000) {
            Thread.sleep(l);
            return true;
        }else
        {
            Thread.sleep(l);
            log.info("付款超时");
            return false;
        }
//        customer.setBalance(customer.getBalance() - count * product.getValue());
//        product.setCount(product.getCount() - count);
//        productOrderMapper.updateCustomer(customer);
//        productOrderMapper.updateProduct(product);

    }


    public void updateDatabase(Map map) {
        Map detail = getTheDetail(map);
        Customer customer = (Customer) detail.get("customer");
        Product product = (Product) detail.get("product");
        Integer count = (Integer) detail.get("count");
        customer.setBalance(customer.getBalance() - count * product.getValue());
        product.setCount(product.getCount() - count);
        productOrderMapper.updateCustomer(customer);
        productOrderMapper.updateProduct(product);
        log.info("抢购成功");
    }
}

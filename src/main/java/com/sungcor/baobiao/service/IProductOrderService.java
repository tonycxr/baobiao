package com.sungcor.baobiao.service;

import com.borland.jbcl.util.Res;
import com.sungcor.baobiao.entity.Customer;
import com.sungcor.baobiao.entity.Product;
import com.sungcor.baobiao.entity.Result;

import java.util.Map;
import java.util.concurrent.ExecutionException;

public interface IProductOrderService {

    void sleep(int i);
    void buyByThread(Map map);
    Boolean buy(Map map) throws InterruptedException;
    Result getgoods();
    boolean pay(Map map) throws InterruptedException;
    void simulateException();
}

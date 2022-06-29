package com.sungcor.baobiao.service;

import com.borland.jbcl.util.Res;
import com.sungcor.baobiao.entity.Customer;
import com.sungcor.baobiao.entity.Product;
import com.sungcor.baobiao.entity.Result;

import java.util.Map;

public interface IProductOrderService {

    void sleep(int i);
    void buyByThread(Map map);
    Result buy(Map map);
    Result pay(Customer customer, Product product, Integer count);
    Result getgoods();
}

package com.sungcor.baobiao.service;

import com.sungcor.baobiao.entity.SungcorProduct;

import java.util.Map;

public interface ISungcorProductService {
    SungcorProduct getSungcorProduct(String productName);
    Integer getProductProfit(Map map);
}

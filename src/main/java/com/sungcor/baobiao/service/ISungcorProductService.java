package com.sungcor.baobiao.service;


import com.sungcor.baobiao.entity.Result;
import com.sungcor.baobiao.entity.SungcorProduct;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

public interface ISungcorProductService {
    SungcorProduct getSungcorProduct(String productName);
    Integer getProductProfit(Map map);
    Result getTheRaq(Map map) throws Throwable;
    Result getTheExcel(HttpServletResponse response,String name);
}

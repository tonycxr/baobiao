package com.sungcor.baobiao.controller;

import com.sungcor.baobiao.entity.Result;
import com.sungcor.baobiao.entity.SungcorProduct;
import com.sungcor.baobiao.report.service.IReportService;
import com.sungcor.baobiao.service.IProductOrderService;
import com.sungcor.baobiao.service.ISungcorProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

@RestController
public class ReportFormController {

    @Autowired
    private IReportService reportService;

    @Autowired
    private ISungcorProductService sungcorProductService;

    @Autowired
    private IProductOrderService productOrderService;

    @PostMapping("/buyProductByThread")
    public Result buyProductByThread(@RequestBody Map map){
        return productOrderService.buyByThread(map);
    }

    @PostMapping("/buyProduct")
    public Result buyProduct(@RequestBody Map map){
        return productOrderService.buy(map);
    }

    @PostMapping("/getSungcorProduct")
    public SungcorProduct getSungcorProduct(@RequestBody Map map){
        if(map.get("productName")!=null){
            return sungcorProductService.getSungcorProduct(map.get("productName").toString());
        }else {
            return null;
        }

    }

    @PostMapping("/getProductProfit")
    public Integer getProductProfit(@RequestBody Map map){
        if(map.get("productName")!=null && map.get("seasonNumber")!=null){
            return sungcorProductService.getProductProfit(map);
        }else {
            return null;
        }
    }

    @PostMapping("/getTheRaq")
    public Result getTheRaq(HttpServletResponse response,@RequestBody Map map)throws Throwable{
        return sungcorProductService.getTheRaq(response,map);
    }

    @GetMapping("/getTheExcel")
    public Result getTheExcel(HttpServletResponse response,@RequestParam(value = "name") String name) throws Exception {
        return sungcorProductService.getTheExcel(response,name);
    }

    @GetMapping("/getAnnotationExcel")
    public Result getAnnotationExcel(@RequestBody Map map){
        return sungcorProductService.getAnnotationExcel(map);
    }
}

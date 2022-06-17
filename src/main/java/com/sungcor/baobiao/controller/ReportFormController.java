package com.sungcor.baobiao.controller;

import com.runqian.report4.model.ReportDefine;
import com.runqian.report4.usermodel.IReport;
import com.sungcor.baobiao.entity.Result;
import com.sungcor.baobiao.entity.SungcorProduct;
import com.sungcor.baobiao.report.service.IReportService;
import com.sungcor.baobiao.service.ISungcorProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.Map;

@RestController
public class ReportFormController {

    @Autowired
    private IReportService reportService;

    @Autowired
    private ISungcorProductService sungcorProductService;

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
    public IReport getTheRaq(@RequestBody Map map)throws Exception{
        String path = map.get("path").toString();
        IReport target = sungcorProductService.getTheRaq(path);
        return target;
    }
}

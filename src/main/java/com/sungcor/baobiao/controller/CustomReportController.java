package com.sungcor.baobiao.controller;

import com.alibaba.fastjson.JSON;
import com.sungcor.baobiao.STSMApplicationContext;
import com.sungcor.baobiao.STSMConstant;
import com.sungcor.baobiao.entity.UserInfoBean;
import com.sungcor.baobiao.report.bean.ReportModelBean;
import com.sungcor.baobiao.report.bean.ReportQuerySettingBean;
import com.sungcor.baobiao.report.service.IReportModelService;
import com.sungcor.baobiao.report.service.IReportQuerySettingService;
import com.sungcor.baobiao.report.service.IReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import java.util.HashMap;
import java.util.Map;

import static com.sungcor.baobiao.utils.Util.mapToObject;

@RestController
public class CustomReportController {
    @Autowired
    private IReportService reportService;

    @Autowired
    private IReportModelService reportModelService;

    @Autowired
    private IReportQuerySettingService reportQuerySettingService;

    @PostMapping("/findBeanById")
    public Map findBeanById(@RequestBody Map map) throws Exception{
        String modelId = map.get("modelId").toString();
        UserInfoBean user = (UserInfoBean) mapToObject((Map) map.get("UserInfo"),UserInfoBean.class);
        ReportModelBean reportModelBean = reportModelService.findBeanById(modelId);
        HashMap target = new HashMap();
        if(reportModelBean==null){
            return null;
        }
        if (reportModelBean.getTypeId() == STSMConstant.NUM_SIX) {
            //组合报表
            target.put("reportType","groupReport");
        } else if (reportModelBean.getTypeId() == STSMConstant.NUM_FIVE) {
            //分组报表
            target.put("reportType", "groupingReport");
        }else if(reportModelBean.getTypeId()==STSMConstant.NUM_SEVEN){
            //趋势报表
            target.put("reportType","trendReport");
        }
        else {
            //普通报表
            target.put("reportType", "ordinaryReport");
        }

//        target.put("reportUrl", this.getURL(request));
        target.put("reportUrl",null);
        target.put("modelId", modelId);
        target.put("typeId", reportModelBean.getTypeId());
        target.put("userId", user.getUserId());
        String skinPath = "/css/default";
        if(user.getFace()!=null){
            skinPath = STSMApplicationContext.getProperty(user.getFace());
        }
//        findSkinColor(request,skinPath);
        map.put("skin", "/css/default");
        ReportQuerySettingBean r = reportQuerySettingService.findByModelId(modelId);
        HashMap mapDate = reportQuerySettingService.getQueryDate(r);
        String beginTime = mapDate.get("beginTime").toString();
        String endTime = mapDate.get("endTime").toString();
        String reportTime = "报表发起时间："+beginTime+"，报表结束时间："+endTime;
        target.put("reportTime", reportTime);
        target.put("reportName", reportModelBean.getReportName());
        target.put("showTable", reportModelBean.getShowTable());
        target.put("showChart", reportModelBean.getShowChart());
        //缓存默认的查询条件
//        reportService.defaultModelQuery(modelId, user.getUserId(), beginTime,endTime,reportModelBean.getTypeId());
        return target;
    }
}

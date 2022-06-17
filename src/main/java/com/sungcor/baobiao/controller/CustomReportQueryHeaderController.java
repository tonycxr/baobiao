package com.sungcor.baobiao.controller;

import com.sungcor.baobiao.report.service.*;
import com.sungcor.baobiao.report.bean.*;
import com.sungcor.baobiao.report.util.ServiceResult;
import com.sungcor.baobiao.utils.SessionHelper;
import org.apache.commons.lang.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.sungcor.baobiao.report.util.ServiceResult;

/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 * Date: 16-5-26
 * Time: 下午4:14
 * To change this template use File | Settings | File Templates.
 */

@RestController
@RequestMapping("/customqueryheader")
public class CustomReportQueryHeaderController {

    private ServiceResult serviceResult;

    private IReportQuerySettingService reportQuerySettingService;

    @PostMapping("/comprehensiveQuery")
    public String comprehensiveQuery(Map<String, Object> map) {
        String modelId = (String) map.get("modelId");
        List list = reportQuerySettingService.getQueryCondition(modelId);
        serviceResult.setData(list);
        serviceResult.setOperatorResult(true);
        return serviceResult.returnServiceResult();
    }

    @PostMapping("/initSelectValue")
    public String initSelectValue(Map<String, Object> map) {
        String id = (String) map.get("id");
        String dataSource = (String) map.get("dataSource");
        List list = new ArrayList();
        if (StringUtils.isNotBlank(dataSource) && dataSource.equals("dict")) {
            list = reportQuerySettingService.getSelectValue(id);
        } else if (StringUtils.isNotBlank(dataSource) && dataSource.equals("bpm")) {
            list = reportQuerySettingService.getSelectValue2(id);
        } else if (StringUtils.isNotBlank(dataSource) && dataSource.equals("sf_service_category")) {
            list = reportQuerySettingService.getSelectValue3(id);
        } else if (StringUtils.isNotBlank(dataSource) && dataSource.equals("customer")) {
            list = reportQuerySettingService.getSelectValue4(id);
        } else if (StringUtils.isNotBlank(dataSource) && dataSource.equals("customModel")) {
            list = reportQuerySettingService.getSelectValue5(id);
        }
        serviceResult.setData(list);
        serviceResult.setOperatorResult(true);
        return serviceResult.returnServiceResult();
    }

    @PostMapping("/initQueryTime")
    public String initQueryTime(Map<String, Object> receive) {
        String modelId = (String) receive.get("modelId");
        ReportQuerySettingBean r = reportQuerySettingService.findByModelId(modelId);
        HashMap map = reportQuerySettingService.getQueryDate(r);
        r.setBeginDate(map.get("beginTime").toString());
        r.setEndDate(map.get("endTime").toString());
        serviceResult.setData(r);
        serviceResult.setOperatorResult(true);
        return serviceResult.returnServiceResult();
    }

    @PostMapping("/queryTimeByType")
    public String queryTimeByType(Map<String, Object> receive) {
        ReportQuerySettingBean r = new ReportQuerySettingBean();
        r.setDatePurview((String) receive.get("datePurview"));
        HashMap map = reportQuerySettingService.getQueryDate(r);
        r.setBeginDate(map.get("beginTime").toString());
        r.setEndDate(map.get("endTime").toString());
        serviceResult.setData(r);
        serviceResult.setOperatorResult(true);
        return serviceResult.returnServiceResult();
    }
}

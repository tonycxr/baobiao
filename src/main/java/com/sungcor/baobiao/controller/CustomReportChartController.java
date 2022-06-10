package com.sungcor.baobiao.controller;

import com.sungcor.baobiao.report.service.*;
import com.sungcor.baobiao.report.bean.*;
import com.sungcor.baobiao.utils.SessionHelper;
import lombok.extern.log4j.Log4j;
import org.apache.struts2.ServletActionContext;
import org.springframework.web.bind.annotation.RestController;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.sungcor.baobiao.report.util.ServiceResult;

/**
 * Created by lenovo on 2016/8/4.
 */

@RestController
public class CustomReportChartController
{

    private IReportChartService reportChartService;
    private ServiceResult serviceResult;
    private IReportTypeAttrService reportTypeAttrService;

    private IReportModelService reportModelService;

    private List<ReportChartTypeBean> chartTypeList;
    private ReportModelBean reportModel;
    private List<ReportChartModelBean> chartModelList;
    private ReportChartModelBean chartModel;
    private String[] ids;

    public int getResultSize()
    {
        return 0;
    }


//    public List listResults(int from, int length)
//    {
//        List list = null;
//        if(request.getParameter("index")!=null){
//            list = queryIndexByModelId();
//        }else{
//            list = queryChart();
//        }
//        return list;
//    }

    public List queryChart(){
        chartModelList = reportChartService.queryChart(reportModel.getId());
        return chartModelList;
    }



    public List<Map> queryIndexByModelId(){
        List<Map> list = null;
        HashMap<String,Object> map = new HashMap<String, Object>();
        map.put("typeId",reportModel.getTypeId());
        map.put("modelId",reportModel.getId());
        list = reportTypeAttrService.findSelIndex(map);
        return list;
    }

    public String index(){
        reportModel = reportModelService.findBeanById(String.valueOf(reportModel.getId()));
        chartTypeList = reportChartService.queryTypeList();
        if(reportModel.getTypeId()==7){
            chartTypeList = chartTypeList.subList(2,chartTypeList.size());
        }else{
            chartTypeList.remove(2);
        }
        return "index";
    }

    public String saveInfo(){
        reportChartService.updateModel(reportModel);
        reportChartService.insertList(chartModelList);
        return "success";
    }

    public String delete(){
        reportChartService.deleteChartById(ids);
        serviceResult.setOperatorResult(true);
        return serviceResult.returnServiceResult();
    }

    public String queryIsIndex(){
        int count = reportChartService.queryIsIndex(chartModel);
        if(count>0){
            serviceResult.setOperatorResult(false);
        }else{
            serviceResult.setOperatorResult(true);
        }
        return serviceResult.returnServiceResult();
    }

    public List<ReportChartTypeBean> getChartTypeList()
    {
        return chartTypeList;
    }

    public void setChartTypeList(List<ReportChartTypeBean> chartTypeList)
    {
        this.chartTypeList = chartTypeList;
    }

    public ReportModelBean getReportModel()
    {
        return reportModel;
    }

    public void setReportModel(ReportModelBean reportModel)
    {
        this.reportModel = reportModel;
    }

    public List<ReportChartModelBean> getChartModelList()
    {
        return chartModelList;
    }

    public void setChartModelList(List<ReportChartModelBean> chartModelList)
    {
        this.chartModelList = chartModelList;
    }

    public String[] getIds()
    {
        return ids;
    }

    public void setIds(String[] ids)
    {
        this.ids = ids;
    }

    public ReportChartModelBean getChartModel()
    {
        return chartModel;
    }

    public void setChartModel(ReportChartModelBean chartModel)
    {
        this.chartModel = chartModel;
    }
}

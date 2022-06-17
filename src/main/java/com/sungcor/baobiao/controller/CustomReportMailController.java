package com.sungcor.baobiao.controller;

import com.sungcor.baobiao.entity.Result;
import com.sungcor.baobiao.report.service.*;
import com.sungcor.baobiao.report.bean.*;
import com.sungcor.baobiao.utils.SessionHelper;
import lombok.extern.log4j.Log4j;
import org.springframework.web.bind.annotation.RestController;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import com.sungcor.baobiao.report.util.ServiceResult;
import com.alibaba.fastjson.JSON;

/**
 * Created by 清 on 2016/6/15.
 */
@RestController
public class CustomReportMailController {

    private IReportMailService iReportMailService;

    private IReportTaskService reportTaskService;
    private ReportMailBean reportMailBean;
    private String id;
    private String taskId;
    private String taskName;
    private String reportType;
    private String isZuHe;

    public int getResultSize() {
        HashMap map = new HashMap();
        map.put("id", id);
        return iReportMailService.findListCountByMap(map);
    }


    public List listResults(int from, int length) {
        HashMap map = new HashMap();
        map.put("beginRow", from);
        map.put("pageRowCnt", length);
        map.put("id", id);
        return iReportMailService.findListByMap(map);
    }

//    public String list() {
//        return this.getGrid();
//    }

    public String showAdd() {
        List<ReportMailBean> reportMailBeans=iReportMailService.getByTaskId(Integer.parseInt(taskId));
        if(reportMailBeans.size()>0){
            reportMailBean=reportMailBeans.get(0);
        }else {
            reportMailBean=new ReportMailBean();
            reportMailBean.setTaskId(Integer.parseInt(taskId));
            reportMailBean.setTaskName(reportTaskService.findById(Integer.parseInt(taskId)).getName());
        }
        if(reportType.equals("6")){
            isZuHe="is";
        }else{
            isZuHe="isNot";
        }
        return "showAdd";
    }

    public Result saveOrUpdateMail(){
        iReportMailService.insertMail(reportMailBean);
        HashMap<String, String> sr = new HashMap<String, String>(2);
        sr.put("taskId",reportMailBean.getTaskId()+"");
        sr.put("taskName",reportMailBean.getTaskName());
        return Result.ok(JSON.toJSONString(sr));
    }


    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public ReportMailBean getReportMailBean() {
        return reportMailBean;
    }

    public void setReportMailBean(ReportMailBean reportMailBean) {
        this.reportMailBean = reportMailBean;
    }

    public String getIsZuHe() {
        return isZuHe;
    }

    public void setIsZuHe(String isZuHe) {
        this.isZuHe = isZuHe;
    }

    public String getReportType() {
        return reportType;
    }

    public void setReportType(String reportType) {
        this.reportType = reportType;
    }
}

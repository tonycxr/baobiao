package com.sungcor.baobiao.report.service;


import com.sungcor.baobiao.report.bean.ReportMailBean;
import com.sungcor.baobiao.report.bean.ReportMailUserBean;

import java.util.HashMap;
import java.util.List;

public interface IReportMailService {

    //根据定时任务
    public List<HashMap> findTaskMail(String taskId);

    public void insertMail(ReportMailBean reportMailBean);
    public List<ReportMailBean> getByTaskId(int taskId);
    public Integer findListCountByMap(HashMap map);
    public List<ReportMailUserBean>findListByMap(HashMap map);
    public void deleteMail(String[] ids);
}

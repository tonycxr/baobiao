package com.sungcor.baobiao.report.dao;

import com.sungcor.baobiao.report.bean.ReportMailBean;
import com.sungcor.baobiao.report.bean.ReportMailUserBean;

import java.util.HashMap;
import java.util.List;

public interface IReportMailDAO {
    public List<HashMap> findTaskMail(String taskId);

    public void insertMail(ReportMailBean reportMailBean);
    public List<ReportMailBean> getByTaskId(int taskId);
    public void deleteMail(int taskId);
    public Integer findListCountByMap(HashMap map);
    public List<ReportMailUserBean> findListByMap(HashMap map);
    public void deleteMails(String[] ids);
}

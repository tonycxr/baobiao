package com.sungcor.baobiao.report.service;



import com.sungcor.baobiao.report.bean.ReportCustomIndexBean;

import java.util.HashMap;
import java.util.List;


public interface IReportCustomIndexService
{
    public ReportCustomIndexBean getById(int id);
    public void addCusIndex(ReportCustomIndexBean bean);
    public void updateCusIndex(ReportCustomIndexBean bean);
    public List<String> deleteCusIndex(String[] ids);
    public List<ReportCustomIndexBean> queryCusIndex(HashMap<String,String> map);
    public int queryCusIndexCount(HashMap<String,String> map);
}

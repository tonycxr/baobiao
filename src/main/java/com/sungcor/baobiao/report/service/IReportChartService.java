package com.sungcor.baobiao.report.service;



import com.sungcor.baobiao.report.bean.ReportChartModelBean;
import com.sungcor.baobiao.report.bean.ReportChartTypeBean;
import com.sungcor.baobiao.report.bean.ReportModelBean;

import java.util.List;

public interface IReportChartService
{
    public List<ReportChartTypeBean> queryTypeList();

    public List<ReportChartModelBean> queryChart(int modelId);

    public ReportChartModelBean queryChartById(int id);

    public void updateModel(ReportModelBean reportModel);

    public void insertList(List<ReportChartModelBean> list);

    public void deleteChartByModelId(int modelId);
    public void deleteChartById(String[] ids);
    public int queryIsIndex(ReportChartModelBean chartModel);
}

package com.sungcor.baobiao.report.dao;

import com.sungcor.baobiao.report.bean.ReportFieldBean;

public interface IReportFieldDAO {
    public void insert(ReportFieldBean reportFieldBean);

    public void update(ReportFieldBean reportFieldBean);

    public void delete(int Id);

    public void deleteFieldName(String fieldName);

    public ReportFieldBean getReportFieldBean(int Id);

    public ReportFieldBean getReportFieldBeanByFieldName(String fieldName);
}

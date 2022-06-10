package com.sungcor.baobiao.report.service;


import com.sungcor.baobiao.report.bean.ReportFieldBean;


public interface IReportFieldService {

    public void insert(ReportFieldBean reportFieldBean);

    public void update(ReportFieldBean reportFieldBean);

    public void delete(int Id);
    public ReportFieldBean getReportFieldBean(int Id);
    public ReportFieldBean getReportFieldBeanByFieldName(String fieldName);
    public void deleteFieldName(String fieldName);
}

package com.sungcor.baobiao.report.service;


import com.sungcor.baobiao.report.bean.ReportTypeAttributeBean;

import java.util.List;

public interface IReportTypeAttributeService {
    public void insert(ReportTypeAttributeBean reportTypeAttributeBean);

    public void update(ReportTypeAttributeBean reportTypeAttributeBean);

    public void delete(int Id);
    public void deleteFieldName(String fieldName);

    public List<ReportTypeAttributeBean> getReportTypeAttributeByFieldName(String fieldName);
}

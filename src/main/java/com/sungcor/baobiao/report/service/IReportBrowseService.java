package com.sungcor.baobiao.report.service;

import com.sungcor.baobiao.report.bean.ReportTaskFileBean;

import java.util.HashMap;
import java.util.List;

public interface IReportBrowseService {
    Integer findListCountByMap(HashMap map);
    List<ReportTaskFileBean> findListByMap(HashMap map);
    List<ReportTaskFileBean> search(HashMap params);
    void insert(ReportTaskFileBean reportTaskFileBean);
    void delete(String[] ids);
}

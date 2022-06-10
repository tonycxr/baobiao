package com.sungcor.baobiao.report.service;


import com.sungcor.baobiao.report.bean.ReportGroupBean;
import com.sungcor.baobiao.report.bean.ReportTypeBean;

import java.util.List;
import java.util.Map;


public interface IReportGroupService {

    //获取所有的报表分组
    List<ReportTypeBean> findReportTypeList();

    List<ReportGroupBean> findReportGroupList();

    void addReportGroup(ReportGroupBean reportGroup);

    void deleteReportGroupById(String[] ids);

    void updateReportGroup(ReportGroupBean reportGroup);

    boolean checkReportGroup(String name);

    ReportGroupBean findReportGroupById(int id);

    List<Map> findModelNumByGroupId(String[] ids);
}

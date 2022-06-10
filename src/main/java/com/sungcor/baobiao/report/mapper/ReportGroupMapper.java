package com.sungcor.baobiao.report.mapper;

import com.sungcor.baobiao.report.bean.ReportGroupBean;
import com.sungcor.baobiao.report.bean.ReportTypeBean;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;


@Mapper
@Component
@Repository
public interface ReportGroupMapper {
    List<ReportTypeBean> findReportTypeList();

    List<ReportGroupBean> findReportGroupList();

    void addReportGroup(ReportGroupBean reportGroup);

    void deleteReportGroupById(String[] ids);

    void updateReportGroup(ReportGroupBean reportGroup);

    ReportGroupBean findReportGroupByName(String name);

    ReportGroupBean findReportGroupById(int id);

    List<Map> findModelNumByGroupId(String[] ids);
}

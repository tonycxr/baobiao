package com.sungcor.baobiao.report.mapper;

import com.sungcor.baobiao.report.bean.ReportChartModelBean;
import com.sungcor.baobiao.report.bean.ReportChartTypeBean;
import com.sungcor.baobiao.report.bean.ReportModelBean;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;

@Mapper
@Component
@Repository
public interface ReportChartMapper {
    public List<ReportChartTypeBean> queryTypeList();

    public List<ReportChartModelBean> queryChart(int modelId);

    public ReportChartModelBean queryChartById(int id);

    public void updateModel(ReportModelBean reportModel);

    public void insertList(List list);

    public void deleteChartByModelId(int modelId);
    public void deleteChartById(String[] ids);

    public int queryIsIndex(ReportChartModelBean chartModel);

    public void copyChartIndex(HashMap<String,Object> map);
}

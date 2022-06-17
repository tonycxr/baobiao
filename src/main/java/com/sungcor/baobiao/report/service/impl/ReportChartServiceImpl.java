package com.sungcor.baobiao.report.service.impl;


import com.sungcor.baobiao.report.bean.ReportChartModelBean;
import com.sungcor.baobiao.report.bean.ReportChartTypeBean;
import com.sungcor.baobiao.report.bean.ReportModelBean;
import com.sungcor.baobiao.report.mapper.ReportChartMapper;
import com.sungcor.baobiao.report.mapper.ReportTypeAttrMapper;
import com.sungcor.baobiao.report.service.IReportChartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by lenovo on 2016/8/4.
 */
@Transactional
@Service("IReportChartService")
public class ReportChartServiceImpl implements IReportChartService
{

    @Autowired
    private ReportChartMapper reportChartMapper;

    @Autowired
    private ReportTypeAttrMapper reportTypeAttrMapper;
    @Override
    public List<ReportChartTypeBean> queryTypeList()
    {
        return reportChartMapper.queryTypeList();
    }

    @Override
    public List<ReportChartModelBean> queryChart(int modelId)
    {
        List<ReportChartModelBean> chartModelList = reportChartMapper.queryChart(modelId);
        HashMap<String,Object> map = new HashMap<String, Object>();
        map.put("modelId",modelId);
        List<Map> list = reportTypeAttrMapper.findSelIndex(map);
        map.clear();
        for(Map m:list){
            map.put(m.get("id").toString(),m.get("name").toString());
        }
        for(ReportChartModelBean cm:chartModelList){
            rsetChart(cm,list);
        }
        return chartModelList;
    }

    @Override
    public ReportChartModelBean queryChartById(int id)
    {
        ReportChartModelBean chart = reportChartMapper.queryChartById(id);
        HashMap<String,Object> map = new HashMap<String, Object>();
        if(chart!=null){
            map.put("modelId",chart.getModelId());
            List<Map> list = reportTypeAttrMapper.findSelIndex(map);
            rsetChart(chart,list);
        }
        return chart;
    }

    public void rsetChart(ReportChartModelBean chart,List<Map> list){
        HashMap<String,Object> map = new HashMap<String, Object>();
        for(Map m:list){
            map.put(m.get("id").toString(),m);
        }
        String[] indexIds = chart.getIndexIds().split(";");
        String indexNames = "";
        String fieldNamefs = "";
        for(int i=0;i<indexIds.length;i++){
            if(map.containsKey(indexIds[i])){
                indexNames+=((Map)map.get(indexIds[i])).get("selIndexName").toString()+";";
                fieldNamefs+=((Map)map.get(indexIds[i])).get("fieldName").toString()+";";
            }else{
                chart.setIndexIds(chart.getIndexIds().replace(indexIds[i]+";",""));
            }
        }
        chart.setIndexNames(indexNames);
        chart.setFieldName(fieldNamefs);
    }

    @Override
    public void updateModel(ReportModelBean reportModel)
    {
        reportChartMapper.updateModel(reportModel);
    }

    @Override
    public void insertList(List<ReportChartModelBean> list)
    {
        if(list!=null&&list.size()>0){
            deleteChartByModelId(list.get(0).getModelId());
            reportChartMapper.insertList(list);
        }
    }

    @Override
    public void deleteChartByModelId(int modelId)
    {
        reportChartMapper.deleteChartByModelId(modelId);
    }

    @Override
    public void deleteChartById(String[] ids)
    {
        reportChartMapper.deleteChartById(ids);
    }

    @Override
    public int queryIsIndex(ReportChartModelBean chartModel)
    {
        return reportChartMapper.queryIsIndex(chartModel);
    }


}

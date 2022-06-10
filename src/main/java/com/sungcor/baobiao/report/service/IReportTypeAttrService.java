package com.sungcor.baobiao.report.service;

import com.sungcor.baobiao.report.bean.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public interface IReportTypeAttrService
{

    public List<ReportTypeAttributeBean> findAttrList(HashMap<String,Object> map);

    public List<Map> queryDim(HashMap<String,Object> map);

    public List<Map> queryIndex(HashMap<String,Object> map);

    public void addDimension(List<ReportDimensionDetBean> list);

    public void addIndex(List<ReportStatIndexBean> list);

    public void addStatDim(List<ReportStatDimensionBean> list);

    //根据模板获取维度
    public List<Map> findSelDimIndex(HashMap<String,Object> map);
    //根据模板获取指标
    public List<Map> findSelIndex(HashMap<String,Object> map);
    //组合报表获取子报表相关信息
    public List<Map> findGroupingReport(String modelId);

    public List<ReportStatDimensionBean> findDimByModelId(HashMap<String,Object> map);

    public void addChapter(List<ReportChapterBean> list, ReportChapterBean chapter);

    public void addCombination(List<ReportChapterBean> list);

    public List<ReportChapterBean> findModelChapter(int modelId);

    public List<ReportChapterBean> findChapterChild(int chapterId);
    public ReportChapterBean findChapter(int chapterId);

    public List<ReportModelBean> findModel(HashMap<String,Object> map);

    public void deleteModelChapter(int modelId);

    public void addTreeDimension(List<ReportTreeDimesnsionBean> list);

    public List<Map> findSelTreeDim(HashMap<String,Object> map);

    public void copyContext(int n_modelId,int o_modelId,int typeId);

    public void addTrendDim(HashMap<String,Object> map);

    public HashMap<String,Object> findSelTrendDim(HashMap<String,Object> map);
}

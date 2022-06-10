package com.sungcor.baobiao.report.mapper;

import com.sungcor.baobiao.report.bean.*;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Mapper
@Component
@Repository
public interface ReportTypeAttrMapper {
    public List<ReportTypeAttributeBean> findAttrList(HashMap<String,Object> map);

    public List<Map> querydimFromDict(HashMap<String,Object> map);

    public List<Map> querydimFromOrg(HashMap<String,Object> map);

    public List<Map> querydimFromArea(HashMap<String,Object> map);

    public List<Map> querydimFromRole(HashMap<String,Object> map);

    public List<Map> querydimFromUser(HashMap<String,Object> map);

    public List<Map> querydimFromRunLog(HashMap<String,Object> map);

    public List<Map> querydimFromBpm(HashMap<String,Object> map);

    public List<Map> querydimFromCustomer(HashMap<String,Object> map);

    public List<Map> querydimFromServiceCat(HashMap<String,Object> map);

    public List<Map> querydimFromCustomModel(HashMap<String,Object> map);

    public void addDimension(List<ReportDimensionDetBean> list);

    public void addIndex(List<ReportStatIndexBean> list);

    public void addStatDim(List<ReportStatDimensionBean> list);

    public List<Map> findSelDimIndex(HashMap<String,Object> map);

    public List<Map> findSelIndex(HashMap<String,Object> map);

    public List<Map> findGroupingReport(String modelId);

    public List<ReportStatDimensionBean> findDimByModelId(HashMap<String,Object> map);

    public void deteteStatDimByModelIds(String[] ids);
    public void deteteIndexByModelIds(String[] ids);
    public void deteteDimByModelIds(String[] ids);
    public void deleteChapterByModelIds(String[] ids);
    public void deleteCpChildByModelIds(String[] ids);

    public List<ReportChapterBean> findChapterCByModelIds(String[] ids);

    public int updateChapter(ReportChapterBean chapter);
    public int addChapter(ReportChapterBean bean);
    public void addChapterChild(List<ReportChapterBean> list);
    public void updateModelChapter(List<ReportChapterBean> list);
    public List<ReportModelBean> findModel(HashMap<String,Object> map);

    public List<ReportChapterBean> findModelChapter(int modelId);
    public List<ReportChapterBean> findChapterChild(int chapterId);
    public ReportChapterBean findChapter(int chapterId);

    public void deleteChapterChild(int chapterId);
    public void deleteModelChapter(int modelId);

    public void addTreeDimension(List<ReportTreeDimesnsionBean> list);
    public void deleteTreeDimensionByModelIds(String[] modelId);

    public List<Map> findSelTreeDim(HashMap<String,Object> map);
    public List<Map> findSelTreeDim_1(HashMap<String,Object> map);
    public void copyDim(HashMap<String,Object> map);
    public void copyIndex(HashMap<String,Object> map);
    public void copyStatDim(HashMap<String,Object> map);
    public void copyTreeDim(HashMap<String,Object> map);

    public void addTrendDim(HashMap<String,Object> map);

    public HashMap<String, Object> findSelTrendDim(HashMap<String, Object> map);
    public void deteteTrendDimByModelIds(String[] modelId);
    public void copyTrendDim(HashMap<String,Object> map);
}

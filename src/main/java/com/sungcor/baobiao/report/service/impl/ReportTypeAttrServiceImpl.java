package com.sungcor.baobiao.report.service.impl;

import com.sungcor.baobiao.report.bean.*;
import com.sungcor.baobiao.report.mapper.ReportChartMapper;
import com.sungcor.baobiao.report.mapper.ReportTypeAttrMapper;
import com.sungcor.baobiao.report.service.IReportTypeAttrService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by lenovo on 2016/5/27.
 */
@Transactional
@Service
public class ReportTypeAttrServiceImpl implements IReportTypeAttrService
{

    @Autowired
    private ReportTypeAttrMapper reportTypeAttrMapper;
    @Autowired
    private ReportChartMapper reportChartMapper;
    /**
     * 查询可选择的统计维度
     * @param map
     * @return
     */
    @Override
    public List<ReportTypeAttributeBean> findAttrList(HashMap<String, Object> map)
    {
        return reportTypeAttrMapper.findAttrList(map);
    }

    /**
     * 查询可选的统计维度指标
     * @param map
     * @return
     */
    public List<Map> queryDim(HashMap<String,Object> map){
        List<Map> list = null;
        if(StringUtils.isNumeric(map.get("attrId").toString())){
            list = reportTypeAttrMapper.querydimFromDict(map);
        }else{
            if(map.get("source").toString().equals("dict")){
                map.put("status","dimension");
                list = reportTypeAttrMapper.querydimFromDict(map);
            }else if(map.get("source").toString().equals("runLog")){
                list = reportTypeAttrMapper.querydimFromRunLog(map);
            }else if(map.get("source").toString().equals("bpm")||map.get("source").toString().equals("evaluation")){
                list = reportTypeAttrMapper.querydimFromBpm(map);
            }else if(map.get("source").toString().equals("customer")){
                list = reportTypeAttrMapper.querydimFromCustomer(map);
            }else if(map.get("source").toString().equals("serviceCat")){
                list = reportTypeAttrMapper.querydimFromServiceCat(map);
            }else if(map.get("source").toString().equals("organization")){
                list = reportTypeAttrMapper.querydimFromOrg(map);
            }else if(map.get("source").toString().equals("area")){
                list = reportTypeAttrMapper.querydimFromArea(map);
            }else if(map.get("source").toString().equals("role")){
                list = reportTypeAttrMapper.querydimFromRole(map);
            }else if(map.get("source").toString().equals("user")){
                list = reportTypeAttrMapper.querydimFromUser(map);
            }else if(map.get("source").toString().equals("customModel")){
                list = reportTypeAttrMapper.querydimFromCustomModel(map);
            }
        }

        return list;
    }

    /**
     * 查询可选的统计指标
     * @param map
     * @return
     */
    @Override
    public List<Map> queryIndex(HashMap<String,Object> map)
    {
        List<Map> list = null;
        if(map.get("source").toString().equals("dict")){
            map.put("status","index");
            list = reportTypeAttrMapper.querydimFromDict(map);
        }else if(map.get("source").toString().equals("runLog")){
            if(StringUtils.isNumeric(map.get("attrId").toString())){
                list = reportTypeAttrMapper.querydimFromDict(map);
            }else{
                list = reportTypeAttrMapper.querydimFromRunLog(map);
            }
        }else if(map.get("source").toString().equals("bpm")||map.get("source").toString().equals("evaluation")){
            if(StringUtils.isNumeric(map.get("attrId").toString())){
                list = reportTypeAttrMapper.querydimFromDict(map);
            }else
            {
                list = reportTypeAttrMapper.querydimFromBpm(map);
            }
        }else if(map.get("source").toString().equals("serviceCat")){
            list = reportTypeAttrMapper.querydimFromServiceCat(map);
        }else if(map.get("source").toString().equals("only")){
            list = new ArrayList<Map>();
//            List<Map> mapList = reportTypeAttrMapper.findSelIndex(map);
            map.put("s_id",map.get("attrId").toString());
            map.put("id","only_"+map.get("attrId").toString());
            list.add(map);
//            if(mapList!=null&&mapList.size()==0){
//                list.add(map);
//            }
        }else if(map.get("source").toString().equals("custom")){
            list = new ArrayList<Map>();
            map.put("s_id",map.get("attrId").toString());
            map.put("id","custom_"+map.get("attrId").toString());
            list.add(map);
        }else if(map.get("source").toString().equals("customModel")){
            list = reportTypeAttrMapper.querydimFromCustomModel(map);
        }
        return list;
    }

    /**
     * 保存添加的统计维度指标
     * @param list
     */
    @Override
    public void addDimension(List<ReportDimensionDetBean> list)
    {
        if(list!=null&&list.size()>0){
            String[] m = {String.valueOf(list.get(0).getModelId())};
            reportTypeAttrMapper.deteteDimByModelIds(m);
            reportTypeAttrMapper.addDimension(list);
        }
    }

    /**
     * 保存添加的统计指标
     * @param list
     */
    @Override
    public void addIndex(List<ReportStatIndexBean> list)
    {
        if(list!=null&&list.size()>0)
        {
            String[] m = {String.valueOf(list.get(0).getModelId())};
            reportTypeAttrMapper.deteteIndexByModelIds(m);
            reportTypeAttrMapper.addIndex(list);
        }
    }

    /**
     * 保存选择的统计维度
     * @param list
     */
    @Override
    public void addStatDim(List<ReportStatDimensionBean> list)
    {
        if(list!=null&&list.size()>0)
        {
            String[] m = {String.valueOf(list.get(0).getModelId())};
            reportTypeAttrMapper.deteteStatDimByModelIds(m);
            reportTypeAttrMapper.addStatDim(list);
        }
    }

    /**
     * 根据模板ID查询保存的统计维度指标
     * @param map
     * @return
     */
    @Override
    public List<Map> findSelDimIndex(HashMap<String, Object> map)
    {
        return reportTypeAttrMapper.findSelDimIndex(map);
    }

    /**
     * 根据模板ID查询保存的统计指标
     * @param map
     * @return
     */
    @Override
    public List<Map> findSelIndex(HashMap<String, Object> map)
    {
        return reportTypeAttrMapper.findSelIndex(map);
    }

    /**
     * 根据模板ID查询章节
     * @param modelId
     * @return
     */
    @Override
    public List<Map> findGroupingReport(String modelId) {
        return reportTypeAttrMapper.findGroupingReport(modelId);  //To change body of implemented methods use File | Settings | File Templates.
    }

    /**
     * 根据模板ID查询选择的统计维度
     * @param map
     * @return
     */
    @Override
    public List<ReportStatDimensionBean> findDimByModelId(HashMap<String, Object> map)
    {
        return reportTypeAttrMapper.findDimByModelId(map);
    }

    /**
     * 添加章节信息
     * @param list
     * @param chapter
     */
    @Override
    public void addChapter(List<ReportChapterBean> list,ReportChapterBean chapter)
    {
        if(chapter.getId()!=0){
            reportTypeAttrMapper.updateChapter(chapter);
        }else{
            reportTypeAttrMapper.addChapter(chapter);
        }
        if(list!=null&&list.size()>0){
            for(ReportChapterBean m:list){
                m.setId(chapter.getId());
            }
            addChapterChild(list);
        }
    }

    public void addChapterChild(List<ReportChapterBean> list){
        if(list!=null&&list.size()>0){
//            String[] m = {String.valueOf(list.get(0).getId())};
            reportTypeAttrMapper.deleteChapterChild(list.get(0).getId());
            reportTypeAttrMapper.addChapterChild(list);
        }
    }
    /**
     * 更新章节排序
     * @param list
     */
    @Override
    public void addCombination(List<ReportChapterBean> list){
        if(list!=null&&list.size()>0){
            reportTypeAttrMapper.updateModelChapter(list);
        }
    }
    /**
     * 根据模板ID查询章节
     * @param modelId
     * @return
     */
    @Override
    public List<ReportChapterBean> findModelChapter(int modelId){
        return reportTypeAttrMapper.findModelChapter(modelId);
    }
    /**
     * 根据章节ID查询子模版
     * @param chapterId
     * @return
     */
    @Override
    public List<ReportChapterBean> findChapterChild(int chapterId){
        return reportTypeAttrMapper.findChapterChild(chapterId);
    }

    /**
     * 根据报表分组查询模板
     * @param map
     * @return
     */
    @Override
    public List<ReportModelBean> findModel(HashMap<String,Object> map)
    {
        return reportTypeAttrMapper.findModel(map);
    }

    /**
     * 删除组合报表的章节信息
     * @param chapterId
     */
    @Override
    public void deleteModelChapter(int chapterId)
    {
        reportTypeAttrMapper.deleteChapterChild(chapterId);
        reportTypeAttrMapper.deleteModelChapter(chapterId);
    }

    /**
     * 添加树形分组维度数据
     * @param list
     */
    @Override
    public void addTreeDimension(List<ReportTreeDimesnsionBean> list)
    {
        if(list!=null&&list.size()>0){
            String[] m = {String.valueOf(list.get(0).getModelId())};
            reportTypeAttrMapper.deleteTreeDimensionByModelIds(m);
            reportTypeAttrMapper.addTreeDimension(list);
        }
    }

    @Override
    public List<Map>findSelTreeDim(HashMap<String, Object> map)
    {
        String sql1 = "";
        String sql2 = "";
        String sql3 = "";
        if(map.get("level").toString().equals("1")){
            sql1 = "t.SELECT_INDEX_ID ";
            sql1 = " order by t.sort";
        }else if(map.get("level").toString().equals("2")){
//            sql1 = "if(t1.SELECT_INDEX_ID is not null,t1.SELECT_INDEX_ID,t.SELECT_INDEX_ID) ";
            sql1 = "CASE WHEN t1.SELECT_INDEX_ID is not null THEN t1.SELECT_INDEX_ID ELSE t.SELECT_INDEX_ID END ";
//            sql2 = "if(t1.SELECT_INDEX_NAME is not null,t1.SELECT_INDEX_NAME,t.SELECT_INDEX_NAME) NAME1,";
            sql2 = "CASE WHEN t1.SELECT_INDEX_NAME is not null THEN t1.SELECT_INDEX_NAME ELSE t.SELECT_INDEX_NAME END \"name1\",";
            sql3 = "left join REPORT_TREE_DIMENSION_T t1 on (t.SELECT_INDEX_ID = t1.PARENT_INDEX_ID or t.SELECT_INDEX_ID = t1.SELECT_INDEX_ID) and t1" +
                    ".model_id = "+map.get("modelId").toString()+" order by t.sort,t1.sort desc";

        }else if(map.get("level").toString().equals("3")){
//            sql1 = "if(t2.SELECT_INDEX_ID is not null,t2.SELECT_INDEX_ID, if(t1.SELECT_INDEX_ID is not null,t1" +
//                    ".SELECT_INDEX_ID,t.SELECT_INDEX_ID)) ";
            sql1 = "CASE WHEN t2.SELECT_INDEX_ID is not null THEN t2.SELECT_INDEX_ID " +
                    "WHEN t1.SELECT_INDEX_ID is not null THEN t1.SELECT_INDEX_ID ELSE t.SELECT_INDEX_ID END ";
//            sql2 = "if(t1.SELECT_INDEX_NAME is not null,t1.SELECT_INDEX_NAME,t.SELECT_INDEX_NAME) NAME1," +
//                    "if(t2.SELECT_INDEX_NAME is not null,t2.SELECT_INDEX_NAME,if(t1.SELECT_INDEX_NAME is not null,t1.SELECT_INDEX_NAME,t.SELECT_INDEX_NAME)) NAME2,";
            sql2 = "CASE WHEN t1.SELECT_INDEX_NAME is not null THEN t1.SELECT_INDEX_NAME ELSE t.SELECT_INDEX_NAME END \"name1\"," +
                    "CASE WHEN t2.SELECT_INDEX_NAME is not null THEN t2.SELECT_INDEX_NAME " +
                    "WHEN t1.SELECT_INDEX_NAME is not null THEN t1.SELECT_INDEX_NAME ELSE t.SELECT_INDEX_NAME END \"name2\",";
            sql3 = "left join REPORT_TREE_DIMENSION_T t1 on (t.SELECT_INDEX_ID = t1.PARENT_INDEX_ID or t.SELECT_INDEX_ID = t1.SELECT_INDEX_ID) and t1" +
                    ".model_id = "+map.get("modelId").toString()+" ";
            sql3 += "left join REPORT_TREE_DIMENSION_T t2 on (t1.SELECT_INDEX_ID = t2.PARENT_INDEX_ID or t1" +
                    ".SELECT_INDEX_ID = t2.SELECT_INDEX_ID) and t2.model_id = "+map.get("modelId").toString() +" " +
                    "order by t.sort,t1.sort desc,t2.sort desc";

        }
        map.put("sql1",sql1);
        map.put("sql2",sql2);
        map.put("sql3",sql3);
        List<Map> list = reportTypeAttrMapper.findSelTreeDim(map);
        List<Map> list2 = reportTypeAttrMapper.findSelTreeDim_1(map);
        list.addAll(list2);
        return list;
    }

    /**
     * 根据章节ID查询章节信息
     * @param chapterId
     * @return
     */
    @Override
    public ReportChapterBean findChapter(int chapterId){
        return reportTypeAttrMapper.findChapter(chapterId);
    }

    public void copyContext(int n_modelId,int o_modelId,int typeId){
        HashMap<String,Object> map = new HashMap<String, Object>();
        map.put("n_modelId",n_modelId);
        map.put("o_modelId",o_modelId);
        map.put("typeId",typeId);
        if(typeId==5){  //分组报表
            reportTypeAttrMapper.copyTreeDim(map);
            reportTypeAttrMapper.copyIndex(map);
            reportTypeAttrMapper.copyStatDim(map);
            reportChartMapper.copyChartIndex(map);
        }else if(typeId==6){ //组合报表
            List<ReportChapterBean> list = findModelChapter(o_modelId);
            for(ReportChapterBean chapterBean:list){
                chapterBean.setModelId(n_modelId);
                List<ReportChapterBean> listchild = findChapterChild(chapterBean.getId());
                reportTypeAttrMapper.addChapter(chapterBean);
                if(listchild!=null&&listchild.size()>0){
                    for(ReportChapterBean mt:listchild){
                        mt.setId(chapterBean.getId());
                    }
                    addChapterChild(listchild);
                }
            }
        }else if(typeId==7){
            reportTypeAttrMapper.copyTrendDim(map);
            reportTypeAttrMapper.copyIndex(map);
            reportChartMapper.copyChartIndex(map);
        }else{
            reportTypeAttrMapper.copyDim(map);
            reportTypeAttrMapper.copyIndex(map);
            reportTypeAttrMapper.copyStatDim(map);
            reportChartMapper.copyChartIndex(map);
        }
    }

    /**
     * 添加趋势维度数据
     */
    @Override
    public void addTrendDim(HashMap<String,Object> map){
        String[] m = {String.valueOf(map.get("modelId").toString())};
        reportTypeAttrMapper.deteteTrendDimByModelIds(m);
        reportTypeAttrMapper.addTrendDim(map);
    }

    @Override
    public HashMap<String, Object> findSelTrendDim(HashMap<String, Object> map)
    {
        return reportTypeAttrMapper.findSelTrendDim(map);
    }
}

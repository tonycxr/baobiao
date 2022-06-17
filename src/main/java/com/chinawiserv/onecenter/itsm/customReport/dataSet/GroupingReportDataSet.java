package com.chinawiserv.onecenter.itsm.customReport.dataSet;

import com.runqian.report4.dataset.DataSet;
import com.runqian.report4.dataset.IDataSetFactory;
import com.runqian.report4.dataset.Row;
import com.runqian.report4.usermodel.Context;
import com.runqian.report4.usermodel.DataSetConfig;
import com.sungcor.baobiao.report.bean.ReportChartModelBean;
import com.sungcor.baobiao.report.bean.ReportCustomIndexBean;
import com.sungcor.baobiao.report.bean.ReportModelBean;
import com.sungcor.baobiao.report.service.*;
import com.sungcor.baobiao.report.util.CommReportUtil;
import com.sungcor.baobiao.utils.ReportUtil;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by lenovo on 2016/6/16.
 */
@Service
public class GroupingReportDataSet extends ReportUtil implements IDataSetFactory
{
    @Autowired
    public IReportModelService reportModelService;
    @Autowired
    public IReportService reportService;
    @Autowired
    public IReportTypeAttrService reportTypeAttrService;
    @Autowired
    public IReportTaskService reportTaskService;
    @Autowired
    public IReportChartService reportChartService;
    @Autowired
    private IReportCustomIndexService reportCustomIndexService;

    @Override
    public DataSet createDataSet(Context context, DataSetConfig dataSetConfig, boolean b){
        //获取参数map
        Map map = context.getParamMap(false);

        //获取任务id，如果任务Id为空，表示是查询，否则根据任务Id获取参数
        String taskId = map.get("taskId") == null ? "" : map.get("taskId").toString();
        //模板Id
        String modelId = map.get("modelId") == null ? "" : map.get("modelId").toString();
        //当前用户Id
        String userId = map.get("userId") == null ? "" : map.get("userId").toString();
        String showTable = map.get("showTable") == null ? "1" : map.get("showTable").toString();
        String showChart = map.get("showChart") == null ? "0" : map.get("showChart").toString();
        DataSet ds1 = new DataSet("ds1");
        //维度Id
        ds1.addCol("dimensionId");
        //指标Id
        ds1.addCol("statIndexId");
        ds1.addCol("name");
        ds1.addCol("type");
        ds1.addCol("pname");
        ds1.addCol("chartId");
        ds1.addCol("chartType");
        ds1.addCol("chartTitle");

        ReportModelBean modelBean= reportModelService.findBeanById(modelId);
        if(modelBean.getTypeId()==5){
            //获取报表的参数
            List<HashMap> queryList = new ArrayList<HashMap>();
            if ("".equals(taskId)) {
                //当前为报表查询，直接取缓存
                queryList = reportService.findReportModelQuery(null, modelId, userId, "query",1);
            } else {
                //当前为报表任务，根据taskId获取
                queryList = reportTaskService.findTaskModelQuery(taskId);
            }

            HashMap<String, Object> modelMap = new HashMap<String, Object>();
            modelMap.put("modelId", modelId);
            modelMap.put("level", 2);
            modelMap.put("value","-1");
            for(HashMap m:queryList){
                if(m.get("source")!=null && "organization".equals(m.get("source").toString())){
                    modelMap.put("source",m.get("source").toString());
                    modelMap.put("fieldName",m.get("fieldName").toString());
                    modelMap.put("value",m.get("fieldValue").toString());
                }
            }
            //获取维度
            List<Map> reportStatDimensionBeanList = reportTypeAttrService.findSelTreeDim(modelMap);
            //获取指标
            List<Map> reportStatIndexBeanList = reportTypeAttrService.findSelIndex(modelMap);


            //拼装维度所需数据
            List<HashMap> dimensionList = new ArrayList<HashMap>();
            //拼装指标所需数据
            List<HashMap> statIndexList = new ArrayList<HashMap>();
            for (int i = 0; i < reportStatDimensionBeanList.size(); i++) {
                Row rr = ds1.addRow();
                HashMap dimensionMap = new HashMap();
                String id = reportStatDimensionBeanList.get(i).get("id").toString();
                String source = reportStatDimensionBeanList.get(i).get("source").toString();
                rr.setData(1, source + "_" + id);
                rr.setData(3, reportStatDimensionBeanList.get(i).get("name1"));
                rr.setData(4, "1");
                rr.setData(5, reportStatDimensionBeanList.get(i).get("name").toString());
                dimensionMap.put("id", id);
                dimensionMap.put("fieldName", reportStatDimensionBeanList.get(i).get("fieldName"));
                dimensionMap.put("source", source);
                dimensionList.add(dimensionMap);
            }
            List<HashMap> customIndexList = new ArrayList<HashMap>();
            for (int i = 0; i < reportStatIndexBeanList.size(); i++) {
                Row rr = ds1.addRow();
                String id = reportStatIndexBeanList.get(i).get("s_id").toString();
                String source = reportStatIndexBeanList.get(i).get("source").toString();
                rr.setData(2, source + "_" + id);
                rr.setData(3, reportStatIndexBeanList.get(i).get("selIndexName"));
                rr.setData(4, "2");
                if(source.equals("custom")){//自定义指标部分
                    ReportCustomIndexBean customIndex = reportCustomIndexService.getById(Integer.parseInt(id));
                    String expressionIndex = customIndex.getExpressionIndex();
                    String expressionField = customIndex.getExpressionField();
                    HashMap cusMap = new HashMap();
                    cusMap.put("id",source + "_" + id);
                    cusMap.put("exp",expressionIndex);
                    cusMap.put("suffix",customIndex.getSuffix());
                    customIndexList.add(cusMap);
                    List<String> indexList = CommReportUtil.getIndexByExp(expressionIndex);
                    String[] fieldList = expressionField.split(",");
                    for(int x=0;x<indexList.size();x++){
                        HashMap statIndexMap = new HashMap();
                        statIndexMap.put("id", indexList.get(x).split("_")[1]);
                        statIndexMap.put("fieldName", fieldList[x]);
                        statIndexMap.put("source", indexList.get(x).split("_")[0]);
                        statIndexList.add(statIndexMap);
                    }
                }else{//非自定义指标部分
                    HashMap statIndexMap = new HashMap();
                    statIndexMap.put("id", id);
                    statIndexMap.put("fieldName", reportStatIndexBeanList.get(i).get("fieldName"));
                    statIndexMap.put("source", source);
                    statIndexList.add(statIndexMap);
                }
            }

            //初始化数据域
            reportService.findReportDataSet(dimensionList, statIndexList, queryList, modelId, userId,customIndexList);
            //图形部分
            if(showChart.equals("1")){
                List<ReportChartModelBean> chartModelList= reportChartService.queryChart(Integer.valueOf(modelId));
                for (int i = 0; i < chartModelList.size(); i++)
                {
                    Row rr = ds1.addRow();
                    rr.setData(6, chartModelList.get(i).getId());
                    rr.setData(4, "3");
                    rr.setData(7, chartModelList.get(i).getTypeId());
                    rr.setData(8, StringUtils.isBlank(chartModelList.get(i).getTitle())?" ":chartModelList.get(i).getTitle());
                }
            }
        }
        return ds1;  //To change body of implemented methods use File | Settings | File Templates.
    }
}

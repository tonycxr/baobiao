package com.sungcor.baobiao.entity.dataSet;

import com.runqian.report4.dataset.DataSet;
import com.runqian.report4.dataset.IDataSetFactory;
import com.runqian.report4.dataset.Row;
import com.runqian.report4.usermodel.Context;
import com.runqian.report4.usermodel.DataSetConfig;
import com.sungcor.baobiao.STSMApplicationContext;
import com.sungcor.baobiao.report.bean.ReportChartModelBean;
import com.sungcor.baobiao.report.bean.ReportCustomIndexBean;
import com.sungcor.baobiao.report.bean.ReportModelBean;
import com.sungcor.baobiao.report.service.*;
import com.sungcor.baobiao.report.util.CommReportUtil;
import com.sungcor.baobiao.report.util.FindDates;
import com.sungcor.baobiao.utils.ReportUtil;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.*;

/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 * Date: 16-5-30
 * Time: 上午10:53
 * To change this template use File | Settings | File Templates.
 */
public class TrendReportDataSet extends ReportUtil implements IDataSetFactory {

    @Autowired
    private IReportModelService reportModelService;
    @Autowired
    private IReportService reportService;
    @Autowired
    private IReportTypeAttrService reportTypeAttrService;
    @Autowired
    private IReportTaskService reportTaskService;
    @Autowired
    private IReportChartService reportChartService;
    @Autowired
    private IReportCustomIndexService reportCustomIndexService;


    @Override
    public DataSet createDataSet(Context context, DataSetConfig dataSetConfig, boolean b) {

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
        ds1.addCol("chartId");
        ds1.addCol("chartType");
        ds1.addCol("chartTitle");
        ReportModelBean modelBean= reportModelService.findBeanById(modelId);
        if(modelBean.getTypeId()==7){
            //获取报表的参数
            List<HashMap> queryList = new ArrayList<HashMap>();
            if ("".equals(taskId)) {
                //当前为报表查询，直接取缓存
                queryList = reportService.findReportModelQuery(null, modelId, userId, "query",1);
            } else {
                //当前为报表任务，根据taskId获取
                queryList = reportTaskService.findTaskModelQuery(taskId);
            }

            Row rt = ds1.addRow();
            rt.setData(4, "0");
            HashMap<String, Object> modelMap = new HashMap<String, Object>();
            modelMap.put("modelId", modelId);
            //获取维度
            //获取指标
            List<Map> reportDimList = new ArrayList<Map>();
            List<Map> reportStatIndexBeanList = reportTypeAttrService.findSelIndex(modelMap);
            String dBegin = "";
            String dEnd = "";
            HashMap<String,Object> dimMap = reportTypeAttrService.findSelTrendDim(modelMap);
            String timeType = dimMap.get("trendType").toString();
            for(int i=0;i<queryList.size();i++){
                HashMap qmap = queryList.get(i);
                String fieldName = qmap.get("fieldName").toString();
                String source = qmap.get("source")==null?"":qmap.get("source").toString();
                if("".equals(source)){
                    if("beginTime".equals(fieldName)){
                        dBegin = qmap.get("fieldValue").toString();
                    }if("endTime".equals(fieldName)){
                        dEnd = qmap.get("fieldValue").toString();
                    }if("datePurview".equals(fieldName)){
                        HashMap<String, String> timeMap = reportService.getQueryDate(qmap.get("fieldValue").toString());
                        dBegin = timeMap.get("beginTime");
                        dEnd = timeMap.get("endTime");
                    }
                }
            }
            reportDimList = FindDates.findDates(dBegin,dEnd,timeType);
            //拼装维度所需数据
            List<HashMap> dimensionList = new ArrayList<HashMap>();
            //拼装指标所需数据
            List<HashMap> statIndexList = new ArrayList<HashMap>(new HashSet<HashMap>());
            String dbtype = STSMApplicationContext.getProperty("stsm.dbtype");
            for (int i = 0; i < reportDimList.size(); i++) {
                Row rr = ds1.addRow();

                String id = reportDimList.get(i).get("id").toString();
                String source = "trend";
                rr.setData(1, source + "_" + id);
                rr.setData(3, reportDimList.get(i).get("name").toString());
                rr.setData(4, "1");

                HashMap dimensionMap = new HashMap();
                dimensionMap.put("id", id);
                dimensionMap.put("fieldName", FindDates.findFormat(timeType).get(dbtype));
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
                    rr.setData(5, chartModelList.get(i).getId());
                    rr.setData(4, "3");
                    rr.setData(6, chartModelList.get(i).getTypeId());
                    rr.setData(7, StringUtils.isBlank(chartModelList.get(i).getTitle())?" ":chartModelList.get(i).getTitle());
                }
            }
        }

        return ds1;  //To change body of implemented methods use File | Settings | File Templates.
    }
}

package com.sungcor.baobiao.entity.dataSet;


import com.runqian.report4.dataset.DataSet;
import com.runqian.report4.dataset.IDataSetFactory;
import com.runqian.report4.dataset.Row;
import com.runqian.report4.usermodel.Context;
import com.runqian.report4.usermodel.DataSetConfig;
import com.sungcor.baobiao.STSMApplicationContext;
import com.sungcor.baobiao.report.bean.ReportChartModelBean;
import com.sungcor.baobiao.report.bean.ReportCustomIndexBean;
import com.sungcor.baobiao.report.service.*;
import com.sungcor.baobiao.report.util.CommReportUtil;
import com.sungcor.baobiao.report.util.FindDates;
import com.sungcor.baobiao.utils.ReportUtil;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.*;

/**
 * Created by lenovo on 2016/8/5.
 */
public class ChartDataSet extends ReportUtil implements IDataSetFactory
{
    @Autowired
    private IReportService reportService;
    @Autowired
    private IReportTypeAttrService reportTypeAttrService;
    @Autowired
    private IReportChartService reportChartService;
    @Autowired
    private IReportCustomIndexService reportCustomIndexService;
    @Autowired
    private IReportTaskService reportTaskService;
    @Override
    public DataSet createDataSet(Context context, DataSetConfig dataSetConfig, boolean b) {

        //获取参数map
        Map map = context.getParamMap(false);
        boolean isTree = map.get("isTree")==null?false:true;
        String taskId = map.get("taskId") == null ? "" : map.get("taskId").toString();
        //模板Id
        String modelId = map.get("modelId") == null ? "" : map.get("modelId").toString();
        //当前用户Id
        String userId = map.get("userId") == null ? "" : map.get("userId").toString();

        String chartId = map.get("chartId") == null ? "0" : map.get("chartId").toString();
        String chartType = map.get("chartType") == null ? "" : map.get("chartType").toString();
        isTree = chartType.equals("3")?false:isTree;
        int cellNum = map.get("cellNum") == null ? 1 : Integer.parseInt(map.get("cellNum").toString());
//        cellNum = isTree?cellNum+2:cellNum;
        //获取报表的参数
        List<HashMap> queryList = new ArrayList<HashMap>();
        if ("".equals(taskId)) {
            //当前为报表查询，直接取缓存
            queryList = reportService.findReportModelQuery(null, modelId, userId, "query",1);
        } else {
            //当前为报表任务，根据taskId获取
            queryList = reportTaskService.findTaskModelQuery(taskId);
        }
        DataSet ds1;
        HashMap<String, Object> modelMap = new HashMap<String, Object>();
        modelMap.put("modelId", modelId);
        List<Map> reportStatDimensionBeanList = new ArrayList<Map>();
        //获取维度
        if(chartType.equals("3")){
            HashMap<String,Object> dimMap = reportTypeAttrService.findSelTrendDim(modelMap);
            String timeType = dimMap.get("trendType").toString();
            String dBegin = "";
            String dEnd = "";
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
            List<Map> reportDimList = FindDates.findDates(dBegin,dEnd,timeType);
            String dbtype = STSMApplicationContext.getProperty("stsm.dbtype");
            for (int i = 0; i < reportDimList.size(); i++) {
                String id = reportDimList.get(i).get("id").toString();
                String source = "trend";
                HashMap dimensionMap = new HashMap();
                dimensionMap.put("id", id);
                dimensionMap.put("s_id", id);
                dimensionMap.put("name", reportDimList.get(i).get("name").toString());
                dimensionMap.put("fieldName", FindDates.findFormat(timeType).get(dbtype));
                dimensionMap.put("source", source);
                reportStatDimensionBeanList.add(dimensionMap);
            }
        }else{
            if(!isTree){
                reportStatDimensionBeanList = reportTypeAttrService.findSelDimIndex(modelMap);
            }else{
                modelMap.put("level", 2);
                modelMap.put("value","-1");
                for(HashMap m:queryList){
                    if(m.get("source")!=null && "organization".equals(m.get("source").toString())){
                        modelMap.put("source",m.get("source").toString());
                        modelMap.put("fieldName",m.get("fieldName").toString());
                        modelMap.put("value",m.get("fieldValue").toString());
                    }
                }
                reportStatDimensionBeanList = reportTypeAttrService.findSelTreeDim(modelMap);
            }
        }

        ReportChartModelBean chartModel= reportChartService.queryChartById(Integer.valueOf(chartId));
        //拼装维度所需数据
        List<HashMap> dimensionList = new ArrayList<HashMap>();
        //拼装指标所需数据
        List<HashMap> statIndexList = new ArrayList<HashMap>();

        HashMap<String,Map<String,String>> dimMap = new HashMap<String,Map<String,String>>();
        HashMap<String,String> satMap = new HashMap<String,String>();
        List<HashMap> customIndexList = new ArrayList<HashMap>();
        if(chartModel!=null){
            for (int i = 0; i < reportStatDimensionBeanList.size(); i++) {

                HashMap dimensionMap = new HashMap();
                String id = "";
                String source = reportStatDimensionBeanList.get(i).get("source").toString();
                HashMap<String,String> dm = new  HashMap<String,String>();
                dm.put("sort",i+"");
                if(!isTree){
                    id = reportStatDimensionBeanList.get(i).get("s_id").toString();
                    dm.put("name",reportStatDimensionBeanList.get(i).get("name").toString());
                    dimMap.put(source + "_" + id,dm);
                }else{
                    id = reportStatDimensionBeanList.get(i).get("id").toString();
                    try
                    {
                        dm.put("name", reportStatDimensionBeanList.get(i).get("name1").toString());
                    }catch(Exception e){
                        e.printStackTrace();
                    }
                    dimMap.put(source + "_" + id,dm);
                }
                dimensionMap.put("id", id);
                dimensionMap.put("fieldName", reportStatDimensionBeanList.get(i).get("fieldName"));
                dimensionMap.put("source", source);
                dimensionList.add(dimensionMap);


                String[] indexIds = chartModel.getIndexIds().split(";");
                String[] indexNames = chartModel.getIndexNames().split(";");
                String[] fieldNames = chartModel.getFieldName().split(";");
                for (int k = 0; k < indexIds.length; k++)
                {
                    if(statIndexList.size()<indexIds.length){
                        satMap.put(indexIds[k],indexNames[k]);
                        if(indexIds[k].split("_")[0].equals("custom")){//自定义指标部分
                            ReportCustomIndexBean customIndex = reportCustomIndexService.getById(Integer.parseInt(indexIds[k].split("_")[1]));
                            String expressionIndex = customIndex.getExpressionIndex();
                            String expressionField = customIndex.getExpressionField();
                            HashMap cusMap = new HashMap();
                            cusMap.put("id",indexIds[k].split("_")[0] + "_" + customIndex.getId());
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
                            statIndexMap.put("id", indexIds[k].split("_")[1]);
                            statIndexMap.put("fieldName", fieldNames[k]);
                            statIndexMap.put("source", indexIds[k].split("_")[0]);
                            statIndexList.add(statIndexMap);
                        }
                    }
                }
            }
        }
        ds1 = reportService.findReportDataSet(dimensionList, statIndexList, queryList, modelId, userId,customIndexList);
        //图形缺失数据填充
        HashMap<String,Map<String,String>> copydimMap = new  HashMap<String,Map<String,String>>();
        copydimMap.putAll(dimMap);
        HashMap<String,String> copysatMap = new HashMap<String,String>();
        HashMap<String,String> t_copysatMap = new HashMap<String,String>();
        copysatMap.putAll(satMap);
        DataSet ds2 = new DataSet("ds1");
        //维度Id
        ds2.addCol("dimensionId");
        //指标Id
        ds2.addCol("statIndexId");
        ds2.addCol("type");
        ds2.addCol("value");
        ds2.addCol("dimensionName");
        ds2.addCol("statIndexName");
        ds2.addCol("dimsort");
        ds2.addCol("cellNum");
        for(int i=1;i<=ds1.getRowCount();i++){
            if(satMap.get(ds1.getRowData(i)[1])!=null&&dimMap.get(ds1.getRowData(i)[0])!=null){
                Row rr = ds2.addRow();
                rr.setData(1,ds1.getRowData(i)[0]);
                rr.setData(2,ds1.getRowData(i)[1]);
                rr.setData(3,ds1.getRowData(i)[2]);
                rr.setData(4,ds1.getRowData(i)[3]==null?0:ds1.getRowData(i)[3]);
                rr.setData(5,dimMap.get(ds1.getRowData(i)[0]).get("name"));
                rr.setData(6,satMap.get(ds1.getRowData(i)[1]));
                rr.setData(7,dimMap.get(ds1.getRowData(i)[0]).get("sort"));
                t_copysatMap.put(ds1.getRowData(i)[1].toString(),satMap.get(ds1.getRowData(i)[1]));
                if(!chartType.equals("3")){
                    copydimMap.remove(ds1.getRowData(i)[0]);
                }
            }
        }

        for(String key:t_copysatMap.keySet()){
            copysatMap.remove(key);
        }
      if(chartType.equals("3")){
          Set<String> dimkeySet = copydimMap.keySet();
          for(String dimkey:dimkeySet){
              for(String satkey:copysatMap.keySet()){
                  Row rr = ds2.addRow();
                  rr.setData(1,dimkey);
                  rr.setData(2,satkey);
                  rr.setData(3,"3");
                  rr.setData(4,0);
                  rr.setData(5,copydimMap.get(dimkey).get("name"));
                  rr.setData(6,copysatMap.get(satkey));
                  rr.setData(7,copydimMap.get(dimkey).get("sort"));
              }
          }
      }else{
            Set<String> dimkeySet = copydimMap.keySet();
            for(String dimkey:dimkeySet){
                for(String satkey:satMap.keySet()){
                    Row rr = ds2.addRow();
                    rr.setData(1,dimkey);
                    rr.setData(2,satkey);
                    rr.setData(3,"3");
                    rr.setData(4,0);
                    rr.setData(5,copydimMap.get(dimkey).get("name"));
                    rr.setData(6,satMap.get(satkey));
                    rr.setData(7,copydimMap.get(dimkey).get("sort"));
                }
            }
      }

        for (int i = 0; i < cellNum-1; i++)
        {
            Row rr = ds2.addRow();
            rr.setData(3,"6");
            rr.setData(8,i);
        }

        return ds2;  //To change body of implemented methods use File | Settings | File Templates.
    }
}


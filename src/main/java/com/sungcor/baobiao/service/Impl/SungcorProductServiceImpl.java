package com.sungcor.baobiao.service.Impl;

import com.alibaba.fastjson.JSONObject;
import com.runqian.report4.cache.ReportEntry;
import com.runqian.report4.dataset.DataSet;
import com.runqian.report4.dataset.Row;

import com.runqian.report4.model.ReportDefine;
import com.runqian.report4.usermodel.Context;
import com.runqian.report4.usermodel.Engine;
import com.runqian.report4.usermodel.IReport;
import com.runqian.report4.util.ReportUtils;
import com.runqian.report4.util.ReportUtils2;
import com.sungcor.baobiao.entity.SungcorProduct;
import com.sungcor.baobiao.mapper.SungcorProductMapper;
import com.sungcor.baobiao.report.bean.ReportChartModelBean;
import com.sungcor.baobiao.report.bean.ReportCustomIndexBean;
import com.sungcor.baobiao.report.bean.ReportModelBean;
import com.sungcor.baobiao.report.bean.ReportTaskBean;
import com.sungcor.baobiao.report.service.*;
import com.sungcor.baobiao.report.util.CommReportUtil;
import com.sungcor.baobiao.report.util.JobUtil;
import com.sungcor.baobiao.service.ISungcorProductService;

import com.sungcor.baobiao.utils.DateUtil;
import com.sungcor.baobiao.utils.HttpUtil;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.Principal;
import java.util.*;

import static com.sungcor.baobiao.utils.FileUtil.readFile;

@Transactional
@Service
public class SungcorProductServiceImpl implements ISungcorProductService {
    @Autowired
    private SungcorProductMapper sungcorProductMapper;

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
    public SungcorProduct getSungcorProduct(String productName) {
        return sungcorProductMapper.getSungcorProduct(productName);
    }

    @Override
    public Integer getProductProfit(Map map){
        SungcorProduct target = sungcorProductMapper.getSungcorProduct(map.get("productName").toString());
        try{
            Integer seasonNumber = (Integer) map.get("seasonNumber");
            switch (seasonNumber) {
                case 1:
                    return target.getFirstSeason();
                case 2:
                    return target.getSecondSeason();
                case 3:
                    return target.getThirdSeason();
                case 4:
                    return target.getFourthSeason();
            }
        }catch (Exception e){
            e.printStackTrace();
            return 0;
        }
        return 0;

    }

    @Override
    public IReport getTheRaq(String path) throws Exception{

        File file =new File(path);
        String taskId = "cxrTest";
        //报表模板ID
        Integer modelId = 1;
        //获取模板信息
        ReportModelBean reportModelBean = reportModelService.findBeanById(String.valueOf(modelId));
        Context cxt = new Context();
        cxt.setParamValue("modelId",modelId);
        cxt.setParamValue("userId","admin");
        cxt.setParamValue("taskId",taskId);
        cxt.setParamValue("reportName",reportModelBean.getReportName());
        cxt.setParamValue("showTable",reportModelBean.getShowTable()+"");
        cxt.setParamValue("showChart",reportModelBean.getShowChart()+"");
        cxt.setParamValue("skin","/css/default");
        ReportDefine rd = (ReportDefine) ReportUtils.read(String.valueOf(file));
        String beginTime = DateUtil.getCurrentTime("yyyy-MM-dd hh:mm:ss");
        String endTime =  DateUtil.parse(DateUtil.getNowWeekSunday(new Date()),"yyyy-MM-dd hh:mm:ss");
        String reportTime = "统计时间:"+ beginTime+"至"+endTime;
        cxt.setParamValue("reportTime",reportTime);

        Engine engine = new Engine(rd, cxt); //构造报表引擎
        IReport iReport = engine.calc(); //运算报表
        return iReport;
    }

    @Override
    public DataSet createDataSet(Map map) {

        //获取任务id，如果任务Id为空，表示是查询，否则根据任务Id获取参数
        String taskId = map.get("taskId") == null ? "" : map.get("taskId").toString();
        //模板Id
        String modelId = map.get("modelId") == null ? "" : map.get("modelId").toString();
        //当前用户Id
        String userId = map.get("userId") == null ? "" : map.get("userId").toString();
//        String showTable = map.get("showTable") == null ? "1" : map.get("showTable").toString();
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
        //获取报表的参数
        List<HashMap> queryList = new ArrayList<HashMap>();
        if ("".equals(taskId)) {
            //当前为报表查询，直接取缓存
            queryList = reportService.findReportModelQuery(null, modelId, userId, "query", 1);
        } else {
            //当前为报表任务，根据taskId获取
            queryList = reportTaskService.findTaskModelQuery(taskId);
        }

        Row rt = ds1.addRow();
        rt.setData(4, "0");
        HashMap<String, Object> modelMap = new HashMap<String, Object>();
        modelMap.put("modelId", modelId);
        //获取维度
        List<Map> reportStatDimensionBeanList = reportTypeAttrService.findSelDimIndex(modelMap);
        //获取指标
        List<Map> reportStatIndexBeanList = reportTypeAttrService.findSelIndex(modelMap);


        //拼装维度所需数据
        List<HashMap> dimensionList = new ArrayList<HashMap>();
        //拼装指标所需数据
        List<HashMap> statIndexList = new ArrayList<HashMap>(new HashSet<HashMap>());
        for (int i = 0; i < reportStatDimensionBeanList.size(); i++) {
            Row rr = ds1.addRow();
            HashMap dimensionMap = new HashMap();
            String id = reportStatDimensionBeanList.get(i).get("s_id").toString();
            String source = reportStatDimensionBeanList.get(i).get("source").toString();
            rr.setData(1, source + "_" + id);
            rr.setData(3, reportStatDimensionBeanList.get(i).get("name"));
            rr.setData(4, "1");

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
            if (source.equals("custom")) {//自定义指标部分
                ReportCustomIndexBean customIndex = reportCustomIndexService.getById(Integer.parseInt(id));
                String expressionIndex = customIndex.getExpressionIndex();
                String expressionField = customIndex.getExpressionField();
                HashMap cusMap = new HashMap();
                cusMap.put("id", source + "_" + id);
                cusMap.put("exp", expressionIndex);
                cusMap.put("suffix", customIndex.getSuffix());
                customIndexList.add(cusMap);
                List<String> indexList = CommReportUtil.getIndexByExp(expressionIndex);
                String[] fieldList = expressionField.split(",");
                for (int x = 0; x < indexList.size(); x++) {
                    HashMap statIndexMap = new HashMap();
                    statIndexMap.put("id", indexList.get(x).split("_")[1]);
                    statIndexMap.put("fieldName", fieldList[x]);
                    statIndexMap.put("source", indexList.get(x).split("_")[0]);
                    statIndexList.add(statIndexMap);
                }
            } else {//非自定义指标部分
                HashMap statIndexMap = new HashMap();
                statIndexMap.put("id", id);
                statIndexMap.put("fieldName", reportStatIndexBeanList.get(i).get("fieldName"));
                statIndexMap.put("source", source);
                statIndexList.add(statIndexMap);
            }
        }

        //初始化数据域
        reportService.findReportDataSet(dimensionList, statIndexList, queryList, modelId, userId, customIndexList);
        //图形部分
        if (showChart.equals("1")) {
            List<ReportChartModelBean> chartModelList = reportChartService.queryChart(Integer.valueOf(modelId));
            for (int i = 0; i < chartModelList.size(); i++) {
                Row rr = ds1.addRow();
                rr.setData(5, chartModelList.get(i).getId());
                rr.setData(4, "3");
                rr.setData(6, chartModelList.get(i).getTypeId());
                rr.setData(7, StringUtils.isBlank(chartModelList.get(i).getTitle()) ? " " : chartModelList.get(i).getTitle());
            }
        }
//        }
        return ds1;  //To change body of implemented methods use File | Settings | File Templates.
    }
}


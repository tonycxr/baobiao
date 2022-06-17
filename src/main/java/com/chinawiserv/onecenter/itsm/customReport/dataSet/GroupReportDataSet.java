package com.chinawiserv.onecenter.itsm.customReport.dataSet;

import com.runqian.report4.dataset.DataSet;
import com.runqian.report4.dataset.IDataSetFactory;
import com.runqian.report4.dataset.Row;
import com.runqian.report4.usermodel.Context;
import com.runqian.report4.usermodel.DataSetConfig;
import com.sungcor.baobiao.report.bean.ReportChartModelBean;
import com.sungcor.baobiao.report.bean.ReportModelBean;
import com.sungcor.baobiao.report.service.*;
import com.sungcor.baobiao.utils.ReportUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 * Date: 16-6-6
 * Time: 下午1:49
 * To change this template use File | Settings | File Templates.
 */
@Service
public class GroupReportDataSet extends ReportUtil implements IDataSetFactory {

    @Autowired
    public IReportService reportService;
    @Autowired
    public IReportTypeAttrService reportTypeAttrService;
    @Autowired
    public IReportTaskService reportTaskService;
    @Autowired
    public IReportChartService reportChartService;
    @Autowired
    public IReportModelService reportModelService;
    @Override
    public DataSet createDataSet(Context context, DataSetConfig dataSetConfig, boolean b) {

        Map map = context.getParamMap(false);

        //获取任务id，如果任务Id为空，表示是查询，否则根据任务Id获取参数
        String taskId = map.get("taskId")==null?"":map.get("taskId").toString();
        //模板Id
        String modelId = map.get("modelId")==null?"":map.get("modelId").toString();
        //当前用户Id
        String userId = map.get("userId")==null?"":map.get("userId").toString();
        DataSet ds1 = new DataSet("ds1");
        //当前模板Id
        ds1.addCol("modelId");
        //子模板Id
        ds1.addCol("subModelId");
        //章节名称
        ds1.addCol("subModelName");
        //章节描述
        ds1.addCol("subModelDescription");
        //子模板
        ds1.addCol("subModelRaq");
        //用户Id
        ds1.addCol("userId");
        ds1.addCol("height");
        ds1.addCol("showTable");
        ds1.addCol("showChart");
        ds1.addCol("chartHeight");
        List<Map> subModelList = reportTypeAttrService.findGroupingReport(modelId);
        String id = "";
        for(int i=0;i<subModelList.size();i++){
            Row rr = ds1.addRow();

            rr.setData(1,modelId);
            String subModelId = subModelList.get(i).get("MODEL_ID").toString();
            ReportModelBean reportModel= reportModelService.findBeanById(subModelId);
            rr.setData(2,subModelId);
            if(!subModelList.get(i).get("ID").toString().equals(id)){
                rr.setData(3,subModelList.get(i).get("CHAPERT_NAME"));
                rr.setData(4,subModelList.get(i).get("CHAPERT_DESC"));
                id = subModelList.get(i).get("ID").toString();
            }
            rr.setData(8,reportModel.getShowTable()+"");
            rr.setData(9,reportModel.getShowChart()+"");
            int typeId = Integer.parseInt(subModelList.get(i).get("TYPE_ID").toString());
            rr.setData(5,typeId);
            rr.setData(6,userId);
            HashMap modelMap = new HashMap();
            modelMap.put("modelId",subModelId);
            List<HashMap> queryList = new ArrayList<HashMap>();
            if("".equals(taskId)){
                //当前为报表查询，直接取缓存
                queryList = reportService.findReportModelQuery(null,subModelId,userId,"query",6);
            }else{
                //当前为报表任务，根据taskId获取
                queryList = reportTaskService.findTaskModelQuery(taskId);
            }
            if(typeId == 5){
                //获取报表的参数
                modelMap.put("value","-1");
                for(HashMap m:queryList){
                    if(m.get("source")!=null&&"organization".equals(m.get("source").toString())){
                        modelMap.put("value",m.get("fieldValue").toString());
                    }
                }
                //分组报表
                modelMap.put("level","2");
                List<Map> reportStatDimensionBeanList = reportTypeAttrService.findSelTreeDim(modelMap);
                rr.setData(7,reportStatDimensionBeanList.size()+2);
            }else if(typeId == 7){
                List<Map> reportStatIndexBeanList = reportTypeAttrService.findSelIndex(modelMap);
                rr.setData(7,reportStatIndexBeanList.size()+2);
            }
            else {
                List<Map> reportStatDimensionBeanList = reportTypeAttrService.findSelDimIndex(modelMap);
                rr.setData(7,reportStatDimensionBeanList.size()+2);
            }
            if(reportModel.getShowChart()==1){
                List<ReportChartModelBean> chartModelList= reportChartService.queryChart(Integer.valueOf(subModelId));
                rr.setData(10,chartModelList.size());
            }else{
                rr.setData(10,0);
            }
        }

        return ds1;  //To change body of implemented methods use File | Settings | File Templates.
    }
}

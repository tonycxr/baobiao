package com.sungcor.baobiao.report.service.impl;

import com.runqian.report4.dataset.DataSet;
import com.runqian.report4.dataset.Row;
import com.sungcor.baobiao.STSMApplicationContext;
import com.sungcor.baobiao.report.mapper.ReportMapper;
import com.sungcor.baobiao.report.mapper.ReportModelMapper;
import com.sungcor.baobiao.report.bean.ReportModelBean;
import com.sungcor.baobiao.report.service.IReportService;
import com.sungcor.baobiao.report.service.IReportTypeAttrService;
import com.sungcor.baobiao.report.util.CommReportUtil;
import com.sungcor.baobiao.utils.UtilTools;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 * Date: 16-5-30
 * Time: 上午11:13
 * To change this template use File | Settings | File Templates.
 */
@Transactional
@Service("IReportService")
public class ReportServiceImpl implements IReportService {

    @Autowired
    public ReportMapper reportMapper;

    @Autowired
    public ReportModelMapper reportModelMapper;

    @Autowired
    public IReportTypeAttrService reportTypeAttrService;

    private static Map<String, List<HashMap>> queryMap = new HashMap<String, List<HashMap>>();

    private static Map<String, DataSet> dataSetMap = new HashMap<String, DataSet>();

    static ScriptEngine jse = new ScriptEngineManager().getEngineByName("JavaScript");

    @Override
    public void defaultModelQuery(String modelId, String userId, String beginTime, String endTime,int modelType) {
        if (queryMap == null) {
            queryMap = new HashMap<String, List<HashMap>>();
        }
        List<HashMap> list = new ArrayList<HashMap>();
        HashMap map = new HashMap();
        if(beginTime!=null&&!"".equals(beginTime)){
            //开始时间
            map.put("fieldName","beginTime");
            map.put("relation", "<=");
            map.put("fieldValue", beginTime);
            map.put("source", "");
            list.add(map);
        }
        if(endTime!=null&&!"".equals(endTime)){
            //结束时间
            map = new HashMap();
            map.put("fieldName","endTime");
            map.put("relation", "<=");
            map.put("fieldValue", endTime);
            map.put("source", "");
            list.add(map);
        }
        //获取报表固定参数
        list = findStaticModelQuery(list,modelId);
        saveQueryMap(userId,modelId,modelType,list);
    }

    private void saveQueryMap(String userId,String modelId,int modelType,List<HashMap> list){
        String mapKey = "query_" + userId + "_" + modelId;
        if(modelType == 6){
            //组合报表
            List<Map> mapList = reportTypeAttrService.findGroupingReport(modelId);
            for(int i=0 ;i<mapList.size();i++){
                Map map = mapList.get(i);
                mapKey = "query_" + userId + "_" + map.get("MODEL_ID").toString();
                queryMap.remove(mapKey);
                queryMap.put(mapKey, list);
            }
        }else{
            queryMap.remove(mapKey);
            queryMap.put(mapKey, list);
        }
    }

    //获取报表固定参数
    private List<HashMap> findStaticModelQuery(List<HashMap> list,String modelId){
        HashMap map = new HashMap();
        //增加固定参数,服务产品&服务分类
        ReportModelBean reportModelBean = reportModelMapper.findBeanById(modelId);
        if(reportModelBean.getServiceCategoryId()!=0){
            map = new HashMap();
            map.put("fieldName", "service_category_id");
            map.put("relation", "=");
            map.put("fieldValue", reportModelBean.getServiceCategoryId());
            map.put("source", "bpm");
            list.add(map);
        }
        if(reportModelBean.getProductId()!=0){
            map = new HashMap();
            map.put("fieldName", "productId");
            map.put("relation", "=");
            map.put("fieldValue", reportModelBean.getProductId());
            map.put("source", "product");
            list.add(map);
        }
        return list;

    }

    @Override
    public List<HashMap> findReportModelQuery(HttpServletRequest request, String modelId, String userId, String flag,int modelType) {
        String mapKey = "query_" + userId + "_" + modelId;
        if (queryMap == null) {
            queryMap = new HashMap<String, List<HashMap>>();
        }
        if ("add".equals(flag)) {
            List<HashMap> list = new ArrayList<HashMap>();
            Enumeration pNames = request.getParameterNames();
            HashMap map = null;
            while (pNames.hasMoreElements()) {
                String name = (String) pNames.nextElement();
                String val = request.getParameter(name);
                map = new HashMap();
                map.put("fieldName", name);
                if (name.equals("beginTime")&&val!=null&&!"".equals(val)) {
                    map.put("relation", "<=");
                    map.put("fieldValue", val);
                    map.put("source", "");
                } else if (name.equals("endTime")&&val!=null&&!"".equals(val)) {
                    map.put("relation", ">=");
                    map.put("fieldValue", val);
                    map.put("source", "");
                } else {
                    map.put("relation", "=");
                    if (val.indexOf("|") > 0) {
                        String[] value = val.split("\\|");
                        map.put("fieldValue", value[0]);
                        map.put("source", value[1]);
                    }
                }
                if (map.size() == 4) {
                    list.add(map);
                }
            }
            //增加固定参数,服务产品&服务分类
            list = findStaticModelQuery(list,modelId);
            saveQueryMap(userId,modelId,modelType,list);
            return list;
        }
        return queryMap.get(mapKey);
    }

    //别名命名规则：T1为流程主表，T0为维度，T为指标，T1为查询条件
    @Override
    public DataSet findReportDataSet(List<HashMap> dimensionList, List<HashMap> statIndexList, List<HashMap>
            queryList, String modelId, String userId, List<HashMap> customIndexList) {

        //增加对工单or资产的判断 begin

        HashMap tableNameMap = reportMapper.findTableNameByModelId(modelId);
        String tableName =tableNameMap.get("MAIN_TABLE_NAME")==null?"BPM_PROCESS_INSTANCE":tableNameMap.get("MAIN_TABLE_NAME").toString();
        String childTableName =tableNameMap.get("CHILD_TABLE_NAME")==null?"CASE_FORM_DATA":tableNameMap.get("CHILD_TABLE_NAME").toString();
        String relationFiled =tableNameMap.get("RELATION_FILED")==null?"PROCESS_INSTANCE_ID":tableNameMap.get("RELATION_FILED").toString();
        String timeFiled =tableNameMap.get("TIME_FILED")==null?"CREATE_TIME":tableNameMap.get("TIME_FILED").toString();
        String nameField =tableNameMap.get("NAME_FIELD")==null?"FIELD_NAME":tableNameMap.get("NAME_FIELD").toString();
        String valueFiled =tableNameMap.get("VALUE_FILED")==null?"FIELD_VALUE":tableNameMap.get("VALUE_FILED").toString();
        //增加对工单or资产的判断 end

        HashMap<String,String> m = new HashMap<String, String>();
        String dbtype = "mysql";//判断是连接的oracle还是mysql
        DataSet ds1 = new DataSet("ds1");
        //维度Id
        ds1.addCol("dimensionId");
        //指标Id
        ds1.addCol("statIndexId");
        ds1.addCol("type");
        ds1.addCol("value");
        ds1.addCol("dimensionName");
        ds1.addCol("statIndexName");
        ds1.addCol("dimsort");
        //条件sql
        String whereSql = findWhereSql(queryList,dbtype,childTableName,relationFiled,"T1."+timeFiled,nameField,valueFiled);
//        String whereSql = " WHERE 1=1 ";
        //暂时增加数据域
        String regionInner = "";
        //数据域
        if(tableName.equals("BPM_PROCESS_INSTANCE")){
            //工单
            regionInner = " AND EXISTS (SELECT 1 FROM SYS_USER_REGION T2 \n" +
                    "WHERE  T2.REGION_ID = T1.REGION_ID AND T2.USER_ID = '"+userId+"') ";
        }else if(tableName.equals("CMDB_CI")){
            //资产
            regionInner = " AND EXISTS (SELECT 1 FROM SYS_REGION_AREA T2 \n" +
                    "LEFT JOIN SYS_USER_REGION T3 ON T3.REGION_ID = T2.REGION_ID AND T3.USER_ID = '"+userId+"' \n" +
                    "WHERE  T1.AREAID = T2.AREA_ID) ";
            whereSql += " AND T1.DELETEFLAG = '0' ";
        }


        HashMap dimensionMap = findFieldNameMap(dimensionList);
        HashMap statIndexMap = findFieldNameMap(statIndexList);
        Set dKeys = dimensionMap.keySet();
        Iterator di = dKeys.iterator();
        Set sKeys = statIndexMap.keySet();
        while (di.hasNext()) {
            //维度的字段名，暂时先支持工单表中的字段。
            //dKey表示字段名称
            String dKey = (String) di.next();
            //dSource指向特定数据表
            String dSource = dimensionMap.get(dKey).toString();
            String dSql = "";
            String dSelect = "'0'";
            int flagSize = 1;
            if (dSource.equals("dict")||dSource.equals("customModel")) {
                //子表 中的字段
                dSql = " LEFT JOIN  "+childTableName+" T0" + dSource + "  ON T1.ID = T0" + dSource + "."+relationFiled+" AND T0" + dSource + "."+nameField+" = '" + dKey + "'";
                dSelect = "T0" + dSource + "."+valueFiled+"";
            } else if(dSource.equals("user") && dKey.equals("handel") ){
                dSql = " JOIN (SELECT DISTINCT IMPLEMENTER_ID,PROCESS_INSTANCE_ID FROM\n" +
                        "(SELECT IMPLEMENTER_ID,PROCESS_INSTANCE_ID FROM BPM_NODE_HISTORY\n" +
                        "UNION ALL\n" +
                        "SELECT OPT_PERSON_ID IMPLEMENTER_ID,PROCESS_INSTANCE_ID FROM BPM_REVIEW_INFO\n" +
                        "UNION ALL\n" +
                        "SELECT SIGNER_ID IMPLEMENTER_ID,PROCESS_INSTANCE_ID FROM BPM_SIGNING) A) TT1 ON T1.ID = TT1."+relationFiled+" ";
                dSelect =" TT1.IMPLEMENTER_ID ";
            } else if (dSource.equals("bpm")||dSource.equals("user")||dSource.equals("serviceCat")) {
                //BPM_PROCESS_INSTANCE中的字段
                dSql = "";
                dSelect = "T1." + dKey;
//            }else if (dSource.equals("cusType")) {
//                //客户类型
//                dSql = " LEFT JOIN  "+childTableName+" T0" + dSource + "  ON T1.ID = T0" + dSource + "."+relationFiled+" AND T0" + dSource + "."+nameField+" = 'customer' \n";
//                dSql += " LEFT JOIN CUSTOMER_INFO T2"+dSource+" ON T0"+dSource+"."+valueFiled+" = T2"+dSource+".ID \n";
//                dSelect = "T2" + dSource +".CUS_TYPE";
//            }else if (dSource.equals("cusLevel")) {
//                //客户级别
//                dSql = " LEFT JOIN  "+childTableName+" T0" + dSource + "  ON T1.ID = T0" + dSource + "."+relationFiled+" AND T0" + dSource + "."+nameField+" = 'customer' \n";
//                dSql += " LEFT JOIN CUSTOMER_INFO T2"+dSource+" ON T0"+dSource+"."+valueFiled+" = T2"+dSource+".ID \n";
//                dSelect = "T2" + dSource +".CUS_LEVEL";
            } else if (dSource.equals("runLog")) {
                //关闭代码和撤销代码
                if (dKey.equals("caseClose")) {
                    //关闭代码
                    dSql = " LEFT JOIN  BPM_PROCESS_RUNLOG T0" + dSource + "  ON T1.ID = T0" + dSource + "."+relationFiled+" AND T0" + dSource + ".OPT_TYPE = 'confirm'";
                } else if (dKey.equals("terminate")) {
                    //撤销代码
                    dSql = " LEFT JOIN  BPM_PROCESS_RUNLOG T0" + dSource + "  ON T1.ID = T0" + dSource + "."+relationFiled+" AND T0" + dSource + ".OPT_TYPE = 'terminate'";
                }
                dSelect = "T0" + dSource + ".APPEND_ATT";
            }else if (dSource.equals("organization")) {
                flagSize = dimensionList.size();
                //分组报表查询按组织查询
//                String ids = "-1";
//                for(int i=0;i<dimensionList.size();i++){
//                    ids += ","+dimensionList.get(i).get("id").toString();
//                }
//                dSql = " LEFT JOIN SYS_USER T2 ON T1.REPORTER = T2.ID AND T1.REPORTERSOURCE = 'sys'\n";
//                dSql += " LEFT JOIN SYS_USER T3 ON T1.CREATOR = T3.ID\n";
//                dSelect = "findGroupOrganization(IFNULL(T2.ORGANIZATION_ID,T3.ORGANIZATION_ID),'"+ids+"')";

            }else if (dSource.equals("trend") ) {
                dSql = " ";
                dSelect = dKey;
            }
            for(int k=0 ; k<flagSize;k++){
                String orgWhereSql = "";
                if(dSource.equals("organization")){
                    dSql = " LEFT JOIN SYS_USER Tp ON T1.REPORTER = Tp.ID AND T1.REPORTERSOURCE = 'sys'\n";
                    dSql += " LEFT JOIN SYS_USER T3 ON T1.CREATOR = T3.ID\n";
                    dSelect = "findGroupOrganization((CASE WHEN Tp.ORGANIZATION_ID IS NULL THEN T3.ORGANIZATION_ID" +
                            " ELSE Tp.ORGANIZATION_ID END),'"+dimensionList.get(k).get("id").toString()+"')";
                    orgWhereSql =whereSql + " AND "+ dSelect +" !='-1'";
                }

                Iterator si = sKeys.iterator();
                while (si.hasNext()) {
                    //指标的字段名或者标识
                    String sKey = (String) si.next();
                    //sSource指向特定数据表
                    String sSource = statIndexMap.get(sKey).toString();
                    List<HashMap> list = new ArrayList<HashMap>();

                    String sSql = "";
                    //初始值为sKey表示特殊指标
                    String sSelect = "'" + sKey + "'";
                    String sql = "";
                    String countSql = " SUM(1) ";
                    //此处if判定一些特定的指标
                    if (sSource.equals("dict")||sSource.equals("customModel")) {
                        //CASE_FORM_DATA 中的字段
                        sSql = " LEFT JOIN  "+childTableName+" T" + sSource + "  ON T1.ID = T" + sSource + "."+relationFiled+" AND T" + sSource + "."+nameField+" = '" + sKey + "'";
                        sSelect = "T" + sSource + "."+valueFiled+"";
                    } else if (sSource.equals("runLog")) {
                        if (sKey.equals("caseClose")) {
                            //关闭代码
                            sSql = " LEFT JOIN  BPM_PROCESS_RUNLOG T" + sSource + "  ON T1.ID = T" + sSource + "."+relationFiled+" AND T" + sSource + ".OPT_TYPE = 'confirm'";
                        } else if (sKey.equals("terminate")) {
                            //撤销代码
                            sSql = " LEFT JOIN  BPM_PROCESS_RUNLOG T" + sSource + "  ON T1.ID = T" + sSource + "."+relationFiled+" AND T" + sSource + ".OPT_TYPE = 'terminate'";
                        }
                        sSelect = "T" + sSource + ".APPEND_ATT";
                    } else if (sSource.equals("evaluation")) {
                        //满意度
                        sSql = " LEFT JOIN  BPM_EVALUATION T" + sSource + "  ON T1.ID = T" + sSource + "."+relationFiled+" ";
                        sSelect = "T" + sSource + ".EVALUATION_LEVEL";
                    } else if (sSource.equals("bpm")) {
                        //BPM_PROCESS_INSTANCE中的字段
                        sSelect = "T1." + sKey;
                    } else if (sKey.equals("notFinished")) {
                        //未完成数
                        sql += " AND T1.STATES != '-1' AND T1.STATES != '0'";
                    } else if (sKey.equals("effective")) {
                        //有效数
                        sql += " AND T1.STATES != '-1'";
                    } else if (sKey.equals("total")) {
                        //总数无需特殊处理
                    } else if (sKey.equals("notFinishedRate")) {
                        //未完成率
                        if(dbtype.equals("oracle")){
                            countSql = " TRIM(TO_CHAR(SUM(CASE WHEN T1.STATES != '-1' AND T1.STATES != '0' THEN 100 " +
                                    "ELSE  0 END)/SUM(1),'990.99')||'%') ";
                        }else if(dbtype.equals("mysql")){
                            countSql = " CONCAT(ROUND(SUM(IF (T1.STATES != '-1' AND T1.STATES != '0',100,0))/SUM(1),2),'%') ";
                        }
                    } else if (sKey.equals("effectiveRate")) {
                        //有效率
                        if(dbtype.equals("oracle")){
                            countSql = " TRIM(TO_CHAR(SUM(CASE WHEN T1.STATES != '-1' THEN 100 ELSE 0 END)/SUM(1),'990" +
                                    ".99')||'%') ";
                        }else if(dbtype.equals("mysql")){
                            countSql = " CONCAT(ROUND(SUM(IF (T1.STATES!='-1',100,0))/SUM(1),2),'%') ";
                        }
                    } else if (sKey.equals("invalidRate")) {
                        //无效率
                        if(dbtype.equals("oracle")){
                            countSql = " TRIM(TO_CHAR(SUM(CASE WHEN T1.STATES = '-1' THEN 100 ELSE 0 END)/SUM(1),'990" +
                                    ".99')||'%') ";
                        }else if(dbtype.equals("mysql")){
                            countSql = " CONCAT(ROUND(SUM(IF (T1.STATES='-1',100,0))/SUM(1),2),'%') ";
                        }
                    } else if (sKey.equals("shelveRate")) {
                        //无效率
                        if(dbtype.equals("oracle")){
                            countSql = " TRIM(TO_CHAR(SUM(CASE WHEN T1.STATES = '5' THEN 100 ELSE 0 END)/SUM(1),'990" +
                                    ".99')||'%') ";
                        }else if(dbtype.equals("mysql")){
                            countSql = " CONCAT(ROUND(SUM(IF (T1.STATES='5',100,0))/SUM(1),2),'%') ";
                        }
                    } else if (sKey.equals("finished")) {
                        //已完成
                        sql += " AND T1.STATES = '0'";
                    } else if (sKey.equals("finishedRate")) {
                        //完成率
                        if(dbtype.equals("oracle")){
                            countSql = " TRIM(TO_CHAR(SUM(CASE WHEN T1.STATES = '0' THEN 100 ELSE 0 END)/SUM(1),'990" +
                                    ".99')||'%') ";
                        }else if(dbtype.equals("mysql")){
                            countSql = " CONCAT(ROUND(SUM(IF (T1.STATES='0',100,0))/SUM(1),2),'%') ";
                        }
                    } else if (sKey.equals("successFinished")) {
                        //成功解决数
                        sSql = " LEFT JOIN  BPM_PROCESS_RUNLOG T" + sKey + "  ON T1.ID = T" + sKey + "."+relationFiled+" AND T" + sKey + ".OPT_TYPE = 'confirm'";
                        sql += " AND T" + sKey + ".APPEND_ATT = '19'";
                    } else if (sKey.equals("successRate")) {
                        //成功解决率
                        sSql = " LEFT JOIN  BPM_PROCESS_RUNLOG T" + sKey + "  ON T1.ID = T" + sKey + "."+relationFiled+" AND T" + sKey + ".OPT_TYPE = 'confirm'";
                        if(dbtype.equals("oracle")){
                            countSql = " TRIM(CASE WHEN SUM(CASE WHEN T1.STATES = '0' THEN 1 ELSE 0 END)=0 THEN '0' ELSE \n" +
                                    " TO_CHAR(SUM(CASE WHEN T" + sKey + ".APPEND_ATT = '19' THEN 100 ELSE 0 END)/SUM" +
                                    "(CASE WHEN T1.STATES = '0' THEN 1 ELSE 0 END),'990.99')||'%' END) ";
                        }else if(dbtype.equals("mysql")){
                            countSql = " CONCAT(ROUND(SUM(IF (T" + sKey + ".APPEND_ATT = '19',100,0))/SUM(IF (T1.STATES='0',1,0)),2),'%') ";
                        }

                    } else if (sKey.equals("finishOnTime")) {
                        //按时完成
                        if(dbtype.equals("oracle")){
                            sql += " AND  T1.LEVAE_TIME >= TO_DATE(T1.SLA_SOLUTE_END_TIME,'yyyy-mm-dd,hh24:mi:ss') AND T1.STATES = '0'";
                        }else if(dbtype.equals("mysql")){
                            sql += " AND  T1.LEVAE_TIME >= T1.SLA_SOLUTE_END_TIME AND T1.STATES = '0'";
                        }
//                        sql += " AND T1.SLA_STATUS = '202' AND T1.STATES = '0'";
                    } else if (sKey.equals("timeoutCompleted")) {
                        //超时完成
                        if(dbtype.equals("oracle")){
                            sql += " AND TO_DATE(T1.SLA_SOLUTE_END_TIME,'yyyy-mm-dd,hh24:mi:ss') >= T1.LEVAE_TIME AND T1.STATES = '0'";
                        }else if(dbtype.equals("mysql")){
                            sql += " AND T1.SLA_SOLUTE_END_TIME >= T1.LEVAE_TIME AND T1.STATES = '0'";
                        }
//                        sql += " AND T1.SLA_STATUS = '201' AND T1.STATES = '0'";
                    } else if (sKey.equals("timeoutNotCompleted")) {
                        //超时未完成
                        if(dbtype.equals("oracle")){
                            sql += " AND TO_DATE(T1.SLA_SOLUTE_END_TIME,'yyyy-mm-dd,hh24:mi:ss') >= sysdate AND T1.STATES != '0'";
                        }else if(dbtype.equals("mysql")){
                            sql += " AND T1.SLA_SOLUTE_END_TIME >= NOW() AND T1.STATES != '0'";
                        }
//                        sql += " AND T1.SLA_STATUS = '201' AND T1.STATES != '0'";
                    } else if (sKey.equals("timeoutNum")) {
                        //超时个数
                        if(dbtype.equals("oracle")){
                            sql += " AND ((TO_DATE(T1.SLA_SOLUTE_END_TIME,'yyyy-mm-dd,hh24:mi:ss') >= T1.LEVAE_TIME AND T1.STATES = '0')" +
                                    " OR (TO_DATE(T1.SLA_SOLUTE_END_TIME,'yyyy-mm-dd,hh24:mi:ss') >= sysdate AND T1.STATES != '0' )) ";
                        }else if(dbtype.equals("mysql")){
                            sql += " AND ((T1.SLA_SOLUTE_END_TIME >= T1.LEVAE_TIME AND T1.STATES = '0')" +
                                    " OR (T1.SLA_SOLUTE_END_TIME >= NOW() AND T1.STATES != '0'))";
                        }
//                        sql += " AND T1.SLA_STATUS = '201'";
                    } else if (sKey.equals("finishOnTimeRate")) {
                        //按时完成率

                        if(dbtype.equals("oracle")){
                            countSql = " TRIM(TO_CHAR(SUM(CASE WHEN  T1.LEVAE_TIME>= TO_DATE(T1.SLA_SOLUTE_END_TIME,'yyyy-mm-dd,hh24:mi:ss') AND T1.STATES = '0' THEN " +
                                    "100 ELSE 0 END)/SUM(1),'990.99')||'%') ";
                        }else if(dbtype.equals("mysql")){
                            countSql = " CONCAT(ROUND(SUM(IF (T1.LEVAE_TIME >= T1.SLA_SOLUTE_END_TIME AND T1.STATES = '0',100,0))/SUM(1),2),'%') ";
                        }
                    } else if (sKey.equals("timeoutCompletedRate")) {
                        //超时完成率

                        if(dbtype.equals("oracle")){
                            countSql = " TRIM(TO_CHAR(SUM(CASE WHEN TO_DATE(T1.SLA_SOLUTE_END_TIME,'yyyy-mm-dd,hh24:mi:ss') >= T1.LEVAE_TIME AND T1.STATES = '0' THEN " +
                                    "100 ELSE 0 END)/SUM(1),'990.99')||'%') ";
                        }else if(dbtype.equals("mysql")){
                            countSql = " CONCAT(ROUND(SUM(IF (T1.SLA_SOLUTE_END_TIME >= T1.LEVAE_TIME AND T1.STATES = '0',100,0))/SUM(1),2),'%') ";
                        }
                    } else if (sKey.equals("timeoutNotCompletedRate")) {
                        //超时未完成率

                        if(dbtype.equals("oracle")){
                            countSql = " TRIM(TO_CHAR(SUM(CASE WHEN TO_DATE(T1.SLA_SOLUTE_END_TIME,'yyyy-mm-dd,hh24:mi:ss') >= sysdate AND T1.STATES != '0' THEN " +
                                    "100 ELSE 0 END)/SUM(1),'990.99')||'%') ";
                        }else if(dbtype.equals("mysql")){
                            countSql = " CONCAT(ROUND(SUM(IF (T1.SLA_SOLUTE_END_TIME >= NOW() AND T1.STATES != '0',100,0))/SUM(1),2),'%') ";
                        }
                    } else if (sKey.equals("timeoutRate")) {
                        //超时率

                        if(dbtype.equals("oracle")){
                            countSql = " TRIM(TO_CHAR(SUM(CASE WHEN (TO_DATE(T1.SLA_SOLUTE_END_TIME,'yyyy-mm-dd,hh24:mi:ss') >= T1.LEVAE_TIME AND T1.STATES = '0')" +
                                       " OR (TO_DATE(T1.SLA_SOLUTE_END_TIME,'yyyy-mm-dd,hh24:mi:ss') >= sysdate AND T1.STATES != '0') THEN 100 ELSE 0 END)/SUM(1),'990.99')||'%') ";
                        }else if(dbtype.equals("mysql")){
                            countSql = " CONCAT(ROUND(SUM(IF ((T1.SLA_SOLUTE_END_TIME >= T1.LEVAE_TIME AND T1.STATES = '0')" +
                                       " OR (T1.SLA_SOLUTE_END_TIME >= NOW() AND T1.STATES != '0'),100,0))/SUM(1),2),'%') ";
                        }
                    } else if (sKey.equals("visit")) {
                        //已回访
                        sSql = " LEFT JOIN  BPM_EVALUATION T" + sKey + "  ON T1.ID = T" + sKey + "."+relationFiled+" ";
                        sql += " AND T1.STATES = '0' AND T" + sKey + ".ID IS NOT NULL";
                    } else if (sKey.equals("notVisit")) {
                        //未回访
                        sSql = " LEFT JOIN  BPM_EVALUATION T" + sKey + "  ON T1.ID = T" + sKey + "."+relationFiled+" ";
                        sql += " AND T1.STATES = '0' AND T" + sKey + ".ID IS NULL";
                    } else if (sKey.equals("visitRate")) {
                        //回访率
                        sSql = " LEFT JOIN  BPM_EVALUATION T" + sKey + "  ON T1.ID = T" + sKey + "."+relationFiled+" ";

                        if(dbtype.equals("oracle")){
                            countSql = " TRIM(TO_CHAR(SUM(CASE WHEN T" + sKey + ".ID IS NOT NULL THEN 100 ELSE 0 END)" +
                                    "/SUM(1),'990.99')||'%') ";
                        }else if(dbtype.equals("mysql")){
                            countSql = " CONCAT(ROUND(SUM(IF (T" + sKey + ".ID IS NOT NULL,100,0))/SUM(1),2),'%') ";
                        }
                        sql += " AND T1.STATES = '0'";
                    } else  if (sKey.equals("changeNum")){
                        sSql = " LEFT JOIN BPM_PROCESS_PEER T1"+sKey+" ON T1.ID = T1"+sKey+".PEER_ID AND T1"+sKey+".PEER_TYPE = 'ciInfo'\n" +
                                " LEFT JOIN BPM_PROCESS_INSTANCE T2"+sKey+" ON T1"+sKey+".PROCESS_INSTANCE_ID = T2"+sKey+".ID";
                        sql += " AND T2"+sKey+".SERVICE_CATEGORY_ID = '3'";
                    }
                    HashMap map = new HashMap();
                    map.put("dSql", dSql);
                    map.put("sSql", sSql);
                    map.put("dSelect", dSelect);
                    map.put("sSelect", sSelect);
                    if(dSource.equals("organization")){
                        map.put("whereSql", orgWhereSql + sql);
                    }else if(sKey.equals("changeNum")){
                        String changeNumWhereSql = findWhereSql(queryList,dbtype,childTableName,relationFiled,"T2"+sKey+".LEVAE_TIME",nameField,valueFiled);
                        map.put("whereSql", changeNumWhereSql + sql);
                    }else {
                        map.put("whereSql", whereSql + sql);
                    }
                    map.put("countSql", countSql);
//                    String testSql = "select "+dSelect+" dimensionId,"+sSelect+" statIndexId,\n" +
//                            "            "+countSql+"\n" +
//                            "        orderNum from "+tableName+" T1\n" +
//                            "            "+regionInner+"\n" +
//                            "            "+dSql+"\n" +
//                            "            "+sSql+"\n" +
//                            "            "+map.get("whereSql").toString()+"\n" +
//                            "            "+regionInner+"\n" +
//                            "            GROUP BY dimensionId,statIndexId";

                    String testSql = "select "+dSelect+" dimensionId,"+sSelect+" statIndexId,\n" +
                            "            "+countSql+"\n" +
                            "        orderNum from "+tableName+" T1\n" +
                            "            "+dSql+"\n" +
                            "            "+sSql+"\n" +
                            "            "+whereSql+"\n" +
                            "            "+regionInner+"\n" +
                            "            GROUP BY dimensionId,statIndexId";
                    System.out.println("testSQL=="+testSql);
                    map.put("tableName",tableName);
                    map.put("regionInner",regionInner);
                    list = reportMapper.findSelectOrderNum(map);
                    for (int i = 0; i < list.size(); i++) {
                        if(list.get(i).get("dimensionId")!=null){
                            Row rr = ds1.addRow();
                            rr.setData(1, dSource + "_" + list.get(i).get("dimensionId"));
                            rr.setData(2, sSource + "_" + list.get(i).get("statIndexId"));
                            rr.setData(3, "3");
                            rr.setData(4, list.get(i).get("orderNum") == null ? "0" : list.get(i).get("orderNum"));
                            m.put(dSource + "_" + list.get(i).get("dimensionId")+"@"+sSource + "_" + list.get(i).get
                                    ("statIndexId"),list.get(i).get("orderNum") == null ?
                                    "0" : list.get(i).get("orderNum").toString());
                        }
                    }
                }
            }
            if(dSource.equals("trend")){
                break;
            }
        }
        for(int i=0;i<dimensionList.size();i++){
            for(int j=0;j<customIndexList.size();j++){
                String strs = customIndexList.get(j).get("exp").toString();
                List<String> indexList = CommReportUtil.getIndexByExp(strs);
                for(int k=0;k<indexList.size();k++){
                    String key = dimensionList.get(i).get("source")+"_"+dimensionList.get(i).get("id")
                            +"@"+indexList.get(k);
                    String v = null;
                    if(m.get(key)==null){
                        v = "0";
                    }else if(m.get(key).contains("%")){
                        v = m.get(key).replace("%","");
                        float f = Float.parseFloat(v)/100;
                        v = f+"";
                    }else {
                        v = m.get(key);
                    }
                    strs = strs.replaceAll("\\$"+indexList.get(k)+"\\$",v);
                }
                try{
                    Row rr = ds1.addRow();
                    rr.setData(1, dimensionList.get(i).get("source")+"_"+dimensionList.get(i).get("id"));
                    rr.setData(2, customIndexList.get(j).get("id"));
                    rr.setData(3, "3");
                    String num = jse.eval(strs).toString();
                    if("%".equals(customIndexList.get(j).get("suffix"))){
                        num = num.equals("Infinity")?"0":num;
                        num = num.equals("NaN")?"0":num;
                        num =jse.eval(num+"*100").toString();
                        num = CommReportUtil.round(num,2, BigDecimal.ROUND_HALF_EVEN)+"%";
                    }else{
                        num = num.equals("Infinity")?"0": CommReportUtil.round(num,0, BigDecimal.ROUND_HALF_EVEN);
                    }
                    rr.setData(4, num);
                }catch (Exception e){
                    e.printStackTrace();
                }

            }
        }
        //将初始化的数据源放入缓存
        dataSetMap.put("subReport_" + modelId + "_" + userId, ds1);

        return ds1;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public String findWhereSql(List<HashMap> queryList,String dbtype,String childTableName,String relationFiled,String timeFiled,String nameField,String valueFiled) {
        String whereSql = " WHERE 1=1 ";
        if (queryList == null) {
            return whereSql;
        }
        String joinSql = "";
        List<String> sourceList = new ArrayList<String>();
        for (int i = 0; i < queryList.size(); i++) {
            HashMap map = queryList.get(i);
            String fieldName = map.get("fieldName").toString();
            String fieldValue = map.get("fieldValue").toString();
            String relation = map.get("relation").toString();
            String source = map.get("source")==null?"":map.get("source").toString();
            if ("dict".equals(source)||"customModel".equals(source)) {
                //针对数据表中的字段进行查询
                joinSql += " INNER JOIN "+childTableName+" T1" + i + " ON T1.ID = T1" + i + "."+relationFiled+" \n" +
                        "AND T1" + i + "."+nameField+" = '" + fieldName + "' AND T1" + i + "."+valueFiled+" " + relation + " '" + fieldValue + "'\n";
            } else if ("bpm".equals(source)) {
                //工单表，不需要加
                whereSql += " AND T1."+fieldName+" " + relation + " '" + fieldValue + "'\n";
            } else if("reporter".equals(source)){
                //工单表，申请人
                whereSql += " AND T1.REPORTER "+relation+" '"+fieldValue+"' AND T1.REPORTERSOURCE = 'sys'\n";
            }else if("runLog".equals(source)){
                //关闭代码或者撤销代码
                joinSql +=" INNER JOIN BPM_PROCESS_RUNLOG T1"+i+" ON T1.ID = T1"+i+"."+relationFiled+" " +
                        "AND T1"+i+".APPEND_ATT "+relation+"'"+fieldValue+"'";
            }else if("staffPosition".equals(source)){
                //人员职位 (申请人 或者 创建人)
                joinSql +=" LEFT JOIN SYS_USER T1"+i+" ON T1.REPORTER = T1"+i+".ID \n" +
                        " LEFT JOIN SYS_USER T2"+i+" ON T1.CREATOR = T2"+i+".ID \n";
                whereSql +=" AND (T1"+i+".POSITION "+relation+"'"+fieldValue+"' OR T2"+i+".POSITION "+relation+"'"+fieldValue+"') \n";
            }else if("evaluation".equals(source)){
                //满意度
                joinSql += " INNER JOIN  BPM_EVALUATION T1" + i + "  ON T1.ID = T1" + i + "."+relationFiled+" \n"+
                        "AND T1"+i+".EVALUATION_LEVEL "+relation+"'"+fieldValue+"'";
            }else if("organization".equals(source)){
                //组织
                joinSql +=" LEFT JOIN SYS_USER T1"+i+" ON T1.REPORTER = T1"+i+".ID \n" +
                        " LEFT JOIN SYS_USER T2"+i+" ON T1.CREATOR = T2"+i+".ID \n";
                whereSql +=" AND (FIND_IN_SET(T1"+i+".ORGANIZATION_ID,GETCHILDORGANIZATION('"+fieldValue+"'))>0" +
                        " OR FIND_IN_SET(T2"+i+".ORGANIZATION_ID,GETCHILDORGANIZATION('"+fieldValue+"'))>0 )";
            }else if("role".equals(source)){
                //角色  (申请人 或者 创建人)
                joinSql +=" LEFT JOIN SYS_USER_ROLE T1"+i+" ON T1.REPORTER = T1"+i+".USER_ID AND T1"+i+".ROLE_ID "+relation+"'"+fieldValue+"'\n" +
                        " LEFT JOIN SYS_USER_ROLE T2"+i+" ON T1.CREATOR = T2"+i+".USER_ID AND T2"+i+".ROLE_ID "+relation+"'"+fieldValue+"'\n";
                whereSql +=" AND (T1"+i+".ROLE_ID "+relation+"'"+fieldValue+"' OR T2"+i+".ROLE_ID "+relation+"'"+fieldValue+"')";
            }else if("group".equals(source)){
                //工作组 (申请人 或者 创建人)
                joinSql +=" LEFT JOIN SYS_GROUP_USER T1"+i+" ON T1.REPORTER = T1"+i+".USER_ID AND T1"+i+".GROUP_ID "+relation+"'"+fieldValue+"'\n" +
                        " LEFT JOIN SYS_GROUP_USER T2"+i+" ON T1.CREATOR = T2"+i+".USER_ID AND T2"+i+".GROUP_ID "+relation+"'"+fieldValue+"'\n";
                whereSql +=" AND (T1"+i+".GROUP_ID "+relation+"'"+fieldValue+"' OR T2"+i+".GROUP_ID "+relation+"'"+fieldValue+"')";
            }else if("product".equals(source)){
                //服务产品
                joinSql +=" INNER JOIN SF_SP_PROCESSINSTANCE SSP ON SSP.PROCESSINSTANCE_ID = T1.ID\n" +
                        " INNER JOIN SF_SERVICE_PRODUCT SP ON SSP.SERVICEPRODUCT_ID = SP.ID ";
                whereSql +=" AND SP.ID = '"+fieldValue+"'";
//            }else if (source.equals("cusType")) {
//                //客户类型
//                joinSql += " LEFT JOIN  "+childTableName+" T1" + source + "  ON T1.ID = T1" + source + "."+relationFiled+" AND T1" + source + "."+nameField+" = 'customer' \n"
//                        + " LEFT JOIN CUSTOMER_INFO T3"+source+" ON T1"+source+"."+valueFiled+" = T3"+source+".ID \n";
//                whereSql += " AND T3" + source +".CUS_TYPE = '"+fieldValue+"'";
//            }else if (source.equals("cusLevel")) {
//                //客户级别
//                joinSql += " LEFT JOIN  "+childTableName+" T1" + source + "  ON T1.ID = T1" + source + "."+relationFiled+" AND T1" + source + "."+nameField+" = 'customer' \n"
//                        + " LEFT JOIN CUSTOMER_INFO T3"+source+" ON T1"+source+"."+valueFiled+" = T3"+source+".ID \n";
//                whereSql += " AND T3" + source +".CUS_LEVEL = '"+fieldValue+"'";
            }else if("".equals(source)){
                //时间比较
                if(dbtype.equals("oracle")){
                    if("beginTime".equals(fieldName)){
                        whereSql += " AND "+timeFiled+" >= TO_DATE('"+fieldValue+"','yyyy-MM-dd HH24:mi:ss') \n";
                    }
                    if("endTime".equals(fieldName)){
                        whereSql += " AND  TO_DATE('"+fieldValue+"','yyyy-MM-dd HH24:mi:ss')>= "+timeFiled+"  \n";
                    }
                    if("datePurview".equals(fieldName)){
                        //表示任务时的关键字
                        HashMap map2 = getQueryDate(fieldValue);
                        String beginTime = map2.get("beginTime").toString();
                        String endTime = map2.get("endTime").toString();
                        if(!"".equals(beginTime)){
                            whereSql += " AND "+timeFiled+" >= TO_DATE('"+map2.get("beginTime").toString()+"','yyyy-MM-dd HH24:mi:ss') \n";
                        }
                        if(!"".equals(endTime)){
                            whereSql += " AND TO_DATE('"+map2.get("endTime").toString()+"','yyyy-MM-dd HH24:mi:ss') >= "+timeFiled+" \n";
                        }
                    }
                }else if(dbtype.equals("mysql")){
                    if("beginTime".equals(fieldName)){
                        whereSql += " AND "+timeFiled+" >= '"+fieldValue+"' \n";
                    }
                    if("endTime".equals(fieldName)){
                        whereSql += " AND  '"+fieldValue+"'>= "+timeFiled+"  \n";
                    }
                    if("datePurview".equals(fieldName)){
                        //表示任务时的关键字
                        HashMap map2 = getQueryDate(fieldValue);
                        String beginTime = map2.get("beginTime").toString();
                        String endTime = map2.get("endTime").toString();
                        if(!"".equals(beginTime)){
                            whereSql += " AND "+timeFiled+" >= '"+map2.get("beginTime").toString()+"' \n";
                        }
                        if(!"".equals(endTime)){
                            whereSql += " AND '"+map2.get("endTime").toString()+"' >= "+timeFiled+" \n";
                        }
                    }
                }
            }
        }
        whereSql = joinSql + " \n" + whereSql;
        return whereSql;
    }
    @Override
    public HashMap<String, String> getQueryDate(String datePurview) {
        HashMap map = new HashMap();
        String beginTime = "", endTime = "";
        Date date = new Date();
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd 00:00:00");
        if (datePurview.equals("today")) {
            beginTime = sdf.format(date);
            cal.add(Calendar.DAY_OF_YEAR, 1);
            endTime = sdf.format(cal.getTime());
        } else if (datePurview.equals("yesterday")) {
            endTime = sdf.format(date);
            cal.add(Calendar.DAY_OF_YEAR, -1);    //一天
            beginTime = sdf.format(cal.getTime());
        } else if (datePurview.equals("this_week")) {
            int day_of_week = cal.get(Calendar.DAY_OF_WEEK) - 1;
            if (day_of_week == 0)day_of_week = 7;
            cal.add(Calendar.DATE, -day_of_week + 1);
            beginTime = sdf.format(cal.getTime());
            cal.add(Calendar.DATE, 7);
            endTime = sdf.format(cal.getTime());
        } else if (datePurview.equals("last_week")) {
            int day_of_week = cal.get(Calendar.DAY_OF_WEEK) - 1;
            if (day_of_week == 0)day_of_week = 7;
            cal.add(Calendar.DATE, -day_of_week -6);
            beginTime = sdf.format(cal.getTime());
            cal.add(Calendar.DATE, 7);
            endTime = sdf.format(cal.getTime());
        } else if (datePurview.equals("this_month")) {
            beginTime =  UtilTools.fmtDate(cal.getTime(), "yyyy-MM-01 00:00:00");
            cal.add(Calendar.MONTH, 1);
            endTime = UtilTools.fmtDate(cal.getTime(), "yyyy-MM-01 00:00:00");
        } else if (datePurview.equals("last_month")) {
            endTime =  UtilTools.fmtDate(cal.getTime(), "yyyy-MM-01 00:00:00");
            cal.add(Calendar.MONTH, -1);
            beginTime = UtilTools.fmtDate(cal.getTime(), "yyyy-MM-01 00:00:00");
        } else if (datePurview.equals("this_quarter")) {
            int currentMonth = cal.get(Calendar.MONTH) + 1;
            if (currentMonth >= 1 && currentMonth <= 3)
                cal.set(Calendar.MONTH, 0);
            else if (currentMonth >= 4 && currentMonth <= 6)
                cal.set(Calendar.MONTH, 3);
            else if (currentMonth >= 7 && currentMonth <= 9)
                cal.set(Calendar.MONTH, 4);
            else if (currentMonth >= 10 && currentMonth <= 12)
                cal.set(Calendar.MONTH, 9);
            beginTime =  UtilTools.fmtDate(cal.getTime(), "yyyy-MM-01 00:00:00");
            cal.add(Calendar.MONTH,3);
            endTime =  UtilTools.fmtDate(cal.getTime(), "yyyy-MM-01 00:00:00");
        } else if (datePurview.equals("last_quarter")) {
            int currentMonth = cal.get(Calendar.MONTH) + 1;
            if (currentMonth >= 1 && currentMonth <= 3)
                cal.set(Calendar.MONTH, -3);
            else if (currentMonth >= 4 && currentMonth <= 6)
                cal.set(Calendar.MONTH, 0);
            else if (currentMonth >= 7 && currentMonth <= 9)
                cal.set(Calendar.MONTH, 3);
            else if (currentMonth >= 10 && currentMonth <= 12)
                cal.set(Calendar.MONTH, 6);
            beginTime =  UtilTools.fmtDate(cal.getTime(), "yyyy-MM-01 00:00:00");
            cal.add(Calendar.MONTH, 3);
            endTime =  UtilTools.fmtDate(cal.getTime(), "yyyy-MM-01 00:00:00");
        } else if (datePurview.equals("this_year")) {
            beginTime =  UtilTools.fmtDate(cal.getTime(), "yyyy-01-01 00:00:00");
            cal.add(Calendar.YEAR, 1);
            endTime = UtilTools.fmtDate(cal.getTime(), "yyyy-01-01 00:00:00");
        } else if (datePurview.equals("last_year")) {
            endTime =  UtilTools.fmtDate(cal.getTime(), "yyyy-01-01 00:00:00");
            cal.add(Calendar.YEAR, -1);
            beginTime = UtilTools.fmtDate(cal.getTime(), "yyyy-01-01 00:00:00");
        }
//        } else if (datePurview.equals("custom")) {
//            beginTime = r.getBeginDate();
//            endTime = r.getEndDate();
//        }
        map.put("beginTime", beginTime);

        map.put("endTime", endTime);

        return map;
    }

    //将同类数据结构整合在一个sql里面查询，减少数据库操作
    public HashMap findFieldNameMap(List<HashMap> map) {
        HashMap fieldNameMap = new HashMap();
        for (int i = 0; i < map.size(); i++) {
            fieldNameMap.put(map.get(i).get("fieldName"), map.get(i).get("source"));
        }
        return fieldNameMap;
    }


    @Override
    public DataSet findSubReportDataSet(Map map) {
        //模板Id
        String modelId = map.get("modelId") == null ? "" : map.get("modelId").toString();
        //当前用户Id
        String userId = map.get("userId") == null ? "" : map.get("userId").toString();
        return dataSetMap.get("subReport_" + modelId + "_" + userId);  //To change body of implemented methods use File | Settings | File Templates.
    }
}

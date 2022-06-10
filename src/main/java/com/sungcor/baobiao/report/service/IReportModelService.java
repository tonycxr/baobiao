package com.sungcor.baobiao.report.service;

import com.sungcor.baobiao.report.bean.ReportModelBean;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


 public interface IReportModelService {

     List<ReportModelBean> findListByMap(HashMap map);

     Integer findListCountByMap(HashMap map);

     Integer updateStatus(HashMap map);

     Integer insertModel(ReportModelBean reportModelBean);

     Integer copyModel(String id);

     void updateModel(ReportModelBean reportModelBean);

     void updateModelTime(HashMap map);

     List<Map<String,String>> deleteModel(String[] ids);

     ReportModelBean findBeanById(String modelId);

     void updatePermissions(String roles, String modelId);

     List<ReportModelBean> findReportModelList();

     List<ReportModelBean> findListByPara(HashMap map);

     List<ReportModelBean> findModelByIds(List<String> ids);

     List<ReportModelBean> findByName(String name);
}

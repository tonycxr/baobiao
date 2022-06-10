package com.sungcor.baobiao.report.service;

import com.sungcor.baobiao.report.bean.ReportTaskBean;

import java.util.HashMap;
import java.util.List;

public interface IReportTaskService {
    public void insertTask(ReportTaskBean reportTaskBean) throws  Exception;
    public Integer findListCountByMap(HashMap map);
    public List<ReportTaskBean>  findListByMap(HashMap map);
    public void deleteTask(String[] ids);
    public void startOrStop(String[] ids,String status );
    public void startTask(String[] ids);
    public ReportTaskBean findById(int id);
    public void updateTask(ReportTaskBean reportTaskBean)throws Exception;
    void addOrUpdateField(List list);
    public List<HashMap> queryTimingByTaskType(HashMap map);

    // 根据任务ID获取
    public List<HashMap> findTaskModelQuery(String taskId);

    void deleteQuerySetting(String id);

    List initValue(String id);

    void deleteAllQuerySetting(String[] ids);

    public Integer findApplyUserListCountByMap(HashMap map);

    public List<HashMap>  findApplyUserListByMap(HashMap map);

    public String getNextHandleTime (ReportTaskBean reportTaskBean) throws Exception;

    public void updateTaskHandleTime(ReportTaskBean reportTaskBean) throws Exception;
}

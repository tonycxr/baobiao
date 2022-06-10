package com.sungcor.baobiao.report.mapper;

import com.sungcor.baobiao.report.bean.ReportTaskBean;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;

@Mapper
@Component
@Repository
public interface ReportTaskMapper {
    public void insertTask(ReportTaskBean reportTaskBean);
    public Integer findListCountByMap(HashMap map);
    public List<ReportTaskBean> findListByMap(HashMap map);
    public void deleteTask(String[] ids);
    public void startOrStop(HashMap map);
    public void startTask(String[] ids);
    public ReportTaskBean findById(int id);
    public void updateTask(ReportTaskBean reportTaskBean);

    void addOrUpdateField(List list);

    public List<HashMap> findTaskModelQuery(String taskId);

    void deleteQuerySetting(String id);

    List initValue(String id);

    void deleteAllQuerySetting(String[] ids);

    List<ReportTaskBean> findListByModelIds(String[] ids);

    public Integer findApplyUserListCountByMap(HashMap map);

    public java.util.List<java.util.HashMap> findApplyUserListByMap(HashMap map);

    public List<HashMap> queryTimingByTaskType(HashMap map);
}

package com.sungcor.baobiao.report.mapper;

import com.sungcor.baobiao.report.bean.ReportModelBean;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;

@Mapper
@Component
@Repository
public interface ReportModelMapper {
    List<ReportModelBean> findListByMap(HashMap map);

    Integer findListCountByMap(HashMap map);

    Integer updateStatus(HashMap map);

    Integer insertModel(ReportModelBean reportModelBean);

    void updateModel(ReportModelBean reportModelBean);

    void updateModelTime(HashMap map);

    void updateModelCode(ReportModelBean reportModelBean);

    ReportModelBean findBeanById(String modelId);

    void deleteModel(String[] ids);

    void addFunction(HashMap parm);

    void addGroupFunction(HashMap parm);

    void deleteGroupFunction(HashMap parm);

    int findFunction(HashMap parm);

    int findDeleteFunction(HashMap parm);

    void delFunction(HashMap parm);

    List<ReportModelBean> findReportModelList();

    List<ReportModelBean> findListByPara(HashMap map);

    List<ReportModelBean> findModelByIds(List<String> ids);

    List<ReportModelBean> findByName(String name);
}

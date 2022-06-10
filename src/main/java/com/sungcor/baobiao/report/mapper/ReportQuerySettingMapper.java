package com.sungcor.baobiao.report.mapper;

import com.sungcor.baobiao.report.bean.ReportFieldBean;
import com.sungcor.baobiao.report.bean.ReportQuerySettingBean;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;

@Mapper
@Component
@Repository
public interface ReportQuerySettingMapper {
    List<ReportFieldBean> getAll(HashMap params);

    int addQuerySetting(ReportQuerySettingBean reportQuerySettingBean);

    int deleteField2Setting(List list);

    void addField2Setting(List list);

    ReportQuerySettingBean findById(Integer id);

    List<ReportFieldBean> getQueryCondition(String modelId);

    int updateQuerySetting(ReportQuerySettingBean reportQuerySettingBean);

    List getAll2(HashMap map);

    ReportQuerySettingBean findByModelId(String modelId);

    List getSelectValue(String id);

    List getSelectValue2(String id);

    List getSelectValue3(String id);

    List getSelectValue4(String id);

    List getSelectValue5(String id);

    void deleteAllField(String[] ids);

    void deleteAllDateField(String[] ids);

    void copyDateSetting(HashMap map);

    void copyQuerySetting(HashMap map);
}

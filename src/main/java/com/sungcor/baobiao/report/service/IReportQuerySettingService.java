package com.sungcor.baobiao.report.service;


import com.sungcor.baobiao.report.bean.ReportFieldBean;
import com.sungcor.baobiao.report.bean.ReportQuerySettingBean;

import java.util.HashMap;
import java.util.List;

public interface IReportQuerySettingService {

    /**
     * 获取所有查询字段
     *
     * @param params
     * @return
     */
    public List<ReportFieldBean> getAll(HashMap params);

    /**
     * 获取所有已经选择的查询字段
     *
     * @param map
     * @return
     */
    public List<ReportFieldBean> getAll2(HashMap map);

    /**
     * 保存查询设置信息
     *
     * @param reportQuerySettingBean
     * @return
     */
    public int addQuerySetting(ReportQuerySettingBean reportQuerySettingBean);

    /**
     * 删除字段与查询设置信息关联
     *
     * @param list
     * @return
     */
    public boolean deleteField2Setting(List list);

    /**
     * 添加字段与查询设置信息关联
     *
     * @param list
     */
    public void addField2Setting(List list);

    /**
     * 根据ID查询
     *
     * @param id
     * @return
     */
    public ReportQuerySettingBean findById(Integer id);

    /**
     * 获取所有查询条件
     *
     * @return
     */
    public List getQueryCondition(String modelId);

    /**
     * 修改查询设置信息
     *
     * @param reportQuerySettingBean
     * @return
     */
    public int updateQuerySetting(ReportQuerySettingBean reportQuerySettingBean);

    /**
     * 根据modelId查询设置信息
     *
     * @param modelId
     * @return
     */
    public ReportQuerySettingBean findByModelId(String modelId);

    public HashMap getQueryDate(ReportQuerySettingBean r);

    List getSelectValue(String id);

    List getSelectValue2(String id);

    List getSelectValue3(String id);

    List getSelectValue4(String id);

    List getSelectValue5(String id);

    void deleteAllField(String[] ids);

    public void copyQuerySetting(int newId,String oldId);
}

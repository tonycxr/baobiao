package com.sungcor.baobiao.report.service.impl;

import com.sungcor.baobiao.report.mapper.ReportQuerySettingMapper;
import com.sungcor.baobiao.report.bean.ReportFieldBean;
import com.sungcor.baobiao.report.bean.ReportQuerySettingBean;
import com.sungcor.baobiao.report.service.IReportQuerySettingService;
import com.sungcor.baobiao.utils.UtilTools;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 * Date: 16-5-23
 * Time: 下午2:32
 * To change this callate use File | Settings | File callates.
 */

@Transactional
@Service
public class ReportQuerySettingServiceImpl implements IReportQuerySettingService {


    @Autowired
    public ReportQuerySettingMapper reportQuerySettingMapper;

    @Override
    public List<ReportFieldBean> getAll(HashMap params) {
        return reportQuerySettingMapper.getAll(params);
    }

    @Override
    public int addQuerySetting(ReportQuerySettingBean reportQuerySettingBean) {
        return reportQuerySettingMapper.addQuerySetting(reportQuerySettingBean) > 0 ? reportQuerySettingBean.getId() : 0;
    }

    @Override
    public boolean deleteField2Setting(List list) {
        return reportQuerySettingMapper.deleteField2Setting(list) > 0 ? Boolean.TRUE : Boolean.FALSE;
    }

    @Override
    public void addField2Setting(List list) {
        reportQuerySettingMapper.addField2Setting(list);

    }

    @Override
    public ReportQuerySettingBean findById(Integer id) {
        return reportQuerySettingMapper.findById(id);
    }

    @Override
    public List<ReportFieldBean> getQueryCondition(String modelId) {
        return reportQuerySettingMapper.getQueryCondition(modelId);
    }

    @Override
    public int updateQuerySetting(ReportQuerySettingBean reportQuerySettingBean) {
        return reportQuerySettingMapper.updateQuerySetting(reportQuerySettingBean) > 0 ? reportQuerySettingBean.getId() : 0;
    }

    @Override
    public List getAll2(HashMap map) {
        return reportQuerySettingMapper.getAll2(map);
    }

    @Override
    public ReportQuerySettingBean findByModelId(String modelId) {
        return reportQuerySettingMapper.findByModelId(modelId);
    }

    @Override
    public HashMap getQueryDate(ReportQuerySettingBean r) {
        HashMap map = new HashMap();
        String beginTime = "", endTime = "";
        Date date = new Date();
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd 00:00:00");
        String datePurview = r.getDatePurview().toLowerCase();
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
            cal.add(Calendar.MONTH,3);
            endTime =  UtilTools.fmtDate(cal.getTime(), "yyyy-MM-01 00:00:00");
        } else if (datePurview.equals("this_year")) {
            beginTime =  UtilTools.fmtDate(cal.getTime(), "yyyy-01-01 00:00:00");
            cal.add(Calendar.YEAR, 1);
            endTime = UtilTools.fmtDate(cal.getTime(), "yyyy-01-01 00:00:00");
        } else if (datePurview.equals("last_year")) {
            endTime =  UtilTools.fmtDate(cal.getTime(), "yyyy-01-01 00:00:00");
            cal.add(Calendar.YEAR, -1);
            beginTime = UtilTools.fmtDate(cal.getTime(), "yyyy-01-01 00:00:00");
        } else if (datePurview.equals("custom")) {
            beginTime = r.getBeginDate();
            endTime = r.getEndDate();
        }
        map.put("beginTime", beginTime);
        map.put("endTime", endTime);
        return map;
    }

    @Override
    public List getSelectValue(String id) {
        return reportQuerySettingMapper.getSelectValue(id);
    }

    @Override
    public List getSelectValue2(String id) {
        return reportQuerySettingMapper.getSelectValue2(id);
    }

    @Override
    public List getSelectValue3(String id) {
        return reportQuerySettingMapper.getSelectValue3(id);
    }
    @Override
    public List getSelectValue4(String id) {
        return reportQuerySettingMapper.getSelectValue4(id);
    }
    @Override
    public List getSelectValue5(String id) {
        return reportQuerySettingMapper.getSelectValue5(id);
    }
    @Override
    public void deleteAllField(String[] ids) {
        reportQuerySettingMapper.deleteAllField(ids);
    }

    @Override
    public void copyQuerySetting(int newId, String oldId) {
        //保存日期设置
        HashMap map = new HashMap();
        map.put("newId",newId);
        map.put("oldId",oldId);
        //复制时间
        reportQuerySettingMapper.copyDateSetting(map);
        //复制内容
        reportQuerySettingMapper.copyQuerySetting(map);

    }
}

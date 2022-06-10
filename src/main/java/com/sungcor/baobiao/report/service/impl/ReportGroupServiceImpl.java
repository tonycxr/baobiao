package com.sungcor.baobiao.report.service.impl;


import com.sungcor.baobiao.mapper.FunctionMapper;
import com.sungcor.baobiao.report.mapper.ReportGroupMapper;
import com.sungcor.baobiao.report.bean.Function;
import com.sungcor.baobiao.report.bean.ReportGroupBean;
import com.sungcor.baobiao.report.bean.ReportTypeBean;
import com.sungcor.baobiao.report.service.IReportGroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 * Date: 16-5-23
 * Time: 下午2:32
 * To change this template use File | Settings | File Templates.
 */

@Transactional
@Service
public class ReportGroupServiceImpl implements IReportGroupService {


    @Autowired
    public ReportGroupMapper reportGroupMapper;
    @Autowired
    private FunctionMapper functionMapper;

    @Override
    public List<ReportTypeBean> findReportTypeList() {
        return reportGroupMapper.findReportTypeList();  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public List<ReportGroupBean> findReportGroupList()
    {
        return reportGroupMapper.findReportGroupList();
    }

    @Override
    public void addReportGroup(ReportGroupBean reportGroup)
    {
        reportGroupMapper.addReportGroup(reportGroup);
        reportGroup.setCode(ReportGroupBean.MENU_CODE+reportGroup.getId());
        reportGroupMapper.updateReportGroup(reportGroup);

        Function function = new Function(reportGroup.getCode(), reportGroup.getName(),
                "1", "customReport", reportGroup.getId(),
                "", null, null, 800, 600, "1" , "3");
        functionMapper.insertFunction(function);
    }

    @Override
    public void deleteReportGroupById(String[] ids)
    {
        reportGroupMapper.deleteReportGroupById(ids);
        //删除菜单权限
        List list = Arrays.asList(ids);
        functionMapper.deleteReportGroupFunction(list);

    }

    @Override
    public void updateReportGroup(ReportGroupBean reportGroup)
    {
        reportGroupMapper.updateReportGroup(reportGroup);
        List list = new ArrayList();
        list.add(reportGroup.getId());
        functionMapper.deleteReportGroupFunction(list);
        reportGroup.setCode(ReportGroupBean.MENU_CODE+reportGroup.getId());
        Function function = new Function(reportGroup.getCode(), reportGroup.getName(),
                "1", "customReport", reportGroup.getId(),
                "", null, null, 800, 600, "1" , "3");
        functionMapper.insertFunction(function);
    }

    @Override
    public boolean checkReportGroup(String name)
    {
        ReportGroupBean reportGroup = null;
        reportGroup =  reportGroupMapper.findReportGroupByName(name);
        return reportGroup != null;
    }

    @Override
    public ReportGroupBean findReportGroupById(int id)
    {
        return reportGroupMapper.findReportGroupById(id);
    }

    @Override
    public List<Map> findModelNumByGroupId(String[] ids)
    {
        return reportGroupMapper.findModelNumByGroupId(ids);
    }
}

package com.sungcor.baobiao.report.service.impl;


import com.sungcor.baobiao.report.bean.ReportCustomIndexBean;
import com.sungcor.baobiao.report.bean.ReportTypeAttributeBean;
import com.sungcor.baobiao.report.mapper.ReportCustomIndexMapper;
import com.sungcor.baobiao.report.mapper.ReportTypeAttributeMapper;
import com.sungcor.baobiao.report.service.IReportCustomIndexService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by lenovo on 2016/8/16.
 */

@Transactional
@Service
public class ReportCustomIndexImpl implements IReportCustomIndexService
{

    @Autowired
    private ReportCustomIndexMapper reportCustomIndexMapper;
    @Autowired
    private ReportTypeAttributeMapper reportTypeAttributeMapper;

    @Override
    public ReportCustomIndexBean getById(int id)
    {
        return reportCustomIndexMapper.getById(id);
    }

    @Override
    public void addCusIndex(ReportCustomIndexBean bean)
    {
        reportCustomIndexMapper.addCusIndex(bean);
        ReportTypeAttributeBean typeAttributeBean = new ReportTypeAttributeBean();
        typeAttributeBean.setAttributeId(bean.getId()+"");
        typeAttributeBean.setAttributeName(bean.getName());
        typeAttributeBean.setDataSource("custom");
        typeAttributeBean.setStatus("index");
        typeAttributeBean.setTypeId(bean.getTypeId());
        typeAttributeBean.setFieldName("custom_"+bean.getId());
        reportTypeAttributeMapper.insert(typeAttributeBean);
    }

    @Override
    public void updateCusIndex(ReportCustomIndexBean bean)
    {
        reportCustomIndexMapper.updateCusIndex(bean);
        ReportTypeAttributeBean typeAttributeBean = reportTypeAttributeMapper.getReportTypeAttributeByFieldName("custom_"+bean.getId()).get(0);
        typeAttributeBean.setAttributeName(bean.getName());
        reportTypeAttributeMapper.update(typeAttributeBean);
    }

    @Override
    public List<String> deleteCusIndex(String[] ids)
    {
        List<String> list = new ArrayList<String>();
        List<String> useIndexs = new ArrayList<String>();
        for(int i=0;i<ids.length;i++){
            String field = "custom_"+ids[i];
            int count = reportCustomIndexMapper.existIndexByStat(field);
            if(count==0){
                reportTypeAttributeMapper.deleteFieldName(field);
                list.add(ids[i]);
            }else{
                ReportCustomIndexBean bean= reportCustomIndexMapper.getById(Integer.parseInt(ids[i]));
                useIndexs.add(bean.getName());
            }
        }
        if(list.size()>0){
            reportCustomIndexMapper.deleteCusIndex(list);
        }
        return useIndexs;
    }

    @Override
    public List<ReportCustomIndexBean> queryCusIndex(HashMap<String,String> map)
    {
        return reportCustomIndexMapper.queryCusIndex(map);
    }

    @Override
    public int queryCusIndexCount(HashMap<String, String> map)
    {
        return reportCustomIndexMapper.queryCusIndexCount(map);
    }
}

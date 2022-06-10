package com.sungcor.baobiao.report.service.impl;


import com.sungcor.baobiao.report.bean.ReportTypeAttributeBean;
import com.sungcor.baobiao.report.mapper.ReportTypeAttributeMapper;
import com.sungcor.baobiao.report.service.IReportTypeAttributeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: yangqing
 * Date: 16-6-1
 * Time: 上午11:19
 * To change this template use File | Settings | File Templates.
 */
@Transactional
@Service
public class ReportTypeAttributeServiceImpl implements IReportTypeAttributeService {

    private ReportTypeAttributeMapper reportTypeAttributeMapper;

    @Override
    public void insert(ReportTypeAttributeBean reportTypeAttributeBean) {
        reportTypeAttributeMapper.insert(reportTypeAttributeBean);
    }

    @Override
    public void update(ReportTypeAttributeBean reportTypeAttributeBean) {
        reportTypeAttributeMapper.update(reportTypeAttributeBean);
    }

    @Override
    public void delete(int Id) {
        reportTypeAttributeMapper.delete(Id);
    }

    @Override
    public void deleteFieldName(String fieldName) {
        reportTypeAttributeMapper.deleteFieldName(fieldName);
    }

    @Override
    public List<ReportTypeAttributeBean> getReportTypeAttributeByFieldName(String fieldName) {
        return reportTypeAttributeMapper.getReportTypeAttributeByFieldName(fieldName);
    }
}

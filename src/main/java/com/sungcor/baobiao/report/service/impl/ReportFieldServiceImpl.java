package com.sungcor.baobiao.report.service.impl;

import com.sungcor.baobiao.report.bean.ReportFieldBean;
import com.sungcor.baobiao.report.dao.IReportFieldDAO;
import com.sungcor.baobiao.report.service.IReportFieldService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created with IntelliJ IDEA.
 * User: yangqing
 * Date: 16-6-1
 * Time: 上午11:19
 * To change this template use File | Settings | File Templates.
 */
@Transactional
@Service("customReport/reportFieldService")
public class ReportFieldServiceImpl implements IReportFieldService {

    private IReportFieldDAO dao;

    @Override
    public void insert(ReportFieldBean reportFieldBean) {
        dao.insert(reportFieldBean);
    }

    @Override
    public void update(ReportFieldBean reportFieldBean) {
        dao.update(reportFieldBean);
    }

    @Override
    public void delete(int Id) {
       dao.delete(Id);
    }

    @Override
    public ReportFieldBean getReportFieldBean(int Id) {
        return dao.getReportFieldBean(Id);
    }

    @Override
    public ReportFieldBean getReportFieldBeanByFieldName(String fieldName) {
        return dao.getReportFieldBeanByFieldName(fieldName);
    }

    @Override
    public void deleteFieldName(String fieldName) {
        dao.deleteFieldName(fieldName);
    }


}

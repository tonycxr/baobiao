package com.sungcor.baobiao.report.service.impl;

import com.sungcor.baobiao.report.dao.IReportBrowseDAO;
import com.sungcor.baobiao.report.bean.ReportTaskFileBean;
import com.sungcor.baobiao.report.service.IReportBrowseService;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;

@Service
public class ReportBrowseServiceImpl implements IReportBrowseService {

        IReportBrowseDAO reportBrowseDAO;

        @Override
        public Integer findListCountByMap(HashMap map) {
            return reportBrowseDAO.findListCountByMap(map);  //To change body of implemented methods use File | Settings | File Templates.
        }
        @Override
        public List<ReportTaskFileBean> findListByMap(HashMap map) {
            return reportBrowseDAO.findListByMap(map); //To change body of implemented methods use File | Settings | File Templates.
        }
        @Override
        public List<ReportTaskFileBean> search(HashMap params) {
            return reportBrowseDAO.search(params); //To change body of implemented methods use File | Settings | File Templates.
        }

        @Override
        public void insert(ReportTaskFileBean reportTaskFileBean) {
            reportBrowseDAO.insert(reportTaskFileBean);
        }
        @Override
        public void delete(String[] ids){
            reportBrowseDAO.delete(ids);
        }
    }

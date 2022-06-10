package com.sungcor.baobiao.report.service.impl;

import com.sungcor.baobiao.report.bean.ReportMailBean;
import com.sungcor.baobiao.report.bean.ReportMailUserBean;
import com.sungcor.baobiao.report.dao.IReportMailDAO;
import com.sungcor.baobiao.report.service.IReportMailService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;

/**
 * Created by 清 on 2016/6/15.
 */
@Transactional
@Service("customReport/reportMail")
public class ReportMailServiceImpl implements IReportMailService {

    IReportMailDAO iReportMailDAO;

    @Override
    public List<HashMap> findTaskMail(String taskId) {
        return iReportMailDAO.findTaskMail(taskId);  //To change body of implemented methods use File | Settings | File Templates.
    }

    public void insertMail(ReportMailBean reportMailBean){
        String[] ids=reportMailBean.getMailUserId().split(",");
        iReportMailDAO.deleteMail(reportMailBean.getTaskId());
        for(int i=0;i<ids.length;i++){
            reportMailBean.setMailUserId(ids[i]);
            reportMailBean.setExportType(StringUtils.join(reportMailBean.getExportTypes(),","));
            iReportMailDAO.insertMail(reportMailBean);
        }
    }
    public List<ReportMailBean> getByTaskId(int taskId){
        return iReportMailDAO.getByTaskId(taskId);
    }
    public Integer findListCountByMap(HashMap map){
        return iReportMailDAO.findListCountByMap(map);
    }
    public List<ReportMailUserBean>findListByMap(HashMap map){
        return iReportMailDAO.findListByMap(map);
    }
    public void deleteMail(String[] ids){
        iReportMailDAO.deleteMails(ids);
    }
}

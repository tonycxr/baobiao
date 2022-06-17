package com.sungcor.baobiao.report.service.impl;

import com.sungcor.baobiao.report.bean.ReportTaskBean;
import com.sungcor.baobiao.report.mapper.ReportTaskMapper;
import com.sungcor.baobiao.report.service.IReportTaskService;
import com.sungcor.baobiao.report.util.JobUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 * Date: 16-5-23
 * Time: 下午3:52
 * To change this template use File | Settings | File Templates.
 */
@Transactional
@Service("IReportTaskService")
public class ReportTaskServiceImpl implements IReportTaskService {


    @Autowired
    private ReportTaskMapper reportTaskMapper;

    @Autowired
    private JobUtil jobUtil;

    @Override
    public void insertTask(ReportTaskBean reportTaskBean) throws  Exception{

        //保存定时器
        reportTaskBean.setNext_handle_time(this.getNextHandleTime(reportTaskBean));
        reportTaskMapper.insertTask(reportTaskBean);
        //savecusTimer(reportTaskBean);
    }
    public String getNextHandleTime (ReportTaskBean reportTaskBean) throws Exception{
        String val="";
        Date date=new Date();
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        SimpleDateFormat sdf1=new SimpleDateFormat("yyyy-MM-dd ");
        SimpleDateFormat sdf2=new SimpleDateFormat("yyyy-MM-");
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        if(reportTaskBean.getCycle()==0){
            String dayTime=reportTaskBean.getTask_day_time();
            if(date.getTime()<sdf.parse(sdf1.format(date)+dayTime).getTime()){
                val=sdf1.format(date)+dayTime;
            }else {
                val=sdf1.format(new Date(date.getTime()+1000*60*60*24))+dayTime;
            }
        }else if(reportTaskBean.getCycle()==1){
            int week = cal.get(Calendar.DAY_OF_WEEK);
            String dayTime=reportTaskBean.getTask_week_time();
            if((week+"").equals(reportTaskBean.getTask_week_date())){
                if(date.getTime()<sdf.parse(sdf1.format(date)+dayTime).getTime()){
                    val=sdf1.format(date)+dayTime;
                }else{
                    val=sdf1.format(new Date(date.getTime()+1000*60*60*24*7))+dayTime;
                }
            }else if(week<Integer.parseInt(reportTaskBean.getTask_week_date())){
                val=sdf1.format(new Date(date.getTime()+1000*60*60*24*(Integer.parseInt(reportTaskBean.getTask_week_date())-week)))+dayTime;
            }else if(week>Integer.parseInt(reportTaskBean.getTask_week_date())){
                val=sdf1.format(new Date(date.getTime()+1000*60*60*24*(Integer.parseInt(reportTaskBean.getTask_week_date())+7-week)))+dayTime;
            }
        }else if(reportTaskBean.getCycle()==2){
            int month=cal.get(Calendar.MONTH)+1;
            String dayTime=reportTaskBean.getTask_month_time();
            if(date.getTime()<sdf.parse(sdf2.format(date)+dayTime).getTime()){
                val=sdf2.format(date)+dayTime;
            }else{
                Calendar cal1 = Calendar.getInstance();
                cal1.set(Calendar.YEAR,cal.get(Calendar.YEAR));
                cal1.set(Calendar.MONTH,month);
                if(cal1.getActualMaximum(Calendar.DATE)<cal.get(Calendar.DAY_OF_MONTH)){
                    if(month+2>12){
                        val=(cal.get(Calendar.YEAR)+1)+"-0"+(month+2-12)+"-"+dayTime;
                    }else{
                        if(month+2<10){
                            val=(cal.get(Calendar.YEAR))+"-0"+(month+2)+"-"+dayTime;
                        }else{
                            val=(cal.get(Calendar.YEAR))+"-"+(month+2)+"-"+dayTime;
                        }
                    }
                }else{
                    if((month+1)>12){
                        val=(cal.get(Calendar.YEAR)+1)+"-"+(month+1-12)+"-"+dayTime;
                    }else {
                        if(month+1<10){
                            val=(cal.get(Calendar.YEAR))+"-0"+(month+1)+"-"+dayTime;
                        }else{
                            val=(cal.get(Calendar.YEAR))+"-"+(month+1)+"-"+dayTime;
                        }
                    }
                }
            }
        }else if(reportTaskBean.getCycle()==3){
            int month_st=(cal.get(Calendar.MONTH)+1)%3==0?3:(cal.get(Calendar.MONTH)+1)%3;
            String dayTime=reportTaskBean.getTask_quarter_time();
            int month=cal.get(Calendar.MONTH);
            int year=cal.get(Calendar.YEAR);
            if(month_st==Integer.parseInt(reportTaskBean.getTask_quarter_month())){
                if(date.getTime()>=sdf.parse(sdf2.format(date)+dayTime).getTime()){
                    month=cal.get(Calendar.MONTH)+3;
                }
            }else if(month_st<Integer.parseInt(reportTaskBean.getTask_quarter_month())){
                month=cal.get(Calendar.MONTH)+(Integer.parseInt(reportTaskBean.getTask_quarter_month())-month_st);
            }else if(month_st>Integer.parseInt(reportTaskBean.getTask_quarter_month())){
                month=cal.get(Calendar.MONTH)+(Integer.parseInt(reportTaskBean.getTask_quarter_month())+3-month_st);
            }
            if(month>11){
                year=year+1;
                month=month-12;
            }
            Calendar cal1 = Calendar.getInstance();
            cal1.set(Calendar.YEAR,year);
            cal1.set(Calendar.MONTH,month);
            if(cal1.getActualMaximum(Calendar.DATE)<cal.get(Calendar.DAY_OF_MONTH)){
                month+=3;
                if(month>11){
                    year=year+1;
                    month=month-12;
                }
                Calendar cal2 = Calendar.getInstance();
                cal2.set(Calendar.YEAR,year);
                cal2.set(Calendar.MONTH,month);
                if(cal2.getActualMaximum(Calendar.DATE)<cal.get(Calendar.DAY_OF_MONTH)){
                    month+=3;
                    if(month>11){
                        year=year+1;
                        month=month-12;
                    }
                }
            }
            if(month+1<10){
                val=year+"-0"+(month+1)+"-"+dayTime;
            }else {
                val=year+"-"+(month+1)+"-"+dayTime;
            }
        }else if(reportTaskBean.getCycle()==4){
            int year=cal.get(Calendar.YEAR);
            if(date.getTime()<sdf.parse(year+"-"+reportTaskBean.getTask_year_time()).getTime()){
                val=year+"-"+reportTaskBean.getTask_year_time();
            }else{
                year=cal.get(Calendar.YEAR)+1;
                int month=cal.get(Calendar.MONTH);
                Calendar cal1 = Calendar.getInstance();
                cal1.set(Calendar.YEAR,year);
                cal1.set(Calendar.MONTH,month);
                if(cal1.getActualMaximum(Calendar.DATE)<cal.get(Calendar.DAY_OF_MONTH)){
                    year=year+1;
                }
                val=year+"-"+reportTaskBean.getTask_year_time();
            }
        }else if(reportTaskBean.getCycle()==5){
                val=reportTaskBean.getCallTime();
        }
        return  val;
    }
    public String updateNextHandleTime (ReportTaskBean reportTaskBean) throws Exception{
        String val="";
        Date date=new Date();
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        SimpleDateFormat sdf1=new SimpleDateFormat("yyyy-MM-dd ");
        SimpleDateFormat sdf2=new SimpleDateFormat("yyyy-MM-");
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        if(reportTaskBean.getCycle()==0){
            String dayTime=reportTaskBean.getTask_day_time();
                val=sdf1.format(new Date(date.getTime()+1000*60*60*24))+dayTime;
        }else if(reportTaskBean.getCycle()==1){
            String dayTime=reportTaskBean.getTask_week_time();
            val=sdf1.format(new Date(date.getTime()+1000*60*60*24*7))+dayTime;
        }else if(reportTaskBean.getCycle()==2){
            int month=cal.get(Calendar.MONTH)+1;
            String dayTime=reportTaskBean.getTask_month_time();
            Calendar cal1 = Calendar.getInstance();
            cal1.set(Calendar.YEAR,cal.get(Calendar.YEAR));
            cal1.set(Calendar.MONTH,month);
             if(cal1.getActualMaximum(Calendar.DATE)<cal.get(Calendar.DAY_OF_MONTH)){
                   if(month+2>12){
                         val=(cal.get(Calendar.YEAR)+1)+"-0"+(month+2-12)+"-"+dayTime;
                        }else{
                            if(month+2<10){
                                val=(cal.get(Calendar.YEAR))+"-0"+(month+2)+"-"+dayTime;
                            }else{
                                val=(cal.get(Calendar.YEAR))+"-"+(month+2)+"-"+dayTime;
                            }
                        }
             }else{
                        if((month+1)>12){
                            val=(cal.get(Calendar.YEAR)+1)+"-"+(month+1-12)+"-"+dayTime;
                        }else {
                            if(month+1<10){
                                val=(cal.get(Calendar.YEAR))+"-0"+(month+1)+"-"+dayTime;
                            }else{
                                val=(cal.get(Calendar.YEAR))+"-"+(month+1)+"-"+dayTime;
                            }
                        }
              }
        }else if(reportTaskBean.getCycle()==3){
            String dayTime=reportTaskBean.getTask_quarter_time();
            int month=cal.get(Calendar.MONTH)+3;
            int year=cal.get(Calendar.YEAR);
            if(month>11){
                year=year+1;
                month=month-12;
            }
            Calendar cal1 = Calendar.getInstance();
            cal1.set(Calendar.YEAR,year);
            cal1.set(Calendar.MONTH,month);
            if(cal1.getActualMaximum(Calendar.DATE)<cal.get(Calendar.DAY_OF_MONTH)){
                month=cal.get(Calendar.MONTH)+6;
                if(month>11){
                    year=year+1;
                    month=month-12;
                }
                Calendar cal2 = Calendar.getInstance();
                cal2.set(Calendar.YEAR,year);
                cal2.set(Calendar.MONTH,month);
                if(cal2.getActualMaximum(Calendar.DATE)<cal.get(Calendar.DAY_OF_MONTH)){
                    month=cal.get(Calendar.MONTH)+9;
                    if(month>11){
                        year=year+1;
                        month=month-12;
                    }
                }
            }
            if(month+1<10){
                val=year+"-0"+(month+1)+"-"+dayTime;
            }else {
                val=year+"-"+(month+1)+"-"+dayTime;
            }
        }else if(reportTaskBean.getCycle()==4){
            int year=cal.get(Calendar.YEAR)+1;
            int month=cal.get(Calendar.MONTH);
            Calendar cal1 = Calendar.getInstance();
            cal1.set(Calendar.YEAR,year);
            cal1.set(Calendar.MONTH,month);
            if(cal1.getActualMaximum(Calendar.DATE)<cal.get(Calendar.DAY_OF_MONTH)){
                year=year+1;
            }
            val=year+"-"+reportTaskBean.getTask_year_time();
        }else if(reportTaskBean.getCycle()==5){
            val=reportTaskBean.getCallTime();
        }
        return  val;
    }

    public void saveTimer(ReportTaskBean taskBean){

        try {
            String code = "customReportTask_"+taskBean.getId();

            int cycle = taskBean.getCycle();
            String month = "*";
            String days = "*";
            String week = "?";

            String year = null;
            String[] time = null;
            Date beginTime = null;
            Date endTime = null;

            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            if(taskBean.getEffectiveStartTime()!=null && !"".equals(taskBean.getEffectiveStartTime())){
                beginTime = sdf.parse(taskBean.getEffectiveStartTime());
            }if(taskBean.getEffectiveEndTime()!=null && !"".equals(taskBean.getEffectiveEndTime())){
                endTime = sdf.parse(taskBean.getEffectiveEndTime());
            }
            if(cycle == 0){
                //日报
                time = taskBean.getTask_day_time().split(":");

            }else if(cycle ==1){
                //周报
                days="?";
                week = taskBean.getTask_week_date();
                time = taskBean.getTask_week_time().split(":");
            }else if(cycle ==2){
                //月报
                String[] str = taskBean.getTask_month_time().split(" ");
                days = str[0];
                time = str[1].split(":");

            }else if(cycle ==3){
                //季报
                int quarterMonth = Integer.parseInt(taskBean.getTask_quarter_month());
                month = quarterMonth+","+(quarterMonth+3)+","+(quarterMonth+6)+","+(quarterMonth+9);
                String[] str = taskBean.getTask_quarter_time().split(" ");
                days = str[0];
                time = str[1].split(":");
            }else if(cycle ==4){
                //年报
                String[] str = taskBean.getTask_year_time().split(" ");
                month = str[0].split("-")[0];
                days = str[0].split("-")[1];
                time = str[1].split(":");

            }else if(cycle ==5){
                //一次性
                String[] str = taskBean.getCallTime().split(" ");
                String[] str1 = str[0].split("-");
                time = str[1].split(":");
                year=str1[0];
                month=str1[1];
                days=str1[2];
            }
            String hour = time[0];
            String minute = time[1];
            String second = time[2];
//            jobUtil.customJob(code,CustomReportJob.class,year,month,days,week,hour,minute,second,beginTime,endTime,String.valueOf(taskBean.getStatus()));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void deleteTime(String[] ids){
        try {
            for(int i=0;i<ids.length;i++){
                jobUtil.deleteJob("customReportTask_"+ids[i]);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public Integer findListCountByMap(HashMap map) {
        return reportTaskMapper.findListCountByMap(map);  //To change body of implemented methods use File | Settings | File Templates.
    }
    @Override
    public List<ReportTaskBean> findListByMap(HashMap map) {
        return reportTaskMapper.findListByMap(map);  //To change body of implemented methods use File | Settings | File Templates.
    }
    @Override
    public void deleteTask(String[] ids){
        reportTaskMapper.deleteTask(ids);
        //删除定时器
        deleteTime(ids);
    }
    @Override
    public void startOrStop(String[] ids,String status ){
        try {
            HashMap map = new HashMap();
            map.put("ids", ids);
            map.put("status", status);
            reportTaskMapper.startOrStop(map);
            //启用禁用定时器
            if("0".equals(status)){
                //禁用定时器
                for(int i=0;i<ids.length;i++){
                    jobUtil.pauseJob("customReportTask_"+ids[i]);
                }
            }else {
                //启用定时器
                for(int i=0;i<ids.length;i++){
                    jobUtil.resumeJob("customReportTask_"+ids[i]);
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }

    }
    @Override
    public void startTask(String[] ids){
        reportTaskMapper.startTask(ids);
    }
    @Override
    public ReportTaskBean findById(int id){
        return reportTaskMapper.findById(id);
    }
    @Override
    public void updateTask(ReportTaskBean reportTaskBean) throws Exception{
        reportTaskBean.setNext_handle_time(this.getNextHandleTime(reportTaskBean));
        reportTaskMapper.updateTask(reportTaskBean);
        //saveTimer(reportTaskBean);
    }
    @Override
    public void updateTaskHandleTime(ReportTaskBean reportTaskBean) throws Exception{
        reportTaskBean.setNext_handle_time(this.updateNextHandleTime(reportTaskBean));
        reportTaskMapper.updateTask(reportTaskBean);
        //saveTimer(reportTaskBean);
    }
    @Override
    public void addOrUpdateField(List list) {
        reportTaskMapper.addOrUpdateField(list);
    }

    @Override
    public List<HashMap> findTaskModelQuery(String taskId) {
        List<HashMap> map= reportTaskMapper.findTaskModelQuery(taskId);
        return map;
    }
    public List<HashMap> queryTimingByTaskType(HashMap map){
        return reportTaskMapper.queryTimingByTaskType(map);
    }
    @Override
    public void deleteQuerySetting(String id) {
        reportTaskMapper.deleteQuerySetting(id);
    }

    @Override
    public List initValue(String id) {
       return  reportTaskMapper.initValue(id);
    }

    @Override
    public void deleteAllQuerySetting(String[] ids) {
        reportTaskMapper.deleteAllQuerySetting(ids);
    }

    @Override
    public Integer findApplyUserListCountByMap(HashMap map) {
        return reportTaskMapper.findApplyUserListCountByMap(map);  //To change body of implemented methods use File | Settings | File Templates.
    }
    @Override
    public List<HashMap> findApplyUserListByMap(HashMap map) {
        return reportTaskMapper.findApplyUserListByMap(map);  //To change body of implemented methods use File | Settings | File Templates.
    }
}

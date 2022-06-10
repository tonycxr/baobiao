package com.sungcor.baobiao.report.util;

import com.sungcor.baobiao.report.bean.JobExpression;
import com.sungcor.baobiao.report.bean.TimmerVO;
import com.sungcor.baobiao.report.dao.ITimmerDAO;
import org.quartz.JobDataMap;
import org.quartz.JobDetail;
import org.quartz.JobKey;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SchedulerFactory;
import org.quartz.impl.JobDetailImpl;
import org.quartz.impl.StdSchedulerFactory;
import org.quartz.impl.triggers.CronTriggerImpl;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Service("JobUtil")
public class JobUtil implements ApplicationContextAware {

    public static ApplicationContext ctx;
    private static org.quartz.SchedulerFactory factory = new StdSchedulerFactory();
    public JobUtil() throws SchedulerException,Exception {
    }

    ITimmerDAO timmerDAO;

    public ITimmerDAO getTimmerDAO() {
        return timmerDAO;
    }

    public void setTimmerDAO(ITimmerDAO timmerDAO) {
        this.timmerDAO = timmerDAO;
    }

    public void close() {

    }

    /**
     * @用途 从数据库中恢复Job
     */
    public void init() throws Exception {

        // 循环恢复

        java.util.List<TimmerVO> timmerList = timmerDAO.queryJob(null);

        for (TimmerVO itm : timmerList) {

            try{
                Class timmerClass = Class.forName(itm.getClassName());
                this.processJob(itm.getCode(), timmerClass, itm.getExpression(),
                        itm.getBeginTime(), itm.getEndTime());
            }catch (Exception e){
                e.printStackTrace();
            }

        }

    }

    /**
     * @用途 定义一个按照间隔时间执行Job的定时器
     * @param code
     *            - 定时器标识（同一个标识，如果重新进行定义，则将修改定时器的执行间隔时间）
     * @param jobClass
     *            - 定时代码
     * @param spaceSecond
     *            - 间隔时间（秒）
     * @throws Exception
     */
    public void simpleJob(String code, Class jobClass, int spaceSecond)
            throws Exception {
        String expression = "0/" + spaceSecond + " * * * * ?";

        cronJob(code, jobClass, expression, null, null);
    }

    /**
     * @用途 按天执行Job
     * @param code
     *            - Job主键
     * @param jobClass
     *            - 执行对象
     * @param hour
     *            - 执行时间，时
     * @param minute
     *            - 执行时间，分
     * @param second
     *            - 执行时间，秒
     * @param beginDate
     *            - 有效期起，为null代表从现在开始
     * @param endDate
     *            - 有效期止，为null代表无结束时间
     */
    public void dailyJob(String code, Class jobClass, int hour, int minute,
                         int second, java.util.Date beginDate, java.util.Date endDate)
            throws Exception {

        JobExpression je = new JobExpression();

        je.setHour(String.valueOf(hour));
        je.setMinute(String.valueOf(minute));
        je.setSecond(String.valueOf(second));

        String expression = je.generateDailyFixed();

        cronJob(code, jobClass, expression, beginDate, endDate);
    }

    /**
     * @用途 按周运行
     * @param code
     *            - Job主键
     * @param jobClass
     *            - 执行对象
     * @param hour
     *            - 执行时间，时
     * @param minute
     *            - 执行时间，分
     * @param second
     *            - 执行时间，秒
     * @param weekDay
     *            - 每周执行的时间（1-7）
     * @param beginDate
     *            - 有效期起，为null代表从现在开始
     * @param endDate
     *            - 有效期止，为null代表无结束时间
     * @throws Exception
     */
    public void weeklyJob(String code, Class jobClass, int hour, int minute,
                          int second, java.util.List<String> weekDay,
                          java.util.Date beginDate, java.util.Date endDate) throws Exception {
        JobExpression je = new JobExpression();

        je.setHour(String.valueOf(hour));
        je.setMinute(String.valueOf(minute));
        je.setSecond(String.valueOf(second));

        String weekStr = "";
        int dayCnt = weekDay.size();
        for (int i = 0; i < dayCnt; i++) {
            if (i != 0) {
                weekStr += ",";
            }
            weekStr += weekDay.get(i);
        }

        je.setWeek(weekStr);

        String expression = je.generateWeeklyFixed();

        cronJob(code, jobClass, expression, beginDate, endDate);
    }

    /**
     * @用途 按月执行Job
     * @param code
     *            - Job主键
     * @param jobClass
     *            - 执行对象
     * @param hour
     *            - 执行时间，时
     * @param minute
     *            - 执行时间，分
     * @param second
     *            - 执行时间，秒
     * @param monthlyDay
     *            - 每月的执行日期，如果要最后一天执行，传入L
     * @param beginDate
     *            - 有效期起，为null代表从现在开始
     * @param endDate
     *            - 有效期止，为null代表无结束时间
     * @throws Exception
     */
    public void monthlyJob(String code, Class jobClass, int hour, int minute,
                           int second, java.util.List<String> monthlyDay,
                           java.util.Date beginDate, java.util.Date endDate) throws Exception {
        JobExpression je = new JobExpression();

        je.setHour(String.valueOf(hour));
        je.setMinute(String.valueOf(minute));
        je.setSecond(String.valueOf(second));

        String str = "";
        int dayCnt = monthlyDay.size();
        for (int i = 0; i < dayCnt; i++) {
            if (i != 0) {
                str += ",";
            }
            str += monthlyDay.get(i);
        }

        je.setDay(str); // 设置可以执行的日

        String expression = je.generateMonthlyFixed();

        cronJob(code, jobClass, expression, beginDate, endDate);
    }

    /**
     * @用途 按照季度执行Job
     * @param code
     * @param jobClass
     * @param hour
     * @param minute
     * @param second
     * @param monthlyDay
     *            ，要执行的日，如果要最后一天执行，传入L
     * @param monthly
     *            ，数组中的数据只能是 1-3
     * @param beginDate
     * @param endDate
     * @throws Exception
     */
    public void quarterlyJob(String code, Class jobClass, int hour, int minute,
                             int second, java.util.List<String> monthlyDay,
                             java.util.List<Integer> monthly, java.util.Date beginDate,
                             java.util.Date endDate) throws Exception {
        JobExpression je = new JobExpression();

        je.setHour(String.valueOf(hour));
        je.setMinute(String.valueOf(minute));
        je.setSecond(String.valueOf(second));

        String str = "";
        int dayCnt = monthlyDay.size();
        for (int i = 0; i < dayCnt; i++) {
            if (i != 0) {
                str += ",";
            }
            str += monthlyDay.get(i);
        }
        je.setDay(str); // 设置可以执行的日

        String monthStr = "";
        int monthCnt = monthly.size();
        for (int i = 0; i < monthCnt; i++) {
            if (i != 0) {
                monthStr += ",";
            }

            int d1 = monthly.get(i);
            int d2 = d1 + 3;
            int d3 = d2 + 3;
            int d4 = d3 + 3;

            monthStr += d1;
            monthStr += "," + d2;
            monthStr += "," + d3;
            monthStr += "," + d4;
        }
        je.setMonth(monthStr);// 设置可以执行的月

        String expression = je.generateQuarterlyFixed();

        cronJob(code, jobClass, expression, beginDate, endDate);
    }

    /**
     * @用途 按照年执行Job
     * @param code
     * @param jobClass
     * @param hour
     * @param minute
     * @param second
     * @param monthlyDay
     *            ，要执行的日，如果要最后一天执行，传入L
     * @param monthly
     *            ，数组中的数据只能是 1-12
     * @param beginDate
     * @param endDate
     * @throws Exception
     */
    public void yearJob(String code, Class jobClass, int hour, int minute,
                        int second, java.util.List<String> monthlyDay,
                        java.util.List<Integer> monthly, java.util.Date beginDate,
                        java.util.Date endDate) throws Exception {
        JobExpression je = new JobExpression();

        je.setHour(String.valueOf(hour));
        je.setMinute(String.valueOf(minute));
        je.setSecond(String.valueOf(second));

        String str = "";
        int dayCnt = monthlyDay.size();
        for (int i = 0; i < dayCnt; i++) {
            if (i != 0) {
                str += ",";
            }
            str += monthlyDay.get(i);
        }
        je.setDay(str); // 设置可以执行的日

        String monthStr = "";
        int monthCnt = monthly.size();
        for (int i = 0; i < monthCnt; i++) {
            if (i != 0) {
                monthStr += ",";
            }
            monthStr += monthly.get(i);
        }
        je.setMonth(monthStr);// 设置可以执行的月


        String expression = je.generateYearFixed();

        cronJob(code, jobClass, expression, beginDate, endDate);
    }
    /**
     * @用途 增加一个自定义Job
     * @param code
     * @param jobClass
     * @param year
     * @param month
     * @param day
     * @param hour
     * @param minute
     * @param second
     * @param beginDate
     * @param endDate
     * @throws Exception
     */
    public void curstomJob(String code, Class jobClass, int year, int month,
                           int day, int hour, int minute, int second,
                           java.util.Date beginDate, java.util.Date endDate) throws Exception {
        JobExpression je = new JobExpression();

        je.setYear(String.valueOf(year));
        je.setMonth(String.valueOf(month));
        je.setDay(String.valueOf(day));

        je.setHour(String.valueOf(hour));
        je.setMinute(String.valueOf(minute));
        je.setSecond(String.valueOf(second));

        String expression = je.generateCustom();

        cronJob(code, jobClass, expression, beginDate, endDate);
    }

    public void curstomJob(String code, Class jobClass, String year, String month,
                           String day,String week, String hour, String minute, String second,
                           java.util.Date beginDate, java.util.Date endDate) throws Exception {
        JobExpression je = new JobExpression();

        je.setYear(year);
        je.setMonth(month);
        je.setDay(day);

        je.setHour(String.valueOf(hour));
        je.setMinute(String.valueOf(minute));
        je.setSecond(String.valueOf(second));

        je.setWeek(week);

        String expression = je.generateCustom();

        cronJob(code, jobClass, expression, beginDate, endDate);
    }

    //weixm 2016-06-13 增加状态字段
    public void customJob(String code, Class jobClass, String year, String month,
                          String day,String week, String hour, String minute, String second,
                          java.util.Date beginDate, java.util.Date endDate,String states) throws Exception {
        JobExpression je = new JobExpression();

        je.setYear(year);
        je.setMonth(month);
        je.setDay(day);

        je.setHour(String.valueOf(hour));
        je.setMinute(String.valueOf(minute));
        je.setSecond(String.valueOf(second));

        je.setWeek(week);

        String expression = je.generateCustom();

        cronJob(code, jobClass, expression, beginDate, endDate,states);
    }

    /**
     * @用途 删除一个Job
     * @param code
     * @throws Exception
     */
    public void deleteJob(String code) throws Exception {

        TimmerVO vo = new TimmerVO();
        vo.setCode(code);
        this.timmerDAO.deleteJob(vo);

        SchedulerFactory schedulerFactory = new StdSchedulerFactory();
        Scheduler scheduler = schedulerFactory.getScheduler();

        scheduler.deleteJob(new JobKey(code));
    }

    /**
     * @用途 暂停一个Job的执行
     * @param code
     * @throws Exception
     */
    public void pauseJob(String code) throws Exception {

        TimmerVO vo = getTimmerVO(code);
        if (vo != null) {
            vo.setStatus("2");
            presistJob(vo);
        }

        SchedulerFactory schedulerFactory = new StdSchedulerFactory();
        Scheduler scheduler = schedulerFactory.getScheduler();

        scheduler.pauseJob(new JobKey(code));

    }

    /**
     * @用途 恢复Job
     * @param code
     * @throws Exception
     */
    public void resumeJob(String code) throws Exception {

        TimmerVO vo = getTimmerVO(code);
        if (vo != null) {
            vo.setStatus("1");
            presistJob(vo);
        }

        SchedulerFactory schedulerFactory = new StdSchedulerFactory();
        Scheduler scheduler = schedulerFactory.getScheduler();

        scheduler.resumeJob(new JobKey(code));
    }

    /**
     * @用途 自定义复杂的Job
     * @param code
     * @param jobClass
     * @param ex
     * @param beginDate
     * @param endDate
     * @throws Exception
     */
    public void cronJob(String code, Class jobClass, String ex,
                        java.util.Date beginDate, java.util.Date endDate) throws Exception {

        try {
            presistJob(code, jobClass, ex, beginDate, endDate, "1");

            processJob(code, jobClass, ex, beginDate, endDate);
        } catch (Exception e) {
            e.printStackTrace(System.out);
        }
    }

    //weixm 增加状态字段
    public void cronJob(String code, Class jobClass, String ex,
                        java.util.Date beginDate, java.util.Date endDate,String states) throws Exception {

        try {
            presistJob(code, jobClass, ex, beginDate, endDate, states);
            if(states.equals("1")){
                //持续化job
                processJob(code, jobClass, ex, beginDate, endDate);
            }else{
                //暂停job
                pauseJob(code);
            }
        } catch (Exception e) {
            e.printStackTrace(System.out);
        }
    }

    /**
     * @用途 持久化Job信息到数据库中
     * @param code
     * @param jobClass
     * @param ex
     * @param beginDate
     * @param endDate
     */
    private void presistJob(String code, Class jobClass, String ex,
                            java.util.Date beginDate, java.util.Date endDate, String status) {
        // 存储Job信息
        TimmerVO vo = new TimmerVO();
        vo.setCode(code);
        vo.setClassName(jobClass.getName());
        vo.setExpression(ex);
        vo.setBeginTime(beginDate);
        vo.setEndTime(endDate);
        vo.setUpdateTime(new java.util.Date());
        vo.setStatus(status);

        presistJob(vo);
    }

    private void presistJob(TimmerVO vo) {

//        java.util.List<TimmerVO> list = this.timmerDAO.queryJob(vo);
        //weixm 改成查询所有定时器
        java.util.List<TimmerVO> list = this.timmerDAO.queryJob1(vo);
        if (list.size() > 0) {
            // 该实例已经存在，当前为修改
            this.timmerDAO.updateJob(vo);
        } else {
            // 该实例不存在，当前为新增
            this.timmerDAO.insertJob(vo);
        }
    }

    private TimmerVO getTimmerVO(String code) {
        TimmerVO vo = new TimmerVO();
        vo.setCode(code);
        java.util.List<TimmerVO> timmers = this.timmerDAO.queryJob(vo);
        if (timmers.size() > 0) {
            vo = timmers.get(0);
        } else {
            vo = null;
        }

        return vo;
    }

    /**
     * @用途 处理Job信息
     * @param code
     * @param jobClass
     * @param ex
     * @param beginDate
     * @param endDate
     * @throws Exception
     */
    private void processJob(String code, Class jobClass, String ex,
                            java.util.Date beginDate, java.util.Date endDate) throws Exception {
        try {
            Scheduler scheduler = factory.getScheduler();
            scheduler.deleteJob(new JobKey(code));
            // 系统当前时间10秒后
            long startTime = System.currentTimeMillis() + 1000L;
            CronTriggerImpl trigger = new org.quartz.impl.triggers.CronTriggerImpl();
            trigger.setName(code);

            trigger.setCronExpression(ex);

            java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat(
                    "yyyy-MM-dd HH:mm:ss");

            if (beginDate != null) {
                System.out.println("#################################################");
                System.out.println(sdf.format(beginDate));
                System.out.println("#################################################");

                if(beginDate.getTime() < new java.util.Date().getTime()){
                    beginDate = new java.util.Date();
                }

                //beginDate = new java.util.Date(beginDate.getTime());

                trigger.setStartTime(beginDate);
            } else {
                trigger.setStartTime(new Date());
            }

            if (endDate != null) {

//                endDate = new java.util.Date(endDate.getTime());
                //定时器有效期加1
                Calendar cal = Calendar.getInstance();
                cal.setTime(endDate);
                cal.add(Calendar.DATE, 1);
                endDate = cal.getTime();
                trigger.setEndTime(endDate);
            }
            if(endDate!=null&&beginDate!=null){
                if(endDate.getTime()<beginDate.getTime()){
                    return;
                }
            }


            JobDetail jobDetail = new JobDetailImpl();

            ((JobDetailImpl) jobDetail).setJobClass(jobClass);
            ((JobDetailImpl) jobDetail).setName(code);

            JobDataMap jdm = new JobDataMap();
            jdm.put("code", code);
            ((JobDetailImpl) jobDetail).setJobDataMap(jdm);



            scheduler.scheduleJob(jobDetail, trigger);
            //定时器延迟启动60秒
            scheduler.startDelayed(60);
//            scheduler.start();

        } catch (Exception e) {
            System.out.print("beginDate="+beginDate+",endDate="+endDate);
            e.printStackTrace(System.out);
        }
    }

    @Override
    public void setApplicationContext(ApplicationContext arg0)
            throws BeansException {
        // TODO Auto-generated method stub
        ctx = arg0;
    }

    /**
     * @用途 根据开始字符串模糊匹配查询并删除job
     * @param code
     */
    public List<TimmerVO> queryJobByCodeStartFuzzy(String code) {
        TimmerVO vo = new TimmerVO();
        vo.setCode(code);
        return  timmerDAO.queryJobByCodeStartFuzzy(vo);
    }

    public void deleteJobByCodeStartFuzzy(String  code) throws Exception{
        this.deleteJob(this.queryJobByCodeStartFuzzy(code));
    }

    public void deleteJob(List<TimmerVO> joblist) throws Exception{
        if (null != joblist){
            for (TimmerVO vo : joblist){
                this.deleteJob(vo.getCode());
            }
        }

    }

    /*
     * public static void main(String[] argv) throws Exception { JobUtil ju =
     * new JobUtil(); ju.simpleJob("TEST", JobTest.class, 2);
     * System.out.println("Start End");
     *
     * // ju.deleteJob("TEST");
     *
     * }
     */
}

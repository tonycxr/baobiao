package com.sungcor.baobiao.report.bean;

import lombok.Data;

/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 * Date: 16-6-1
 * Time: 上午11:18
 * To change this template use File | Settings | File Templates.
 */
@Data
public class ReportTaskBean {
    private Integer id;
    private String name;
    private Integer typeId;
    private String typeName;
    private Integer groupId;
    private String groupName;
    private Integer modelId;
    private String modelName;
    private Integer cycle;
    private String countRange;
    private String countStartTime;
    private String countEndTime;
    private Integer taskType;
    private String taskTypeName;
    private String callTime;
    private String effectiveStartTime;
    private String effectiveEndTime;
    private Integer status;
    private String detail;
    private String createUser;
    private String createUserName;
    private String createTime;
    private String task_day_date;
    private String task_day_time;
    private String task_week_date;
    private String task_week_time;
    private String task_month_time;
    private String task_quarter_month;
    private String task_quarter_time;
    private String task_year_time;
    private String next_handle_time;

}

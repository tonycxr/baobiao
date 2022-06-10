package com.sungcor.baobiao.report.bean;

import lombok.Data;

/**
 * Created by 清 on 2016/6/15.
 */
@Data
public class ReportMailBean {
    private int id;
    private int taskId;
    private String taskName;
    private String enable;
    private String mailUserId;
    private String mailUserName;
    private String exportType;
    private String[] exportTypes;
}

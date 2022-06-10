package com.sungcor.baobiao.report.bean;

import lombok.Data;

@Data
public class TimmerVO {

    private String code;
    private String className;
    private String expression;
    private String status;

    private java.util.Date beginTime;
    private java.util.Date endTime;

    private java.util.Date updateTime;

}

package com.sungcor.baobiao.report.bean;

import lombok.Data;

@Data
public class ReportCustomIndexBean {
    private int id;
    private int typeId;
    private String name;
    private String expressionIndex;
    private String expressionField;
    private String expressionName;
    private String suffix;
    private int status;
    private String createUser;
    private String createUserName;
    private String createTime;
    private String modifyUser;
    private String modifyUserName;
    private String modifyTime;
    private String typeName;
}

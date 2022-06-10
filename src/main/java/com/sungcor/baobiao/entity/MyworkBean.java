package com.sungcor.baobiao.entity;

import lombok.Data;

import java.util.Date;

@Data
public class MyworkBean {
    private String caseNo;
    private String subject;
    private String states;
    private String currentNode;
    private Integer requestType;
    private String creator;
    private String createTime;
    private String priority;
    private String product;
    private String processInstanceId;
    private String type;
    private String tableName;
    private String previousPerson;
    private String serviceCategoryID;
    private String serviceCategoryName;
    private String previousTime;
    private String counts;
    private String urgency;
    private String bpmSigning;
    private Date createTimeDate;
    private Date previousTimeDate;
    private Integer reserveTime; //剩余时间
    private long targetSLAResponseTime=0;    //sla响应时间
    private long targetSLADealTime=0;             //sla解决时间
    private long targetOLAResponseTime=0;    //ola响应时间
    private long targetOLADealTime=0;             //ola解决时间
    private long remainderResponseTime=0;//剩余响应时间
    private long remainderDealTime=0;         //剩余解决时间
    private long remainderTotal=0;               //工单剩余总的处理时间
    private Integer taskPriority;
    private String taskPriorityName;
    private String surPlustime;
    private String slaStatus;
    private String url;
}

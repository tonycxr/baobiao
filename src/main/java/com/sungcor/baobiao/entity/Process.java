package com.sungcor.baobiao.entity;

import lombok.Data;

import java.util.Date;

@Data
public class Process {
    private Integer id;

    //id字符串 用于oracle映射返回id值
    private String idstr;

    private String code;

    private String name;

    /**
     * 流程的英文名称，作为查找存储流程定义文件时候用
     */
    private String pin;

    private String creatorId;

    private UserInfoBean creator;

    private Date createTime;

    private String updatorId;

    private UserInfoBean updator;

    private Date updateTime;

    /**
     * '删除标识，0为未删除，1为已删除',
     */
    private Integer deleteFlag;

    private Integer  processDefinitionID;

    private Integer currentVersion;

    private ProcessDefinition currentProcessDefinition;

    private Integer lastVersion;

    private Integer formID;

    private FMForm form;

    private String formName;

    private String formVersion;


    private String description;

    private Integer organizationId;


    //对应流程模板ID
    private Integer processTemplateId;

    private ServiceCategory serviceCategory;

    private String updateTimeStr;
    private String organizationName;


}

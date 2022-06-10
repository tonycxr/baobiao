package com.sungcor.baobiao.entity;

import lombok.Data;

@Data
public class FMForm extends Entity{
    private String code;

    private Integer type;

    private int policyID;

    private int orgID;

    private String orgName;

    private int versionID;

    private String status;

    private int parentID;

    private String instanceTableName;

    private String versionNO;

    private String strModifyTime;

    private String label;//显示字段名称
}

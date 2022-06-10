package com.sungcor.baobiao.entity;

import lombok.Data;

import java.util.Date;

@Data
public class CaseVo {
    private String id;

    private String caseNo;

    private String subject;

    private String priority;

    private Date createTime;

    private String creator;

    private String createTimeStr;
}

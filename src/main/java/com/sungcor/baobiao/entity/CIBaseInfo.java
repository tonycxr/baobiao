package com.sungcor.baobiao.entity;

import lombok.Data;

import java.util.Date;

@Data
public class CIBaseInfo {
    private Integer id;
    private String categoryId;    //分类ID

    private String categoryName;

    private Integer auditFlag;     //是否审核 0表示未审核，1审核

    private Integer deleteFlag;

    private String creator;

    private Date createTime;

    private String createTimeString;

    private String updateTimeString;

    private String updater;

    private Date updateTime;

    private String flag;

    private String code;

    private String name;

    private String statusId;

    private String statusName;

    private String adminId;

    private String adminIdName;
    private String model;  //型号

    private String serial;  //序列号

    private String vendor;   //厂商

    private  Integer citype;

    private Integer waitFlag;

    private Integer voucherId;

    private Integer citypeT;

    private Integer waitFlagT;

    private Integer voucherIdT;

    private String areaId;

    private String areaName;

    private String manager;
    private String reconcile;

    private int storeId;

    private  String usability;

    private  Integer audit;
    private  String falgid;
    private  String sflag ;
}

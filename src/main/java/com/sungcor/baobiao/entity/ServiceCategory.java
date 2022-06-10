package com.sungcor.baobiao.entity;

public class ServiceCategory {
    final public static int INCIDENT = 2;
    final public static int PROBLEM = 4;
    final public static int CHANGE = 3;
    final public static int PUBLISH = 5;
    final public static int TASK = 6;
    final public static int SERVICEREQUEST = 1;

    private Integer id; //主键ID
    private String code; //编号
    private String name; //名称
    private String sysFlag; //系统标识

    private int number;//用于无效工单

    public Integer getId() {
        return id;
    }
    public void setId(Integer id) {
        this.id = id;
    }
    public String getCode() {
        return code;
    }
    public void setCode(String code) {
        this.code = code;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getSysFlag() {
        return sysFlag;
    }
    public void setSysFlag(String sysFlag) {
        this.sysFlag = sysFlag;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }
}


package com.sungcor.baobiao.entity;

import lombok.Data;

@Data
public class User {
    private String id;

    private String loginid;

    private String name;

    private String password;

    private String usercode;

    private String sex;

    private String birthday;

    private String organizationid;

    private String email;

    private String mobile;

    private String phone;

    private String active;

    private String available;

    private String  operatetype;

    private String[] areaid;//区域ID

    private String[] areaname;//区域名称

    private String remark;//用户描述

    private String photoPath;//头像路径

    private String areaId;//区域ID

}

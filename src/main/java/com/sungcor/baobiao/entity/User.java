package com.sungcor.baobiao.entity;

import lombok.Data;

@Data
public class User {
    String id;

    String loginid;

    String name;

    String password;

    String usercode;

    String sex;

    String birthday;

    String organizationid;

    String email;

    String mobile;

    String phone;

    String active;

    String available;

    String  operatetype;

    String[] areaid;//区域ID

    String[] areaname;//区域名称

    private String remark;//用户描述

    private String photoPath;//头像路径

    private String areaId;//区域ID

    public String getAreaId() {
        return areaId;
    }

    public void setAreaId(String areaId) {
        this.areaId = areaId;
    }
}

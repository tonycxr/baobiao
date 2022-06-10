package com.sungcor.baobiao.entity;

import lombok.Data;

import java.util.List;

@Data
class MenuBean {
    private String code;
    private String name;
    private String type;
    private String parent;
    private int sort;
    private String icon;
    private String address;
    private String empower;
    private int width;
    private int height;
    private String available;
    private List<MenuBean> subMenu;
}

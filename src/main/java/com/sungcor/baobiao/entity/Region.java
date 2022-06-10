package com.sungcor.baobiao.entity;

import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class Region {
    private Integer id; //主键ID
    private String code; //编号
    private String name; //名称
    private String description;  //描述
    private int parentId; //父ID
    private int sort; //排序号
    private String createUser; // 创建人
    private Date createTime; // 创建时间
    private String modifyUser; // 修改人
    private Date modifyTime; // 修改时间
    private List<Region> childrenRegions;
    private String parentName; //父名称
    private int level;
    private int operFlag;
    private Integer userRegionID;
    private String canUse;
    private String canQuery;
    private String data;
    private String icon;
}

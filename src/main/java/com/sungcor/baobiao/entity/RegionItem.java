package com.sungcor.baobiao.entity;

import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class RegionItem {
    private int id; // 主键ID
    private String code; // 编号
    private String name; // 名称
    private int regionId; // 字典项ID
    private String description; // 描述
    private int level; // 级别
    private int sysFlag; // 系统标识
    private String createUser; // 创建人
    private Date createTime; // 创建时间
    private String modifyUser; // 修改人
    private Date modifyTime; // 修改时间
    private String icon; // 图标

    private String checkORnot; //

    List<ServiceProduct> serviceProductList;
}

package com.sungcor.baobiao.entity;

import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class Category extends Entity{
    //分类编码
    private String code;

    //上级分类id
    private Integer parentCatID;
    //上级分类名称
    private String parentCatName;
    //创建人
    private String createUser;
    //修改人
    private String modifyUser;
    //创建时间
    private Date createTime;
    //修改时间
    private Date modifyTime;

    //排序号，同级下有效
    private Integer sort;
    //系统标示 "1":系统 "0":非系统
    private  String sysFlag = "0";

    private String source;

    private String enableFlag;

    private List<Category> childrenCategories;

    private String icon;
}

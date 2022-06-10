package com.sungcor.baobiao.entity;

import lombok.Data;

import java.util.List;

@Data
public class CategoryArea {
    private int id;

    private String title;//分栏标题

    private String categoryId;//分类ID

    private int columnCount;//该栏所占列

    private int displayFlag;//是否隐藏该栏

    private int sort;//排序

    private int deleteFlag;//是否可删

    private int source;//来源

    private String isImport;//是否允许继承


    private String isSubForm;//是否子表单
    //private String visibleFlag;

    private List<CategoryAttributeField> fieldList;//栏目下的属性
}

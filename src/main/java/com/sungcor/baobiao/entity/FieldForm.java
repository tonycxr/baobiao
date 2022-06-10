package com.sungcor.baobiao.entity;

import lombok.Data;

@Data
public class FieldForm extends Entity{
    private Integer  userdeFined;
    private Integer id;
    //字段分类
    private Integer categoryId;
    //字段分类名称
    private String categoryName;
    //字段名称
    private String name;
    //显示名称
    private String label;
    //控件类型
    private String renderType;
    //描述信息，鼠标移上去显示
    private String toolTip;
    //字段字段长度
    private Integer size;
    //默认值
    private String defaultValue;
    //是否必填（1：必填）
    private String isrequired;
    //是否正行（1：整行）
    private String fullLine;
    // 占行数
    private Integer rows;
    //数据源
    private String dataSource;
    //数据名称
    private String dataSourceName;
    //是否初始化数据（1：不是）
    private String sysFlag;
    //字段引用次数
    private Integer number;
    //存取引用字段表单ID
    private String  numberStr;
    //父ID
    private Integer parentId;
    //父ID名称
    private String parentAtt;
    //是否查询（1:查询）
    private String search;
    //是否通知（1:通知）
    private String notice;
    //是否知识（1:知识）
    private String knowledge;
    //知识字段名称
    private String knowledgeName;
    //数据源地址
    private String dataSourceAddress;
    //配置项引用次数
    private Integer cmdbNumber;
}

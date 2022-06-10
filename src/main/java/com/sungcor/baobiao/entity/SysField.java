package com.sungcor.baobiao.entity;


import lombok.Data;

import java.util.Date;

@Data
public class SysField {
    private Integer id;
    private int areaID;
    private String value;
    private String thekey;
    private Integer conmle;
    private Integer userdeFined;
    private Integer search;
    //字段管理ID
    private Integer comfmId;
    private String cascade;  //关联
    private String cascadeFlag;  //级联
    private String cascadeType;  //区分关联与级联类型，0关联，1级联
    private String parentValue;  //级联时上级字段的值
    private String visibleFlag;
    private String writeFlag;
    private String childs;
    private String tree;
    private Integer fieldID;
    private String name;
    private String label;
    private String tooltip;

    private Integer contentSize;

    private String defaultValue;

    private Integer isRequired;

    private String fullLine;

    private Integer rows;

    private String renderType;

    private Integer isQuery;

    private String dataSource;

    private String format;

    private Integer parentID;

    private String parentAtt;

    private String cascadeParentField;

    private String cascadeParentAtt;

    private String parentField;

    private String fieldIDs;

    private String evaRule;

    private Integer sysFlag;

    private Integer sort;

    private String modifyuser;

    private Date modifyTime;
}

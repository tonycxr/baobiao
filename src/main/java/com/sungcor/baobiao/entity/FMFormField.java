package com.sungcor.baobiao.entity;

import lombok.Data;

@Data
public class FMFormField extends Entity{
    private int areaID;

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

    private Integer deleteFlag;

    private Integer sysFlag;

    private Integer sort;

    private String visibleFlag;

    private String writeFlag;

    private String printFlag;

    private String childs;

    private String tree;
    //字段管理ID
    private Integer comfmId;

    /**
     *  使用标识
     *  该标识用于外部请求webservice接口获取表单数据项时判断是否传递给外部
     *  0 不启用 1 启用
     */
    private String useFlag;

    private Integer conmle;

    private String cascade;  //关联

    private String cascadeFlag;  //级联

    private String cascadeType;  //区分关联与级联类型，0关联，1级联

    private String parentValue;  //级联时上级字段的值

    //数据源地址
    private String dataSourceAddress;

}

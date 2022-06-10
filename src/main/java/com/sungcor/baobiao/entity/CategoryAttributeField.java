package com.sungcor.baobiao.entity;

import lombok.Data;

@Data
public class CategoryAttributeField {
    private int areaID;//栏目ID
    private String label;//标题
    private String name;//名字
    private String renderType;//类型
    private Integer rows;//所占列
    private String dataSource;//数据源
    private String format;
    private int contentSize;
    private String defaultValue;//默认值
    private Integer parentID;//父id
    private String parentAtt;
    private String cascadeParentField;
    private String cascadeParentAtt;
    private String parentField;
    private String fullLine;
    private String evaRule;
    private Integer isRequired;//是否必填
    private String fieldIDs;
    private Integer deleteFlag;
    private Integer sysFlag;
    private Integer sort;//排序
    private String useFlag;
    private int source;//来源 3表示树
    private String isImport;//是否允许继承
    private String childs;
    private String cascade;  //关联
    private String cascadeFlag;  //级联
    private String cascadeType;  //区分关联与级联类型，0关联，1级联
    private String namesub;//名字
    private String tree;
    private String tooltip;
    private String attribute;
    private String attributeSub;
    private String attributekey;
    private String attributevalue;
    private String attributekeySub;
    private String attributevalueSub;
    private Integer userdeFined;
    private int  search;//是否查询
    private int  listquery;//是否查询
}

package com.sungcor.baobiao.report.bean;

import lombok.Data;

/**
 * Created by lenovo on 2016/5/26.
 */
@Data
public class ReportTypeAttributeBean
{
    private Integer id;
    private Integer typeId;   //报表类型ID
    private String status;  //标识状态',-- dimension表示统计维度；chart表示统计图；index表示指标
    private String attributeId;  //属性ID
    private String attributeName;  //属性显示名字
    private String dataSource;    //维度数据来源表',  -- dict表示码表；category表示分类表；only表示自己，如有效数，无效数
    private String fieldName;
}

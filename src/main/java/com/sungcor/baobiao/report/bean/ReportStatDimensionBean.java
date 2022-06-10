package com.sungcor.baobiao.report.bean;

import lombok.Data;

/**
 * Created by lenovo on 2016/5/26.
 */
@Data
public class ReportStatDimensionBean
{
    private int id;
    private int modelId;       //模板ID
    private int typeId;        //报表类型ID
    private int dimensionId;   //维度ID' , -- 对应REPORT_TYPE_ATTRIBUTE_T的ID
    private int sort;          //分组维度' default 1, -- 默认为1，1表示一级分组，2表示二级分组
    private String extension;  //扩展方式' default 'portrait', -- portrait纵向，transverse横向，默认纵向
    private String level;
}

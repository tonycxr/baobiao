package com.sungcor.baobiao.report.bean;

import lombok.Data;

/**
 * Created by lenovo on 2016/5/26.
 */
@Data
public class ReportDimensionDetBean
{
    private int id;
    private int modelId;
    private int typeId;
    private String sindexId;
    private String selIndexName;
    private String dataSource;
    private String fieldName;
    private int sort;
    private int level;
}

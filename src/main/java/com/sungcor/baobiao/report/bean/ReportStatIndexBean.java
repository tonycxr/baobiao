package com.sungcor.baobiao.report.bean;

import lombok.Data;

/**
 * Created by lenovo on 2016/5/29.
 */
@Data
public class ReportStatIndexBean
{
    private int id;
    private int modelId;
    private int typeId;
    private String indexId;
    private String selIndexId;
    private String selIndexName;
    private String indexName;
    private String fieldName;
    private String dataSource;
    private int sort;

}

package com.sungcor.baobiao.report.bean;

import lombok.Data;

/**
 * Created by lenovo on 2016/6/15.
 */
@Data
public class ReportTreeDimesnsionBean
{
    private int id;
    private int modelId;
    private int typeId;
    private String sindexId;
    private String selIndexName;
    private String dataSource;
    private String fieldName;
    private String parentIndexId;
    private int level;
    private int sort;

}

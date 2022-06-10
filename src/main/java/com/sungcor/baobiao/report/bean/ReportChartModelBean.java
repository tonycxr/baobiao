package com.sungcor.baobiao.report.bean;

import lombok.Data;

@Data
public class ReportChartModelBean {
    private Integer id;
    private Integer modelId;
    private Integer typeId;
    private String typeName;
    private String indexNames;
    private String indexIds;
    private String  fieldName;
    private String title;
    private int sort;

}

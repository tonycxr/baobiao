package com.sungcor.baobiao.report.bean;

import lombok.Data;

/**
 * Created by lenovo on 2016/5/30.
 */
@Data
public class ReportGroupRelationBean
{
    private int id;
    private int dimensionId;
    private String dimensionName;
    private int parentId;

}

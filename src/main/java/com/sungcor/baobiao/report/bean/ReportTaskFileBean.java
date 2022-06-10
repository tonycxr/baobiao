package com.sungcor.baobiao.report.bean;

import lombok.Data;

/**
 * Created with IntelliJ IDEA.
 * User: yangqing
 * Date: 16-6-13
 * Time: 上午11:18
 * To change this template use File | Settings | File Templates.
 */
@Data
public class ReportTaskFileBean {
    private int id;
    private Integer typeId;
    private String typeName;
    private Integer groupId;
    private String groupName;
    private Integer modelId;
    private String modelName;
    private Integer taskId;
    private String taskName;
    private String reportName;
    private Integer taskType;
    private String taskTypeName;
    private String size;
    private String createTime;
    private String reportDes;

}

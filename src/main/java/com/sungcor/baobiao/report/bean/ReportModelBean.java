package com.sungcor.baobiao.report.bean;

import lombok.Data;

/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 * Date: 16-5-23
 * Time: 上午11:18
 * To change this template use File | Settings | File Templates.
 */
@Data
public class ReportModelBean {
    private Integer id;
    private Integer typeId;
    private String typeName;
    private Integer groupId;
    private String groupName;
    private String code;
    private String name;
    private String reportName;
    private String status;
    private String description;
    private String createUser;
    private String createUserName;
    private String createTime;
    private String modifyUser;
    private String modifyUserName;
    private String modifyTime;
    private int serviceCategoryId;
    private int productId;
    private int showTable;
    private int showChart;

}

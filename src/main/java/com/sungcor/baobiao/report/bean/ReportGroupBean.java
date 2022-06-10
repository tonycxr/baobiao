package com.sungcor.baobiao.report.bean;

import lombok.Data;

import java.sql.Date;

/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 * Date: 16-5-23
 * Time: 上午11:17
 * To change this template use File | Settings | File Templates.
 */
@Data
public class ReportGroupBean {

    public static final String MENU_CODE = "customReport_reportGroup_";

    private int id;  //'报表分组ID' ,
    private String code; // -- 用于同步到菜单表
    private String name; //'报表分组名称'
    private String desc; //'分类描述'

    private String createUser;
    private String modifyUser;
    private Date createTime;
    private Date modifyTime;

}

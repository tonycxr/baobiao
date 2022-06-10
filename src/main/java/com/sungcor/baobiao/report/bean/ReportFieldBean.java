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
public class ReportFieldBean {
    private Integer id;
    private String name;
    private String code;
    private String typeId;
    private String renderType;
    private String defaultValue;
    private String label;
    private String dataSource;
    private String querySource;
    private Integer reprotType;

}

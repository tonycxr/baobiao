package com.sungcor.baobiao.report.bean;

import lombok.Data;

import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 * Date: 16-5-23
 * Time: 上午11:18
 * To change this template use File | Settings | File Templates.
 */
@Data
public class ReportQuerySettingBean {
    private Integer id;
    private String typeId;
    private Integer modelId;
    private Integer fieldId;
    /**
     * 时间范围
     */
    private String datePurview;
    /**
     * 是否启用颗粒度
     */
    private String isEnable;

    private String beginDate;
    private String endDate;

    private String beginTime;
    private String beginHour;
    private String beginMinute;

    private String endTime;
    private String endHour;
    private String endMinute;

    private String size;
    private String sizeUnit;

    private String createUser;
    private Date createTime;
    private String modifyUser;
    private Date modifyTime;

}

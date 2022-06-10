package com.sungcor.baobiao.entity;

import lombok.Data;

import java.util.List;

@Data
public class FMFormVersion extends Entity{
    private Integer formID;

    private Integer version;

    private Integer vaildFlag;

    private Integer parentFormVersionID;

    private Integer lockFlag;

    private String  locker;

    private Integer publishFlag;

    private List<FMFormArea> areaList;

    private Integer addFlag;

    private Integer deleteFlag;

    private Integer modifyFlag;

    private List<FMFormInstance> instances;

    private String formName;

    private Integer currentVersionID;

    private String createTimeStr;

    private String modifyTimeStr;

    private List<FieldForm> subFormField;

    private Integer displayFlag;  //1不显示子表单，2不显示按钮
}

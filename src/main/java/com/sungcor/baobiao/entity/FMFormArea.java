package com.sungcor.baobiao.entity;

import lombok.Data;

import java.util.List;

@Data
public class FMFormArea extends Entity{
    private int versionID;

    private String title;

    private int columnCount;

    private int displayFlag;

    private int subDisplayFlag;   //子表单显示标识

    private int sort;

    private int deleteFlag;

    private String visibleFlag;

    private Integer formType;

    private List<FMFormField> fieldList;

    private List<FMFormInstance> formInstanceList;
}

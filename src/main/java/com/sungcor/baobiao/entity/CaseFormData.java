package com.sungcor.baobiao.entity;

import lombok.Data;

@Data
public class CaseFormData {
    private Integer id;

    private int processInstanceID;

    private int formVersionId;

    private Integer fieldId;

    private String fieldName;

    private String fieldLabel;

    private String fieldType;

    private String fieldValue;

    private String fieldDataSource;

    private Integer subFormObjId;

    private Integer formType;

    private Integer areaID;
}

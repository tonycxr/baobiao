package com.sungcor.baobiao.entity;

import lombok.Data;

@Data
public class FormAccessControl {
    private Integer id;

    private String businessDef1;

    private String businessDef2;

    private String businessDef3;

    private String businessDef4;

    private String businessDef5;

    private String businessDef6;

    private String businessDef7;

    private String businessDef8;

    private String businessDef9;

    private String businessDef10;

    private int formID;

    private int elementID;

    private int visibleFlag;

    private int writeFlag;

    private int addFlag;

    private int modifyFlag;

    private int deleteFlag;

    private int subFormID;

    private int printFlag;

    private String processInstanceID;
}

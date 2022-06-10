package com.sungcor.baobiao.entity;

import lombok.Data;

import java.util.Map;

@Data
public class FMSubFormInstance extends Entity{
    private Integer formVersionID;

    private Integer formInstanceID;

    private String formInstanceSn;

    private Map fields;

}

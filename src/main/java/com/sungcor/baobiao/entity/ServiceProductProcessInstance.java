package com.sungcor.baobiao.entity;

import lombok.Data;

@Data
public class ServiceProductProcessInstance {
    private Integer id;
    private Integer serviceProductId; //产品ID
    private Integer processInstanceId; //流程ID
}

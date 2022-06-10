package com.sungcor.baobiao.entity;

import lombok.Data;

@Data
public class ServiceProductRegion {
        private Integer serviceProductId; //产品ID
        private Integer organizationId; //组织ID
        private String canUse; //使用创建权限   null 无  0 有
        private String canQuery;  //查询查看权限  null无  0有
}

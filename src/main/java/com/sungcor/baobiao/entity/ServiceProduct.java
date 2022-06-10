package com.sungcor.baobiao.entity;

import lombok.Data;

import java.util.Date;

@Data
public class ServiceProduct {
    private Integer id; //主键ID
    private String code; //编号
    private Integer serviceCategoryId;//分类
    private Integer productGroupId; //分组ID 对应dictItem
    private Integer organizationId; //组织
    private String name; //名称
    private String description; //描述
    private Integer flowId; //流程
    private String icon; //图标
    private String status; //状态 1启用  0禁用
    private String createUser; // 创建人
    private Date createTime; // 创建时间
    private String modifyUser; // 修改人
    private Date modifyTime; // 修改时间

    private String categoryName;  //分类名称
    private String groupName;    //分组名称--关联dictItem
    private String organization;    //组织

    private String itsqmSPCode;  //ITSQM 服务产品代码
    private String itsqmServiceCalssify;    //ITSQM 服务类别
    private String itsqmServiceType;   //ITSQM 服务类型
    private String itsqmSPID;   //ITSQM 服务产品数据库唯一标识
    private Integer regionId;  //数据域id
}

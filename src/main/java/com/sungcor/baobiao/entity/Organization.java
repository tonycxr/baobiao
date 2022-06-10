package com.sungcor.baobiao.entity;

public class Organization {

    private  String id;

    private  String code;

    private  String name;

    private  String parentid;

    private  String governingunit;

    private  String description;

    private  String sort;

    private String  operatetype;

    public String getOperatetype() {
        return operatetype;
    }

    public void setOperatetype(String operatetype) {
        this.operatetype = operatetype;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getParentid() {
        return parentid;
    }

    public void setParentid(String parentid) {
        this.parentid = parentid;
    }

    public String getGoverningunit() {
        return governingunit;
    }

    public void setGoverningunit(String governingunit) {
        this.governingunit = governingunit;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getSort() {
        return sort;
    }

    public void setSort(String sort) {
        this.sort = sort;
    }
}

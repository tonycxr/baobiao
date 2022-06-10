package com.sungcor.baobiao.entity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EntityObject {

    private String id;

    private String parentID;

    private String name;

    private String renterId;

    private String key;

    private String value;

    private List<EntityObject> subList;

    private Map<String,Object> attributeMap;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getParentID() {
        return parentID;
    }

    public void setParentID(String parentID) {
        this.parentID = parentID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRenterId() {
        return renterId;
    }

    public void setRenterId(String renterId) {
        this.renterId = renterId;
    }

    public List<EntityObject> getSubList() {
        if(null == subList) {
            subList = new ArrayList<EntityObject>();
        }
        return subList;
    }

    public void setSubList(List<EntityObject> subList) {
        this.subList = subList;
    }

    public Map<String, Object> getAttributeMap() {
        if(null == attributeMap) {
            attributeMap = new HashMap<String, Object>();
        }
        return attributeMap;
    }

    public void setAttributeMap(Map<String, Object> attributeMap) {
        this.attributeMap = attributeMap;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}

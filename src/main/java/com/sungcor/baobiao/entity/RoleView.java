package com.sungcor.baobiao.entity;

import lombok.Data;

@Data
public class RoleView {
    private String id;

    private String name;

    private String url;

    private String icon;

    private String button;

    private String priority;

    private String description;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        RoleView roleView = (RoleView) o;

        if (id != null ? !id.equals(roleView.id) : roleView.id != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }
}

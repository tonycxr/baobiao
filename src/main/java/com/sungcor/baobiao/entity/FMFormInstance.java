package com.sungcor.baobiao.entity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FMFormInstance extends Entity{
    private Integer formVersionID;

    private Integer parentId;

    private String visibleFlag;

    private String writeFlag;

    private Map<String,String> fields;

    List<FMSubFormInstance> subFormlist;

    public FMFormInstance(){
        this.reporterSource = "cxrDataSource";
    }

    public String getReporterSource() {
        return reporterSource;
    }

    public void setReporterSource(String reporterSource) {
        this.reporterSource = reporterSource;
    }

    private String reporterSource;

    public String getReporterName() {
        return reporterName;
    }

    public void setReporterName(String reporterName) {
        this.reporterName = reporterName;
    }

    private String reporterName;

    public Integer getFormVersionID() {
        return formVersionID;
    }

    public void setFormVersionID(Integer formVersionID) {
        this.formVersionID = formVersionID;
    }

    public Integer getParentId() {
        return parentId;
    }

    public void setParentId(Integer parentId) {
        this.parentId = parentId;
    }

    public String getVisibleFlag() {
        return visibleFlag;
    }

    public void setVisibleFlag(String visibleFlag) {
        this.visibleFlag = visibleFlag;
    }

    public String getWriteFlag() {
        return writeFlag;
    }

    public void setWriteFlag(String writeFlag) {
        this.writeFlag = writeFlag;
    }

    public Map<String, String> getFields() {
        if (this.fields == null)
            this.fields = new HashMap<String, String>();
        return fields;
    }

    public void setFields(Map<String, String> fields) {
        this.fields = fields;
    }

    public List<FMSubFormInstance> getSubFormlist() {
        if (this.subFormlist == null)
            this.subFormlist = new ArrayList<FMSubFormInstance>();
        return subFormlist;
    }

    public void setSubFormlist(List<FMSubFormInstance> subFormlist) {
        this.subFormlist = subFormlist;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        FMFormInstance other = (FMFormInstance) obj;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        return true;
    }
}

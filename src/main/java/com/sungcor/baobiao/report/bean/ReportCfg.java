package com.sungcor.baobiao.report.bean;

import lombok.Data;

import java.util.List;

@Data
public class ReportCfg {
    public String reportId;
    public String name;
    public String reportUrl;
    public String parentId;
    public List<ReportCfg> reportCfgs;
    public String reportType; //0:report 1;group
    public String privilege;//权限

}

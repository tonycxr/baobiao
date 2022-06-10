package com.sungcor.baobiao.report.bean;

import lombok.Data;

@Data
public class ReportChapterBean {
    private int Id;
    private String chapterName;
    private String chapterDesc;
    private String childIds;
    private int modelId;
    private int sort;
    private int num;
    private String type;
}

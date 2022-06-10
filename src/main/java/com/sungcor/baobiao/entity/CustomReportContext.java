package com.sungcor.baobiao.entity;

import com.sungcor.baobiao.report.bean.*;
import lombok.Data;

import java.util.HashMap;
import java.util.List;

@Data
public class CustomReportContext {
    private List<ReportModelBean> reportModelList;
    private List<ReportGroupBean> reportGroupList;
    private List<ReportTypeAttributeBean> reportTypeAttrList;
    private ReportTypeAttributeBean reportTypeAttr;
    private ReportChapterBean chapterBean;
    private int typeId;
    private int modelId;
    private String id;
    private int parentId;
    private String jqgrid;
    private String source;
    private String fieldName;
    private int sort;
    private int[] sindexIds;
    private List<ReportDimensionDetBean> dimList;
    private List<ReportStatIndexBean> indexList;
    private List<ReportStatDimensionBean> statDimList;
    private List<ReportTreeDimesnsionBean> treeDimList;
    private List<ReportChapterBean> rlist;
    private HashMap<String,Object> trendMap;
}

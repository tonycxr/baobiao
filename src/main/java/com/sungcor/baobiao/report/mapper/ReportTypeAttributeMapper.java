package com.sungcor.baobiao.report.mapper;

import com.sungcor.baobiao.report.bean.ReportTypeAttributeBean;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Component
@Repository
public interface ReportTypeAttributeMapper {
    public void insert(ReportTypeAttributeBean reportTypeAttributeBean);

    public void update(ReportTypeAttributeBean reportTypeAttributeBean);

    public void delete(int Id);

    public void deleteFieldName(String fieldName);

    public ReportTypeAttributeBean getReportTypeAttribute(int Id);

    public List<ReportTypeAttributeBean> getReportTypeAttributeByFieldName(String fieldName);
}

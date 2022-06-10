package com.sungcor.baobiao.report.mapper;

import com.sungcor.baobiao.report.bean.ReportCustomIndexBean;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;

@Mapper
@Component
@Repository
public interface ReportCustomIndexMapper {
    public ReportCustomIndexBean getById(int id);
    public void addCusIndex(ReportCustomIndexBean bean);
    public void updateCusIndex(ReportCustomIndexBean bean);
    public void deleteCusIndex(List ids);
    public List<ReportCustomIndexBean> queryCusIndex(HashMap<String,String> map);
    public int queryCusIndexCount(HashMap<String,String> map);
    public void deleteStatIndexByField(String field);
    public int existIndexByStat(String field);
}

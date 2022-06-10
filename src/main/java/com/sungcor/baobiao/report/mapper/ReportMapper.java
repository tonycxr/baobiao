package com.sungcor.baobiao.report.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;

@Mapper
@Component
@Repository
public interface ReportMapper {
    //根据维度id。指标id获取数量
    public List<HashMap> findSelectOrderNum(HashMap map);

    public HashMap findTableNameByModelId(String modelId);

    public String findTableNameByMap(HashMap map);
}

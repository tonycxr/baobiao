package com.sungcor.baobiao.mapper;

import com.sungcor.baobiao.entity.FMForm;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;


@Mapper
@Component
@Repository
public interface FormMapper {
    public void  insert(FMForm form);

    public void  update(FMForm form);

    public void  delete(FMForm form);

    public java.util.List<FMForm> search(java.util.HashMap parms);

    public FMForm getFMForm(FMForm form);

    /**
     * 根据名称查询表单
     * @param form
     * @return
     */
    public java.util.List<FMForm> getFMFormByCode(FMForm form);

    public java.util.List<java.util.HashMap> getFMFormCount(java.util.HashMap parms);

    public Integer checkDelete(Integer formID);

    public List<FMForm> listFormBySCID(Integer id);

    public List<FMForm> listFormBySCIDAndOrg(Map para);

    /**
     * 通过表单名称获取对应的表数据
     * @param map
     * @return
     */
    public Map getFormByTable(Map map);
}

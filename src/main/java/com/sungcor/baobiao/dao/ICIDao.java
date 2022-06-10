package com.sungcor.baobiao.dao;

import com.sungcor.baobiao.entity.CIBaseInfo;
import com.sungcor.baobiao.entity.CaseVo;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public interface ICIDao {
    public Integer saveCiBase(Map map);
    public void saveField(Map map);
    public CIBaseInfo getCiBase(Integer ciId);
    public String getFieldValue(Map map);
    public void deleteCI(Integer ciId);
    public void deleteFields(Integer ciId);
    public void updateCI(Map map);
    public void updateCode(Map map);
    public List<CIBaseInfo> getCIBaseListByWork(HashMap map);
    public List<HashMap> getCIBaseFieldListByWork(HashMap map);
    public void updateDelFlag(Map map);//逻辑删除
    public void setFlag(Map map);//设置flag状态
    public List<CIBaseInfo> getCIBaseListByCateId(HashMap map);

    public List<CaseVo> getProcessByCiId(Map map);//通过ciid得到关联的工单

    public void deleteRelationProcess(Map map);//删除工单与资产的关联
    public List<CIBaseInfo> checkCIByCode(Map map);//根据编码查CI
    public List<CIBaseInfo> getServiceEndTimeList(Map map);

    public List<Map<String,String>> queryCatByPid(Integer categoryId);
    public Integer getCategoryViewCount(HashMap map);
    public List<CIBaseInfo> getCategoryViewList(HashMap map);


    public HashMap getOtherSourceByCiId(HashMap map);
    public HashMap getCIByOtherSource(HashMap map);

    //获取人员所含数据域中所有CI
    public List<CIBaseInfo> getAllRegionCI(HashMap map);
}

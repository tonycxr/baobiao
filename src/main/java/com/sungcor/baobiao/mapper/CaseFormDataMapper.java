package com.sungcor.baobiao.mapper;


import com.sungcor.baobiao.entity.CaseFormData;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Mapper
@Component
@Repository
public interface CaseFormDataMapper {
    public void  insert(CaseFormData caseFormData);

    public void  insertBatch(List<CaseFormData> caseFormDataList);

    public void  delete(Integer processInstanceID);

    public void  deleteByObjID(Integer subFormObjId);

    public void update(CaseFormData caseFormData);

    public void updateByObjID(CaseFormData caseFormData);

    public void updateTempSN(Map map);

    public List<CaseFormData> get(Integer processInstanceID);

    public CaseFormData getCaseFormDataByName(HashMap map);

    public CaseFormData getCaseFormSubDataByName(HashMap map);

    public List<CaseFormData> list(Map map);

    public List<CaseFormData> getByObjID(Integer subFormObjId);

    public void insertListSubForm( List<HashMap<String,Object>> list);
}

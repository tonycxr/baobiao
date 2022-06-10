package com.sungcor.baobiao.mapper;

import com.sungcor.baobiao.report.bean.Function;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Mapper
@Component
@Repository
public interface FunctionMapper {
    public void  insertFunction(Function po);

    public void  insertFunctionByModel(Integer reportModelId);

    public void  deleteFunctionByModel(Integer reportModelId);

    public void  insertReportGroupFunction();
    public void  insertSysconfigFunction();
    public void deleteReportConfigFunction(Map ids);
    public void  deleteReportGroupFunction(List ids);

    public void  deleteReportModelFunction(List ids);

    public void  insertReportModelFunction();

    public void deleteAllFunction();

    public java.util.List<java.util.Map> findFunctionByUser(java.util.Map parm);

//    public List<Object> getUserFunctions(HashMap<Object, Object> param);
    public List<Map> getisfunllparent();
    public int  insertRoleFunction(HashMap<Object, Object> param);

    public int deleteRoleFunction(HashMap<Object, Object> param);

//    public List<Object> getRoleFunctions(HashMap<Object, Object> param);

    public List<java.util.Map> getUserFunctionsByCode(java.util.Map parm);

    public void  insertFunctionPrivilege(Function po);
}

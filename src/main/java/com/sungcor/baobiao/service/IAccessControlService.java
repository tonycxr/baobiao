package com.sungcor.baobiao.service;

import com.sungcor.baobiao.entity.FMFormArea;
import com.sungcor.baobiao.entity.FieldForm;
import com.sungcor.baobiao.entity.FormAccessControl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public interface IAccessControlService  {


    public List<FMFormArea> getACData(List<String> businessFlag, Integer versionID) throws Exception;


    public void saveACData(List<String> businessFlag, FormAccessControl accessControlData) throws Exception;


    public void updateACData(List<String> businessFlag, FormAccessControl accessControlData) throws Exception;


    public List<FormAccessControl> getListByCondition(Map map);


    public void  delete(String bussis);

    public void  insert(FormAccessControl accessControl);

    public void SaveSparepart(HashMap map);

    //获取子表单中字段
    public List<FieldForm> getSubFormField(String subFormId);
}


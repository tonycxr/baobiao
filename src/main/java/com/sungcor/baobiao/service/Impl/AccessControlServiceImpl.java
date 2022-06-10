package com.sungcor.baobiao.service.Impl;

import com.sungcor.baobiao.entity.FMFormArea;
import com.sungcor.baobiao.entity.FMFormVersion;
import com.sungcor.baobiao.entity.FieldForm;
import com.sungcor.baobiao.entity.FormAccessControl;
import com.sungcor.baobiao.mapper.AccessControlMapper;
import com.sungcor.baobiao.service.IAccessControlService;
import com.sungcor.baobiao.service.IFMFormAreaService;
import com.sungcor.baobiao.service.IFMFormVersionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Service
public class AccessControlServiceImpl implements IAccessControlService {

    @Autowired
    private AccessControlMapper accessControlMapper;

    @Autowired
    private IFMFormAreaService areaService;

    @Autowired
    private IFMFormVersionService formVersionService;

    public List<FMFormArea> getACData(List<String> businessFlag, Integer versionID) throws Exception {
        FMFormVersion formVersion = null;
        List<FMFormArea> formAreas = null;
        if(null != businessFlag && businessFlag.size() > 0) {

        }else {
            if (null != versionID) {
                formVersion = formVersionService.getFMFormVersion(versionID);
            }
            if (null != formVersion) {
                formAreas = areaService.search(formVersion.getId());
            }
        }
        return formAreas;
    }

    public void saveACData(List<String> businessFlag, FormAccessControl accessControlData) throws Exception {
        if(null != businessFlag && businessFlag.size() > 0) {

        }
        accessControlMapper.insert(accessControlData);
    }


    public void updateACData(List<String> businessFlag, FormAccessControl accessControlData) throws Exception {
        if(null != businessFlag && businessFlag.size() > 0) {

        }
        // accessControlMapper.update(accessControlData);
    }

    public List<FormAccessControl> getListByCondition(Map map){
        return accessControlMapper.getListByCondition(map);
    }

    public void  delete(String bussis){
        accessControlMapper.delete(bussis);
    }

    public void  insert(FormAccessControl accessControl){
        accessControlMapper.insert(accessControl);
    }

    @Override
    public void SaveSparepart(HashMap map) {
        boolean b = true;
//        try{
//            accessControlMapper.DeleteSparepart(map);//删除之前的记录
//        }catch (Exception e){
//            b = false;
//            e.printStackTrace();
//        }
//        if (b) {
//            accessControlMapper.SaveSparepart(map);//删除成功再插入新的记录
//        }
    }


    @Override
    public List<FieldForm> getSubFormField(String subFormId) {
        List<FieldForm> list=accessControlMapper.getSubFormField(subFormId);
        return list;
    }
}

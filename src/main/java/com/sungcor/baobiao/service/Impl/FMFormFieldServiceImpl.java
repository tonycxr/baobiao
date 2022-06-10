package com.sungcor.baobiao.service.Impl;

import com.sungcor.baobiao.STSMConstant;
import com.sungcor.baobiao.entity.FMForm;
import com.sungcor.baobiao.entity.FMFormArea;
import com.sungcor.baobiao.entity.FMFormField;
import com.sungcor.baobiao.entity.FormAccessControl;
import com.sungcor.baobiao.mapper.FMFormFieldMapper;
import com.sungcor.baobiao.service.IFMFormFieldService;

import com.sungcor.baobiao.utils.UtilTools;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class FMFormFieldServiceImpl implements IFMFormFieldService {
    

    @Autowired
    private FMFormFieldMapper formFieldMapper;
    
    public void insert(FMFormField field) {
        formFieldMapper.insert(field);
    }

    public void update(FMFormField field) {
        formFieldMapper.update(field);
    }

    public void delete(FMFormField field) {
        formFieldMapper.delete(field);
    }

    public List<FMFormField> search(Integer areaID) {
        return formFieldMapper.search(areaID);
    }

    public FMFormField getFMFormField(Integer fieldID) {
        return formFieldMapper.getFMFormField(fieldID);
    }

    public java.util.List<FMFormField> getSubField(Integer fieldID){
        return formFieldMapper.getSubField(fieldID);
    }



    public java.util.List<FMFormField> getSysConSubField(HashMap map){
        return formFieldMapper.getSysConSubField(map);
    }

    public java.util.List<FMFormField> getCmdbCiSubField(HashMap map){
        return formFieldMapper.getCmdbCiSubField(map);
    }

    public java.util.List<FMFormField> listParentFieldList(Integer areaID){
        return formFieldMapper.listParentFieldList(areaID);
    }

    public Integer getMaxFieldSortByAreaID(Integer areaID){
        return formFieldMapper.getMaxFieldSortByAreaID(areaID);
    }

    public void updateSortPre(FMFormField field){
        formFieldMapper.updateSortPre(field);
    }

    public void updateSortNext(FMFormField field){
        formFieldMapper.updateSortNext(field);
    }

    public void updateAreaSortPre(FMFormArea area){
        formFieldMapper.updateAreaSortPre(area);
    }

    public void updateAreaSortNext(FMFormArea area){
        formFieldMapper.updateAreaSortNext(area);
    }

    @Transactional
    public void  update( List<FMFormField> form){
        for(FMFormField fm:form){
            formFieldMapper.update(fm);
        }
    }

//    @Override
//    @Transactional
//    public void updateField(FMForm fmeForm, int versionID) {
//        try {
//            List<FMFormArea> formAreas = operateFormService.showFormByControl(null, fmeForm.getVersionID()); //查询历史form信息
//            List<FMFormArea> newAreas = operateFormService.showFormByControl(null,versionID);//查询新发布信息
//
//            List<FMFormField> parentList = new ArrayList<FMFormField>(STSMConstant.NUM_FIVE_HUNDRED); //进行统一封装，避免过多循环判断
//            List<FMFormField> childList = new ArrayList<FMFormField>(STSMConstant.NUM_FIVE_HUNDRED);
//            for(FMFormArea formArea : formAreas){parentList.addAll(formArea.getFieldList()); }
//            for(FMFormArea formArea : newAreas){childList.addAll(formArea.getFieldList()); }
//
//            for(FMFormField parent: parentList ){ //进行遍历后修改使用状态
//                for(FMFormField child : childList){
//                    if(parent.getName().equals(child.getName()) && STSMConstant.STR_ONE.equals(parent.getUseFlag())){
//                        child.setUseFlag(STSMConstant.STR_ONE);
//                        formFieldMapper.update(child);
//                    }
//                }
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//
//    }

    @Resource(name = "jdbcTemplate")
    private JdbcTemplate jdbcTemplate;

//    @Transactional
//    public void updateFieldPower(FMForm fmeForm, int versionID) {
//        try {
//            System.out.println(UtilTools.getBussinessNoByDate());
//            List<FMFormField> parentList = new ArrayList<FMFormField>(STSMConstant.NUM_FIVE_HUNDRED); //进行统一封装，避免过多循环判断
//            List<FMFormField> childList = new ArrayList<FMFormField>(STSMConstant.NUM_FIVE_HUNDRED);
//            parentList = formService.getFieldsByVersionID(fmeForm.getVersionID()); //查询历史form信息
//            childList = formService.getFieldsByVersionID(versionID);//查询新发布信息
//            for(FMFormField parent: parentList ){ //进行遍历后修改使用状态
//                for(FMFormField child : childList){
//                    if(parent.getName().equals(child.getName())){
//                        Map map = new HashMap();
//                        map.put("fieldID",parent.getId());
//                        List<FormAccessControl> list = accessControlService.getListByCondition(map);
//                        for(FormAccessControl accessControl:list){
//                            accessControl.setElementID(child.getId());
//                            accessControlService.saveACData(null,accessControl);
//                        }
//                        /*String sql = "UPDATE FM_FORM_ACCESS_CONTROL SET ELEMENTID="+child.getId()+" WHERE ELEMENTID="+parent.getId();
//                        jdbcTemplate.execute(sql);*/
//                    }
//                }
//            }
//            System.out.println(UtilTools.getBussinessNoByDate());
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//
//    }

    @Override
    public int getVersionIDMapFieldID(int versionID) {
        return formFieldMapper.getVersionIDMapFieldID(versionID);  //To change body of implemented methods use File | Settings | File Templates.
    }



    @Override
    public void updateVersionIDByParentID(HashMap param) {
        formFieldMapper.updateVersionIDByParentID(param);
    }

    public List<FMFormField> queryFormFields(HashMap map){
        List<FMFormField> list=formFieldMapper.queryFormFields(map);
        return list;
    }

//    @Override
//    public int getSortCount(Map id) {
//        int count=0;
//        try{
//            count  = Integer.parseInt(formFieldMapper.getSortCount(id).get(0).get("syscount").toString());
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return count;
//    }

    @Override
    public List<FMFormField> getsortList(Object object) {
        List<FMFormField> list=formFieldMapper.getsortList(object);
        return list;
    }

    @Override
    public FMFormField getUpward(Map map) {
        return formFieldMapper.getUpward(map);
    }

    @Override
    public FMFormField getDownward(Map map) {
        return formFieldMapper.getDownward(map);
    }

    @Override
    public List<FMFormField> getListUpward(Map map) {
        return formFieldMapper.getListUpward(map);
    }

    @Override
    public List<FMFormField> getListDownward(Map map) {
        return formFieldMapper.getListDownward(map);
    }

    @Override
    public FMFormField getsysupordow(Map map) {
        return formFieldMapper.getsysupordow(map);
    }

    @Override
    public void updateSort(FMFormField Group) {
        formFieldMapper.updateSort(Group);
    }

    @Override
    public FMFormArea getAreaUpward(Map map) {
        return formFieldMapper.getAreaUpward(map);
    }

    @Override
    public FMFormArea getAreaDownward(Map map) {
        return formFieldMapper.getAreaDownward(map);
    }

    @Override
    public List<FMFormArea> getAreaListUpward(Map map) {
        return formFieldMapper.getAreaListUpward(map);
    }

    @Override
    public List<FMFormArea> getAreaListDownward(Map map) {
        return formFieldMapper.getAreaListDownward(map);
    }

    @Override
    public FMFormArea getsysAreaupordow(Map map) {
        return formFieldMapper.getsysAreaupordow(map);
    }
    @Override
    public void updateAreaSort(FMFormArea Group) {
        formFieldMapper.updateAreaSort(Group);
    }


}

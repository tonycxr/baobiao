package com.sungcor.baobiao.service.Impl;

import com.sungcor.baobiao.entity.*;
import com.sungcor.baobiao.mapper.CaseFormDataMapper;
import com.sungcor.baobiao.service.*;
import com.sungcor.baobiao.utils.FormEngineHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
@Service
public class OperateFormServiceImpl implements IOperateFormService {

    @Autowired
    private IFMFormService formService;

    @Autowired
    private IFMFormVersionService formVersionService;

    @Autowired
    private IFMFormAreaService areaService;

    @Autowired
    private IFMFormFieldService fieldService;

    @Autowired
    private IAccessControlService accessControlService;

    @Autowired
    private CaseFormDataMapper caseFormDataMapper;
    public List<FMFormArea> showFormByControl(FormAccessControl businessFlag, Integer formVersioinId) throws Exception {
        try{
                FMForm fmeForm = formService.getFMForm(formVersionService.getFMFormVersion(formVersioinId).getFormID());
                String condition = FormEngineHelper.getBusinsCondition(businessFlag);
                List<FMFormArea> formAreas = null;
                if(0 != formVersioinId) {
                    formAreas = areaService.search(formVersioinId);
                }
                Iterator<FMFormArea> iter = formAreas.iterator();
                while (iter.hasNext()){
                    FMFormArea area = iter.next();
                    List<FMFormField> fields = new ArrayList<FMFormField>();
                    List<FMFormField> fieldList = fieldService.search(area.getId());
                    int writeCount=0;
                    int readerCount=0;
                    for(FMFormField field : fieldList) {
                        Map map = new HashMap();
                        if(businessFlag!=null){
                            map.put("businessDef1",businessFlag.getBusinessDef1());
                            map.put("businessDef2",businessFlag.getBusinessDef2());
                        }
                        map.put("formID",fmeForm.getId());
                        map.put("fieldID",field.getId());
                        List<FormAccessControl> formControlList = accessControlService.getListByCondition(map);
                        if (null != formControlList&&formControlList.size()>0) {
                            /**0?????��**/
                            field.setVisibleFlag(String.valueOf(formControlList.get(0).getVisibleFlag()));
                            field.setWriteFlag(String.valueOf(formControlList.get(0).getWriteFlag()));
                        }
                        if(field.getVisibleFlag()!=null&&field.getVisibleFlag().equals("1")){
                            //不可读，就直接不显示了
                        }else{
                            if(field.getVisibleFlag()==null&&field.getWriteFlag()==null){
                                fields.add(field);
                            }else if(field.getVisibleFlag()!=null&&field.getWriteFlag()!=null){
                                if(field.getWriteFlag().equals("0")||field.getVisibleFlag().equals("0")){
                                    fields.add(field);

                                }
                                if("1".equals(field.getWriteFlag())){
                                    writeCount++;
                                }
                                if("1".equals(field.getVisibleFlag())){
                                    readerCount++;
                                }
                            }
                        }
                        if(readerCount==fieldList.size()){
                            area.setSubDisplayFlag(1);
                        }
                        if(writeCount==fieldList.size()&&readerCount!=fieldList.size()){
                            area.setSubDisplayFlag(2);
                        }
                    }
                    area.setFieldList(fields);
                    if(fields!=null&&fields.size()!=0){
                        area.setVisibleFlag("0");
                    }else{
                        iter.remove();
                    }

//                    if(area.getFormType()!=null&&area.getFormType()==1&&area.getSubDisplayFlag()!=1){
//                        List<FMFormInstance> formInstanceList=listSubFormFieldValueByInsAreaId(businessFlag.getProcessInstanceID(),area.getId());
//                        area.setFormInstanceList(formInstanceList);
//                    }
                }

                return formAreas;

        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    public FMFormInstance getFormFieldValueById(Integer processInstanceID) throws Exception {

        FMFormInstance formInstance = new FMFormInstance();

        List<CaseFormData> caseFormDataList = caseFormDataMapper.get(processInstanceID);
        formInstance.setId(processInstanceID);
        for(CaseFormData caseFormData : caseFormDataList){
            formInstance.getFields().put(caseFormData.getFieldName(),caseFormData.getFieldValue());
        }
        return formInstance;
    }


//    public List<FMFormInstance> listSubFormFieldValueByInsAreaId(String processInstanceID,int areaID) throws Exception {
//        Map map = new HashMap();
//        map.put("processInstanceID",processInstanceID);
//        map.put("areaID",areaID);
//        List<CaseFormData> caseSubFormDataList = caseFormDataService.list(map);
//        List<FMFormInstance> fmFormInstanceList = new ArrayList<FMFormInstance>();
//        for(CaseFormData caseSubFormData : caseSubFormDataList){
//            FMFormInstance fmFormInstance = new FMFormInstance();
//            fmFormInstance.setId(caseSubFormData.getSubFormObjId());
//            if(fmFormInstanceList.contains(fmFormInstance)){
//                int aa = fmFormInstanceList.indexOf(fmFormInstance);
//                fmFormInstance = fmFormInstanceList.get(aa);
//                if(caseSubFormData.getFieldType().equals("select")||caseSubFormData.getFieldType().equals("radio")){
//                    if (null != caseSubFormData.getFieldValue() && caseSubFormData.getFieldValue().trim().length() >0){
//                        Pattern pattern = Pattern.compile("[0-9]*");
//                        Matcher isNum = pattern.matcher(caseSubFormData.getFieldDataSource());
//                        if( !isNum.matches() ){
//                            if("customDataSource".equals(caseSubFormData.getFieldDataSource())){
//                                HashMap hashMap=new HashMap();
//                                hashMap.put("nameMatch",caseSubFormData.getFieldName());
//                                List<FieldForm> fieldFormList=fieldFormService.getFieldFormList(hashMap);
//                                if(fieldFormList.size()>0){
//                                    List<EntityObject> objects=operateFormService.queryDataSource(fieldFormList.get(0).getDataSourceAddress());
//                                    for(EntityObject entityObject:objects){
//                                        if(caseSubFormData.getFieldValue().equals(entityObject.getId())){
//                                            fmFormInstance.getFields().put(caseSubFormData.getFieldName(),entityObject.getName());
//                                        }
//                                    }
//                                }
//                            }else{
//                                String sql="SELECT ID, NAME FROM "+caseSubFormData.getFieldDataSource()+" WHERE ID="+caseSubFormData.getFieldValue();
//                                List list=jdbcTemplate.queryForList(sql);
//                                if(list!=null&&list.size()>0){
//                                    HashMap hashMap=(HashMap)list.get(0);
//                                    fmFormInstance.getFields().put(caseSubFormData.getFieldName(),String.valueOf(hashMap.get("NAME")));
//                                }
//                            }
//                        }else{
//                            IEntityObjectService objectService = new DSDictServiceImpl();
//                            EntityObject object = objectService.getObjBySubId(caseSubFormData.getFieldValue());
//                            if (null != object && object.getId()!=null && !"".equals(object.getId())) {
//                                fmFormInstance.getFields().put(caseSubFormData.getFieldName(),object.getName());
//                            } else {
//                                fmFormInstance.getFields().put(caseSubFormData.getFieldName(),"");
//                            }
//                        }
//                    }
//
//                }else{
//                    fmFormInstance.getFields().put(caseSubFormData.getFieldName(),caseSubFormData.getFieldValue());
//                }
//            }else{
//                if(caseSubFormData.getFieldType().equals("select")||caseSubFormData.getFieldType().equals("radio")){
//                    if (null != caseSubFormData.getFieldValue() && caseSubFormData.getFieldValue().trim().length() >0){
//                        Pattern pattern = Pattern.compile("[0-9]*");
//                        Matcher isNum = pattern.matcher(caseSubFormData.getFieldDataSource());
//                        if( !isNum.matches() ){
//                            if("customDataSource".equals(caseSubFormData.getFieldDataSource())){
//                                HashMap hashMap=new HashMap();
//                                hashMap.put("nameMatch",caseSubFormData.getFieldName());
//                                List<FieldForm> fieldFormList=fieldFormService.getFieldFormList(hashMap);
//                                if(fieldFormList.size()>0){
//                                    List<EntityObject> objects=operateFormService.queryDataSource(fieldFormList.get(0).getDataSourceAddress());
//                                    for(EntityObject entityObject:objects){
//                                        if(caseSubFormData.getFieldValue().equals(entityObject.getId())){
//                                            fmFormInstance.getFields().put(caseSubFormData.getFieldName(),entityObject.getName());
//                                        }
//                                    }
//                                }
//                            }else{
//                                String sql="SELECT ID, NAME FROM "+caseSubFormData.getFieldDataSource()+" WHERE ID="+caseSubFormData.getFieldValue();
//                                List list=jdbcTemplate.queryForList(sql);
//                                if(list!=null&&list.size()>0){
//                                    HashMap hashMap=(HashMap)list.get(0);
//                                    fmFormInstance.getFields().put(caseSubFormData.getFieldName(),String.valueOf(hashMap.get("NAME")));
//                                }
//                            }
//                        }else{
//                            IEntityObjectService objectService = new DSDictServiceImpl();
//                            EntityObject object = objectService.getObjBySubId(caseSubFormData.getFieldValue());
//                            if (null != object && object.getId()!=null && !"".equals(object.getId())) {
//                                fmFormInstance.getFields().put(caseSubFormData.getFieldName(),object.getName());
//                            } else {
//                                fmFormInstance.getFields().put(caseSubFormData.getFieldName(),"");
//                            }
//                        }
//                    }
//                }else{
//                    fmFormInstance.getFields().put(caseSubFormData.getFieldName(),caseSubFormData.getFieldValue());
//                }
//                fmFormInstanceList.add(fmFormInstance);
//            }
//        }
//
//        return fmFormInstanceList;
//    }
}

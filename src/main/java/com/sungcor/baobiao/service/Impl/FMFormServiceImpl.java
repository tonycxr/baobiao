package com.sungcor.baobiao.service.Impl;

import com.sungcor.baobiao.entity.FMForm;
import com.sungcor.baobiao.mapper.FormMapper;
import com.sungcor.baobiao.service.IFMFormService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FMFormServiceImpl implements IFMFormService {
    @Autowired
    private FormMapper formMapper;

    public FMForm getFMForm(Integer formID) {
        FMForm form = new FMForm();
        form.setId(formID);
//        IMemcache<FMForm> fMFormCache = MemCacheFactory.getRemoteMemCache(FMForm.class);
//        try{
//
//            FMForm  fMForm = fMFormCache.get("FMForm_"+formID);
//            if(null != fMForm){
//                return fMForm;
//            }
//        }catch (Exception e){
//            e.printStackTrace();
//        }
        form =  formMapper.getFMForm(form);
//        fMFormCache.set("FMForm_"+formID,form);
        return form;
    }
}

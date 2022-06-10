package com.sungcor.baobiao.service.Impl;

import com.sungcor.baobiao.entity.FMFormArea;
import com.sungcor.baobiao.mapper.FMFormAreaMapper;
import com.sungcor.baobiao.service.IFMFormAreaService;
import com.sungcor.baobiao.service.IFMFormFieldService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class FMFormAreaServiceImpl implements IFMFormAreaService {

    @Autowired
    private FMFormAreaMapper formAreaMapper;

    @Autowired
    private IFMFormFieldService fieldService;

    public void insert(FMFormArea area) {
        formAreaMapper.insert(area);
    }

    public void update(FMFormArea area) {
        formAreaMapper.update(area);
    }

    public void delete(FMFormArea area) {
        formAreaMapper.delete(area);
    }

    public List<FMFormArea> search(Integer versionID) {
        return formAreaMapper.search(versionID);
    }

    public java.util.List<FMFormArea> searchAndFieldList(Integer versionID){
            List<FMFormArea> areaList = formAreaMapper.search(versionID);
            for(FMFormArea area : areaList){
                area.setFieldList(fieldService.search(area.getId()));
            }
            return areaList;
    }

    public java.util.List<FMFormArea> searchAndFieldListByAreaID(Integer areaID){

            FMFormArea fmFormArea = formAreaMapper.getFMFormArea(areaID);
            List<FMFormArea> areaList=new ArrayList<FMFormArea>();
            areaList.add(fmFormArea);
            for(FMFormArea area : areaList){
                area.setFieldList(fieldService.search(area.getId()));
            }
            return areaList;


    }

    public java.util.List<FMFormArea> searchAndFieldListNoCache(Integer versionID){
        List<FMFormArea> areaList = formAreaMapper.search(versionID);
        for(FMFormArea area : areaList){
            area.setFieldList(fieldService.search(area.getId()));
        }
        return areaList;
    }

    public FMFormArea getFMFormArea(Integer areaID) {
        return formAreaMapper.getFMFormArea(areaID);
    }

    public Boolean checkAreaUnique(Map param){
        return formAreaMapper.checkAreaUnique(param)>0?false:true;
    }
}

package com.sungcor.baobiao.service;

import com.sungcor.baobiao.entity.FMFormArea;

import java.util.List;
import java.util.Map;

public interface IFMFormAreaService {

     void  insert(FMFormArea area);

     void  update(FMFormArea area);

     void  delete(FMFormArea area);

     List<FMFormArea> search(Integer versionID);

     List<FMFormArea> searchAndFieldList(Integer versionID);

     List<FMFormArea> searchAndFieldListByAreaID(Integer areaID);

     List<FMFormArea> searchAndFieldListNoCache(Integer versionID);

     FMFormArea getFMFormArea(Integer areaID);

     Boolean checkAreaUnique(Map param);
}

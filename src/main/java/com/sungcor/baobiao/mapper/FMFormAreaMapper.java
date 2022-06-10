package com.sungcor.baobiao.mapper;

import com.sungcor.baobiao.entity.FMFormArea;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.util.Map;

@Mapper
@Component
@Repository
public interface FMFormAreaMapper {
    public void  insert(FMFormArea area);

    public void  update(FMFormArea area);

    public void  delete(FMFormArea area);

    public java.util.List<FMFormArea> search(Integer versionID);

    public FMFormArea getFMFormArea(Integer areaID);

    public int checkAreaUnique(Map param);

}

package com.sungcor.baobiao.mapper;

import com.sungcor.baobiao.entity.FMFormVersion;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

@Mapper
@Component
@Repository
public interface FMFormVersionMapper {
    public void  insert(FMFormVersion version);

    public void  update(FMFormVersion version);

    public void  delete(FMFormVersion version);

    public java.util.List<FMFormVersion> search(java.util.HashMap parms);

    public java.util.List<FMFormVersion> searchSubVersion(Integer versionID);

    public FMFormVersion getFMFormVersion(Integer id);

    public java.util.List<java.util.HashMap> getFormVersionCount(java.util.HashMap parms);

    public java.util.List<FMFormVersion> getVersionByFormID(Integer formID);
}

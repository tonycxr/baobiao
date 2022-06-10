package com.sungcor.baobiao.mapper;

import com.sungcor.baobiao.entity.FieldForm;
import com.sungcor.baobiao.entity.FormAccessControl;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Mapper
@Component
@Repository
public interface AccessControlMapper {
    public void  insert(FormAccessControl accessControl);

    public void  delete(String bussis);

    public List<FormAccessControl> getListByCondition(Map map);

//    public void SaveSparepart(HashMap map);
//
//
//
//    public  void DeleteSparepart(HashMap map);


    public List<FieldForm> getSubFormField(String subFormId);
}

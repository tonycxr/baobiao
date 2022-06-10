package com.sungcor.baobiao.mapper;

import com.sungcor.baobiao.entity.BusinessOpt;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Mapper
@Component
@Repository
public interface BusinessOptMapper {
    void save(BusinessOpt businessOpt);

    void update(BusinessOpt businessOpt);

    BusinessOpt getBusinessOptByNhAndOpType(Map para);

    List<BusinessOpt> getBusinessOptByPID(int id);

    BusinessOpt getBusinessOptByPIDAndNhId(Map para);

}

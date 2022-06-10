package com.sungcor.baobiao.mapper;

import com.sungcor.baobiao.entity.ProcessDefinition;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Mapper
@Component
@Repository
public interface ProcessDefinitionMapper {
    public ProcessDefinition get(Integer id);

    public void save(ProcessDefinition pd) ;

    public void delete(Integer id) ;

    public void update(ProcessDefinition pd);

    public List<ProcessDefinition> listProcessDefByPid(Map param);

    public int getProcessDefCountByPid(Map param);

    public ProcessDefinition getProcessDefinitionByPinAndVersion(Map para);

}

package com.sungcor.baobiao.mapper;

import com.sungcor.baobiao.entity.NodeHistory;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Mapper
@Component
@Repository
public interface NodeHistoryMapper {
    void  update(NodeHistory nodeHistory) ;
    void save(NodeHistory nodeHistory);
    void delete(Integer id);
    NodeHistory  get( Integer id) ;
    List<NodeHistory> getByPIDAndNodeInsID(Map map);
    NodeHistory getRunNodeHiss(Map map);
    NodeHistory getNodeHiss(Map map);
    NodeHistory getByRunFlagAndNodeInstnce(Map map);

    List<NodeHistory> getNoRunByPIDAndNodeName(Map map);

    List<NodeHistory> listNodeHisByProInsID(Integer id);
    List<NodeHistory> listNodeHisRunning(Integer instanceId);
    List<NodeHistory> listNodeHisAside(Integer instanceId);
}

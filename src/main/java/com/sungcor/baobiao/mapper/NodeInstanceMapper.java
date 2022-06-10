package com.sungcor.baobiao.mapper;

import com.sungcor.baobiao.entity.NodeInstance;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;


@Mapper
@Component
@Repository
public interface NodeInstanceMapper {
    /**
     *根据ID查询QBPM流程节点实例对象
     * @param pid
     * @return
     */
    NodeInstance get(Integer pid);

    void save(NodeInstance nodeInstance);

    void saveBatch(List<NodeInstance> nodeInstanceList);

    void update(NodeInstance nodeInstance);

    NodeInstance getNodeInstanceByNameAndPID(Map map );

    NodeInstance getNodeInstanceByUniqueIDAndPID(Map map );

    List<NodeInstance> listNodeInstnceByProInsID(Integer id);

    List<NodeInstance> listRunNodeInstnces();

}

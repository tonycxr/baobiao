package com.sungcor.baobiao.mapper;

import com.sungcor.baobiao.entity.ProcessInstance;
import com.sungcor.baobiao.entity.ServiceCategory;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Mapper
@Component
@Repository
public interface ProcessInstanceMapper {
    /**
     * 根据ID查询QBPM流程实例对象
     *
     * @param pid
     * @return
     */
    public ProcessInstance get(Integer pid);

    public ProcessInstance getSLAByPID(Integer pid);

    public ProcessInstance getBaseInfo(Integer pid);

    void save(ProcessInstance processInstance);

    void update(ProcessInstance processInstance);

    int countByPidAndHangModel(Map map);

    int countByHangModel(Map map);

    void replaceSaveProcessRelation(Map map);

    List<Integer> getCongRelationProcessIds(Integer id);

    List<Integer> getRelationProcessIds(Integer id);

    List<ProcessInstance> getRelatedPI1(Map para);

    List<ProcessInstance> getRelatedPI2(Integer id);

    public ServiceCategory getServiceCategoryByPID(Integer id);

    public void deleteProcessRelation(Map para);

    public void visitEvaluation(Map para);

    public Map selectVisitEvaluation(Integer processInstanceID);


    public Integer deleteEvaluation(Map para);

    /**
     * 获取走过的路径
     * @param processInstanceId  流程实例ID
     * @return
     */
    public List<Map> getRunOverTransitions(Integer processInstanceId);

    /**
     * 查询需要代办的工单
     * @return
     */

    public  List<ProcessInstance> queryToDoSLA(int page);

    public String findCloseCode(String processInstanceID);

    public int getProcessInstanceVoucherCount(HashMap map);

    public List<ProcessInstance> getProcessInstanceVoucherList(HashMap map);

    public int getProcessInstanceCiCount(HashMap map);

    public List<ProcessInstance> getProcessInstanceCiList(HashMap map);

    public ProcessInstance  getSelfdesk(Map map);
    //判断自助服务是否已全部处理完成
    public int isSelfdesk(Map map);

}

package com.sungcor.baobiao.service.Impl;

import com.sungcor.baobiao.entity.*;
import com.sungcor.baobiao.entity.Process;
import com.sungcor.baobiao.mapper.*;
import com.sungcor.baobiao.service.IProcessControlService;
import com.sungcor.baobiao.service.IProcessManagementService;
import com.sungcor.baobiao.service.IQBPMStaffService;
import com.sungcor.baobiao.utils.BusinessHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class ProcessControlServiceImpl implements IProcessControlService {

    @Autowired
    private IProcessManagementService processManagementService;

    @Autowired
    private ProcessInstanceMapper processInstanceMapper;

    @Autowired
    private NodeHistoryMapper nodeHistoryMapper;

    @Autowired
    private NodeInstanceMapper nodeInstanceMapper;

    @Autowired
    private BusinessOptMapper businessOptMapper;

    @Autowired
    private ReviewInfoMapper reviewInfoMapper;

    @Autowired
    private IQBPMStaffService qbpmStaffService;
    @Override
    public ProcessInstance getProcessInstance(Integer id) {
        ProcessInstance processInstance = processInstanceMapper.get(id);
//
        processInstance.setNodeInstances(listNodeInstnceByProInsID(processInstance.getId()));
        processInstance.setNodeHistorys(nodeHistoryMapper.listNodeHisByProInsID(processInstance.getId()));
        ProcessDefinition pd = processManagementService.getProcessDefinitionById(processInstance.getProcessDefinitionID());
        Process process =processManagementService.getProcessById(pd.getProcessID()) ;
        pd.setQbpmProcess(process);
        processInstance.setProcessDefinition(pd);
        processInstance.setProcess(process);
        return processInstance;
    }

    @Override
    public Map selectVisitEvaluation(Integer processInstanceID){
        return processInstanceMapper.selectVisitEvaluation(processInstanceID);
    }

    @Override
    public Set<NodeHistory> getRunNodeHistorys(ProcessInstance processInstance){
        Set<NodeHistory> nodeHistorySet = new HashSet<NodeHistory>();
        for(NodeHistory nodeHis : processInstance.getNodeHistorys()){
            if(nodeHis.getRunFlag()== BusinessHelper.RUN_FLAG_1){
                nodeHistorySet.add(nodeHis);
            }
        }
        return nodeHistorySet;
    }

    @Override
    public List<BusinessOpt> listBusinessOpts(Integer processInstanceID) throws Exception {
        List<BusinessOpt> busOptList = businessOptMapper.getBusinessOptByPID(processInstanceID);
        for(BusinessOpt busOpt : busOptList){
            if (null != busOpt.getOptPersonID()){
                busOpt.setOptPerson(qbpmStaffService.getStaffById(Integer.parseInt(busOpt.getOptPersonID())));
            }
            NodeHistory nodeHistory=nodeHistoryMapper.get(busOpt.getNodeHistoryID());
            busOpt.setNodeHistory(nodeHistory);
            if(busOpt.getOptType().equals(BusinessHelper.REVIEW)){
                NodeInstance nodeInstance=nodeInstanceMapper.get(nodeHistory.getNodeInstanceID());
                Map map=new HashMap();
                map.put("nodeHistoryId",busOpt.getNodeHistoryID());
                List<ReviewInfo> reviewInfos=new ArrayList<ReviewInfo>();
                if(nodeInstance.getPerformAllFlag()==1){
                    reviewInfos = reviewInfoMapper.listRevieInfoByNh2(map);
                }else{
                    map.put("optTimeFlag","true");
                    reviewInfos = reviewInfoMapper.listRevieInfoByNh2(map);
                }
                for(ReviewInfo ri : reviewInfos){
                    if (null != ri.getOptPersonID())
                        ri.setOptPerson(qbpmStaffService.getStaffById(Integer.parseInt(ri.getOptPersonID())));
                }
                busOpt.setReviewInfos(reviewInfos);
            }
        }
        return busOptList;
    }

    public List<NodeInstance> listNodeInstnceByProInsID(Integer bpID){
        return nodeInstanceMapper.listNodeInstnceByProInsID(bpID);
    }
}

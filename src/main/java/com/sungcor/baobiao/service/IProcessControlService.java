package com.sungcor.baobiao.service;

import com.sungcor.baobiao.entity.*;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

public interface IProcessControlService {
    public ProcessInstance getProcessInstance(Integer id);

    public Map selectVisitEvaluation(Integer processInstanceID);

    public Set<NodeHistory> getRunNodeHistorys(ProcessInstance processInstance);

    public List<BusinessOpt> listBusinessOpts(Integer processInstanceID) throws Exception;
}

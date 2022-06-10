package com.sungcor.baobiao.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.sungcor.baobiao.entity.Process;
import com.sungcor.baobiao.entity.ProcessDefinition;

public interface IProcessManagementService {
    public final String auditChange = "auditStandardChange";
    public final String noAuditChange = "noAuditStandardChange";
    /**
     * 查询符合条件的基本信息
     *
     * @param id
     * @return
     */
    public Process getProcessById(Integer id);

    public List<Process> getProcessByName(String name);


    public ProcessDefinition getProcessDefinitionById(int id);
}

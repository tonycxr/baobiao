package com.sungcor.baobiao.service.Impl;

import com.sungcor.baobiao.entity.ProcessDefinition;
import com.sungcor.baobiao.mapper.ProcessDefinitionMapper;
import com.sungcor.baobiao.mapper.ProcessMapper;
import com.sungcor.baobiao.service.IProcessManagementService;
import org.springframework.beans.factory.annotation.Autowired;
import com.sungcor.baobiao.entity.Process;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProcessManagementServiceImpl implements IProcessManagementService {

    @Autowired
    private ProcessMapper processMapper;

    @Autowired
    private ProcessDefinitionMapper processDefinitionMapper;
    @Override
    public Process getProcessById(Integer id) {
        Process process = null;
//        IMemcache<Process> processCache = MemCacheFactory.getRemoteMemCache(Process.class);
//        try{
//
//            process = processCache.get("Process_"+id.toString());
//        }catch (Exception e){
//            e.printStackTrace();
//        }
        if(null ==process ){
            process = processMapper.get(id);
            process.setCurrentProcessDefinition(processDefinitionMapper.get(process.getProcessDefinitionID()));

//            processCache.set("Process_"+id.toString(),process);
        }

//        初始化表单信息
//  unmarkisneeded       if(process.getFormID()!=null){
//            FMForm fmeForm = formDAO.get(process.getFormID());
//            if (fmeForm != null) {
//                process.setFormName(fmeForm.getTitle());
//                if(fmeForm.getCurrentVersionID()!=null)
//                    process.setFormVersion(String.valueOf(fmeForm.getCurrentVersionID()));
//            }
//        }
        return process;
    }

    @Override
    public List<Process> getProcessByName(String name) {
        return null;
    }


    @Override
    public ProcessDefinition getProcessDefinitionById(int id) {
            ProcessDefinition pd = processDefinitionMapper.get(id);
            if (pd != null && pd.getProcessID() != null) {
                pd.setQbpmProcess(processMapper.get(pd.getProcessID()));
            }
            return pd;

    }

}

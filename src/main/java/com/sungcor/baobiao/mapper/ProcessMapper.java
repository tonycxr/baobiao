package com.sungcor.baobiao.mapper;

import com.sungcor.baobiao.entity.MyworkBean;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import com.sungcor.baobiao.entity.Process;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Mapper
@Component
@Repository
public interface ProcessMapper {
    public Process get(Integer id);

    public List<Process> getByName(String id);

    public void save(Process pro) ;

    public void delete(Integer id);

    public void update(Process pro);

    public List<Process> listProcessByOrgs(Map parm);

    public int getProcessCountByOrgs(Map parm);

    public List<Process>  listAvailableProcessByOrgs(Map parm);
    /**
     * 根据分类ID关联表单查询可用的流程
     * @param parm
     * @return
     */
    public List<Process>  listAvailableProcessByOrgsAndCategoryId(Map parm);

    public Integer checkProductRelDelete(Integer id);

    public Integer checkProcessInsDelete(Integer id);


    public List<MyworkBean> queryFormTable(HashMap param);
    public List<MyworkBean> queryFormInfo(HashMap param);

    /**
     * 根据流程版本id查询对应流程所属组织id
     */
    public Integer getOrgIdByProcessDefId(Integer id);
}

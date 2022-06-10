package com.sungcor.baobiao.mapper;

import com.sungcor.baobiao.entity.Organise;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Mapper
@Component
@Repository
public interface OrganiseMapper {
    /**
     * 根据父ID查询子组织对象集合
     * @param id  父ID
     * @return 组织对象集合
     */
    public java.util.List<Organise> getOrganiseListByParentID(int id);
    /**
     * 根据ID查询组织对象
     * @param id
     * @return 组织对象
     */
    public java.util.List<Organise>  getOrganiseById(int id);
    /**
     * 增加组织
     * @param org 组织对象
     * @return
     */
    public int insertOrganise(Organise org);
    /**
     * 根据ID删除组织
     * @param id
     */
    public void deleteOrganiseByID(int id);
    /**
     * 修改组织
     * @param org 组织对象
     * @return
     */
    public int updateOrganiseByID(Organise org);
    /**
     * 根据父ID获得只组织数量
     * @param map 参数集合
     * @return
     */
    public Map getOrganiseCountByParentID(Map map);

    /**
     *    根据父ID查询组织对象 （带分页）
     * @param map
     * @return
     */
    public java.util.List<Organise>  getChildrenOrganises(Map map);

    /**
     *    查询指定组织下的最大排序号sort
     * @param map
     * @return
     */
    public Map getMaxOrganiseSortByParentID(Map map);

    /**
     *  查询关联了用户的组织
     * @param item
     * @return
     */
    public List<HashMap> getRelatedUserOrgs(Integer[] item);

    /**
     * 查询关联了子组织的组织
     * @param item
     * @return
     */
    public List<HashMap> getRelatedChildrenOrgs(Integer[] item);

    /**
     * 插入节点前修改sort号  1
     * @param map
     */
    public void updateSortBefore1(Map map);

    /**
     * 插入节点前修改sort号2
     * @param map
     */
    public void updateSortBefore2(Map map);

    /**
     * 插入节点后修改sort号   1
     * @param map
     */
    public void updateSortAfter1(Map map);

    /**
     * 插入节点后修改sort号   2
     * @param map
     */
    public void updateSortAfter2(Map map) ;

    /**
     * 插入另一节点中前修改sort号
     * @param map
     */
    public void updateSortBeforeAntoherOrg(Map map) ;

    /**
     * 插入另一节点中后修改sort号
     * @param map
     */
    public void updateSortAfterAntoherOrg(Map map) ;
    /**
     * 取得全部组织
     * @return
     */
    public List<Organise> getAll();

    public List<Organise> findAllChildOrg(int id);

    /**
     * 根据第三方id 查询系统id
     * @param id        第三方id
     * @return             系统id
     */
    public Object getSysIdBy3rdId(int id);

    /**
     * 根据上级部门ID查询
     * @param id
     * @return
     */
    public List<Organise> getOrgsByGovId(int id);

    /**
     * 根据code查询
     * @param code
     * @return
     */
    public List<Organise> getOrgsByCode(String code);

    /**
     * 通过名称查询数量
     * @param name
     * @return
     */
    public List<Organise> getOrgsByName(String name);

    void deleteOrgsByCodes(List<String> code);
}

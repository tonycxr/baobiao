package com.sungcor.baobiao.service;

import com.sungcor.baobiao.entity.Organise;
import com.sungcor.baobiao.entity.UserInfoBean;

import java.util.HashMap;
import java.util.List;
import java.util.Map;



public interface IOrganiseService {
    /**
     * 加载组织树
     */
    public List<Organise> initOrganiseTreeDate();


    public List<Organise> initOrganiseTreeDate(int level);

    /**
     * 加载组织树
     * @param orgid
     * @return
     */
    public List<Organise> initOrganiseTreeDate(Integer orgid);

    /**
     * 加载组织树
     * @param operUser
     */
    public   List<Organise> initOrganiseTreeDate(UserInfoBean operUser);


    public List<Organise> initOrganiseTreeDate(Integer orgid,int level);

    public List<Organise> initOrganiseTreeDate(UserInfoBean operUser,int parentOrgID);
    /**
     * 查询子组织数量
     * @param map
     * @return
     */
    public int getChildrenOrganiseCount(Map map);

    /**
     * 查询子组织列表(带分页)
     * @param map
     * @return
     */
    public List<Organise> listChildrenOrganises(Map map);

    /**
     * 调整组织树位置
     * @param nodeID
     * @param tragetNodeID
     * @param parentNode
     * @param tragetNodeID
     * @param moveType "inner"：成为子节点，"prev"：成为同级前一个节点，"next"：成为同级后一个节点
     */
    public boolean updatePositionOrganise(String nodeID,String tragetNodeID,String parentNode,String targetParentNode,String nodeSort ,String targetNodeSort,String moveType);

    /**
     * 新增组织部门
     * @param organise
     * @return
     */
    public Organise insertOrganise(Organise organise)  throws Exception;
    public Boolean checkInsert(String code);

    /**
     * 编辑组织部门
     * @param organise
     * @return
     */
    public void updateOrganise(Organise organise) throws  Exception;

    /**
     * 根据ID查询组织部门
     * @param id
     * @return
     */
    public Organise getOrganiseByID(Integer id);

    /**
     * 组织删除验证
     * @param ids
     * @return
     */
    public java.util.Map checkDelete(String ids);

    /**
     * 删除组织部门
     * @param id
     */
    public void deleteOrganiseByID(int id);

    public List<Integer> getOrganiseTreeIds(int id);

    public List<Organise> getAll();

    // 递归查询所有子组织
    public List<Organise> findAllChildOrg(int id);

    public Object getSystemIdBy3rdId(int id);

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
    public int checkByName(String name);

    /**
     * 查询给定组织的根组织（根组织下的第一级组织）
     * @param id
     * @return
     */
    public Organise getRootOrgByCurrentOrgId(int id);

    public List<Organise> initOrganiseTreeParentIDDate(int level);

    public List<Organise> loadTopOrg();

    public String importOrganise(HashMap map) throws Exception;

    public List<Organise> getOrganiseByName(String name);
}

package com.sungcor.baobiao.service;

import com.sungcor.baobiao.entity.Area;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public interface IAreaService {
    /**
     * 加载区域树
     */
    public List<Area> initAreaTreeDate();

    /**
     * 查询子区域数量
     * @param map
     * @return
     */
    public int getChildrenAreaCount(Map map);
    /**
     * 根据编号查询区域数量
     * @param map
     * @return
     */
    public int getAreaCountByCode(Map map);

    /**
     * 根据名称查询区域数量
     * @param map
     * @return
     */
    public int getAreaCountByName(Map map);

    public int getAreaUpdateCountByName(Map map);

    /**
     * 查询子区域列表(带分页)
     * @param map
     * @return
     */
    public List<Area> listChildrenAreas(Map map);

    /**
     * 调整区域树位置
     * @param nodeID
     * @param tragetNodeID
     * @param parentNode
     * @param tragetNodeID
     * @param moveType "inner"：成为子节点，"prev"：成为同级前一个节点，"next"：成为同级后一个节点
     */
    public boolean updatePositionArea(String nodeID,String tragetNodeID,String parentNode,String targetParentNode,String nodeSort ,String targetNodeSort,String moveType);

    /**
     * 新增区域
     * @param Area
     * @return
     */
    public void insertArea(Area area);

    /**
     * 编辑区域
     * @param organise
     * @return
     */
    public void updateArea(Area area);

    /**
     * 根据ID查询区域
     * @param id
     * @return
     */
    public Area getAreaByID(Integer id);

    /**
     * 区域删除验证
     * @param ids
     * @return
     */
    public java.util.Map checkDelete(String ids);

    /**
     * 删除区域
     * @param id
     */
    public void deleteAreaByID(int id);

    /**
     * 加载简单区域数据
     * @return
     */
    public List<Area> initAreaTreeSimpleDate();

    public List findAppByArea(int areaid);

    public void saveupdateAppArea(int areaid,int[] applist);

    /**
     * 根据第三方id 查询系统id
     * @param id        第三方id
     * @return             系统id
     */
    public Object getSystemIdBy3rdId(int id);

    /**
     * 无锡信息办移植添加方法
     * @param areaName
     * @return
     */
    public List<Area> selectAreaList(String areaName);

    public String importArea(HashMap map);

    public List<Area> queryAreaCountByCode(String code);

    public Area insertAreaReturn(Area area);
}

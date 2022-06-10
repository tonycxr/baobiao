package com.sungcor.baobiao.dao;

import com.sungcor.baobiao.entity.Area;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public interface IAreaDAO {
    /**
     * 根据父ID查询子区域对象集合
     * @param id  父ID
     * @return 区域对象集合
     */
    public java.util.List<Area> getAreaListByParentID(int id);
    /**
     * 根据ID查询区域对象集合
     * @param id
     * @return 区域对象集合
     */
    public Area getAreasById(int id);
    /**
     * 增加区域
     * @param area 区域对象
     * @return
     */
    public int insertArea(Area area);
    /**
     * 根据ID删除区域
     * @param id
     */
    public void deleteAreaByID(int id);
    /**
     * 修改区域
     * @param area 区域对象
     * @return
     */
    public int updateAreaByID(Area area);
    /**
     * 根据父ID获得子区域数量
     * @param map 参数集合
     * @return
     */
    public Map getAreaCountByParentID(Map map);

    /**
     * 根据code获得区域数量
     * @param map 参数集合
     * @return
     */
    public Map getAreaCountByCode(Map map);

    /**
     * 根据name获得区域数量
     * @param map 参数集合
     * @return
     */
    public Map getAreaCountByName(Map map);
    /**
     * 根据父ID查询子区域对象集合 （带分页）
     * @param map
     * @return
     */
    public Map getAreaUpdateCountByName(Map map);
    public java.util.List<Area>  getChildrenAreas(Map map);

    /**
     * 查询指定区域下的最大排序号sort
     * @param map
     * @return
     */
    public Map getMaxAreaSortByParentID(Map map);

    /**
     * 查询关联了子区域的区域
     * @param item
     * @return
     */
    public List<HashMap> getRelatedChildrenAreas(Integer[] item);

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
    public void updateSortBeforeAntoherArea(Map map) ;

    /**
     * 插入另一节点中后修改sort号
     * @param map
     */
    public void updateSortAfterAntoherArea(Map map) ;
    /**
     * 得到所有区域
     * @return
     */
    public java.util.List<Area> getAllArea();

    public List findAppByArea(int areaid);
    public void deleteAppsByArea(int areaid);
    public void saveAppsByArea(HashMap param);

    /**
     * 根据第三方id 查询系统id
     * @param id        第三方id
     * @return             系统id
     */
    public Object getSysIdBy3rdId(int id);

    /**
     * 无锡信息办移植功能
     * @param map
     * @return
     */
    public List<Area> selectAreaList(Map map);

    public List<Area> queryAreaCountByCode(String code);

    //获取人员下所有数据域包含的区域
    public List<Area> getAllRegionArea(HashMap map);
}

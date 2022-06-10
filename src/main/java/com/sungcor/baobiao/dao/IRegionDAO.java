package com.sungcor.baobiao.dao;

import com.sungcor.baobiao.entity.Area;
import com.sungcor.baobiao.entity.Region;
import com.sungcor.baobiao.entity.RegionItem;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public interface IRegionDAO {
    /**
     * 根据父ID查询子数据域对象集合
     * @param id  父ID
     * @return 数据域对象集合
     */
    public List<Region> getRegionListByParentID(int id);
    /**
     * 根据ID查询数据域对象集合
     * @param id
     * @return 数据域对象集合
     */
    public Region getRegionsById(int id);
    /**
     * 增加数据域
     * @param region 数据域对象
     * @return
     */
    public int insertRegion(Region region);
    /**
     * 根据ID删除数据域
     * @param id
     */
    public void deleteRegionByID(int id);
    /**
     * 修改数据域
     * @param region 数据域对象
     * @return
     */
    public int updateRegionByID(Region region);
    /**
     * 根据父ID获得子数据域数量
     * @param map 参数集合
     * @return
     */
    public Map getRegionCountByParentID(Map map);

    /**
     * 根据code获得数据域数量
     * @param map 参数集合
     * @return
     */
    public Map getRegionCountByCode(Map map);

    /**
     * 根据name获得数据域数量
     * @param map 参数集合
     * @return
     */
    public Map getRegionCountByName(Map map);
    /**
     * 根据父ID查询子数据域对象集合 （带分页）
     * @param map
     * @return
     */
    public Map getRegionUpdateCountByName(Map map);
    public List<Region>  getChildrenRegions(Map map);

    /**
     * 查询指定数据域下的最大排序号sort
     * @param map
     * @return
     */
    public Map getMaxRegionSortByParentID(Map map);

    /**
     * 查询关联了子数据域的数据域
     * @param item
     * @return
     */
    public List<HashMap> getRelatedChildrenRegions(Integer[] item);

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
    public void updateSortBeforeAntoherRegion(Map map) ;

    /**
     * 插入另一节点中后修改sort号
     * @param map
     */
    public void updateSortAfterAntoherRegion(Map map) ;
    /**
     * 得到所有数据域
     * @return
     */
    public List<Region> getAllRegion();

    /**
     * 无锡信息办移植功能
     * @param map
     * @return
     */
    public List<Area> selectAreaList(Map map);

    public List<Area> queryAreaCountByCode(String code);

    public void deleteOrganise(Map map);

    public void insertOrganise(Map map);

    public void deleteArea(Map map);

    public void insertArea(Map map);

    public void deleteUserRegion(Map map);

    public void insertUserRegion(Map map);

    //获取所勾选的组织
    public List<HashMap> getOrganise(String regionId);

    public List<HashMap> getAreaChecked(String regionId);

    public List<HashMap> getUserRegion(String userid);

    public List<RegionItem> getRegionItems(Object object);

    public List<Region> getRegionList(Object object);

    public List<HashMap> getProductRegion(Integer id);

    public Region getRegionByCode(String code);

    public void deleteUserForRegion(HashMap map);

    public void insertQuickCreateDefault(HashMap map);

    public List<HashMap> getQuickCreateDefault(int userId);

    public void updateQuickCreateDefault(HashMap map);

    public void deleteCategory(Map map);

    public void insertCategory(Map map);

    public List<HashMap> getCategoryChecked(String regionId);
}

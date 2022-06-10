package com.sungcor.baobiao.service;

import com.sungcor.baobiao.entity.CategoryArea;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public interface ICategoryAreaService {
    /**
     * 根据分类ID获取该分类下所有栏目
     * @param cateId
     * @return
     */
    public List<CategoryArea> getListCateAreaByCateId(int cateId);

    /**
     * 新增栏目
     * @param area
     * @return
     */
    public int insert(CategoryArea area);

    /**
     * 删除栏目
     * @param areaId
     */
    public void delete(int areaId);

    /**
     * 根据Id获取栏目
     * @param areaId
     * @return
     */
    public CategoryArea getAreaById(int areaId);

    /**
     * 更新栏目
     * @param area
     */
    public void update(CategoryArea area);

    /**
     * 根据sourceId获取栏目
     * @param source
     * @return
     */
    public List<CategoryArea> getListBySource(String source);

    /**
     * 递归删除子分栏
     * @param cateId
     * @param sourceId
     */
    public void deleteSubArea(String cateId,String sourceId);

    public void updateSubArea(String cateId,CategoryArea area);

    /**
     * 栏目向上移动
     * @param  map
     * @return
     */
    public CategoryArea getAreaUpward(Map map);

    /**
     * 栏目查询当前数据
     * @param  map
     * @return
     */
    public CategoryArea getsysAreaupordow(Map map);

    /**
     * 栏目修改参数
     * @param Group
     */
    public void updateAreaSort(CategoryArea Group);

    /**
     * 栏目向下移动
     * @param  map
     * @return
     */
    public CategoryArea getAreaDownward(Map map);

    /**
     * 栏目最上
     * @param map
     * @return
     */
    public List<CategoryArea> getAreaListUpward(Map map);
    /**
     * 栏目最下
     * @param map
     * @return
     */
    public List<CategoryArea> getAreaListDownward(Map map);

    public List<CategoryArea> getListCateAreaByName(String name,String cateId,List<CategoryArea> areas);

    public List<CategoryArea> getListCateAreaByNameAndCateId(String name,String cateId);

    /**
     * 递归新增或更新子孙分类栏目
     * @param cateId
     * @param area
     */
    public void addOrUpdateSubArea(int cateId,CategoryArea area,String isSubForm);

    public void updateSubAreaByName(String cateId,CategoryArea area);

    /**
     * 5/10
     * @param map
     * @return
     */
    public int getsorttabcount(HashMap map);
    public List<Map<String, Object>> queryJsonsorttabList(HashMap map);
    public int getsorttabCopycount(HashMap map);
    public List<Map<String, Object>> queryJsonsorttabCopyList(HashMap map);
    public List<Map<String, Object>> queryJsonsorttabCopyCount(HashMap map);
    public void updataFlag(Map map);
    public void updataFlagIsnull(String categoryid);
    public int insertsortcopy(HashMap map);
    public void updatesort(HashMap map);
    public void deletesortcopy(HashMap map);
    /**
     * 向上移动
     * @param map
     * @return
     */
    public Map<String, Object> getUpward(Map map);
    /**
     * 查询当前数据
     * @param map
     * @return
     */
    public  Map<String, Object> gettabupordow(Map map);
    /**
     * 向下移动
     * @param map
     * @return
     */
    public Map<String, Object> getDownward(Map map);

    /**
     * 最上
     * @param map
     * @return
     */
    public List<Map<String, Object>> getTabListUpward(Map map);
    /**
     * 最下
     * @param map
     * @return
     */
    public List<Map<String, Object>> getTabListDownward(Map map);
    public void updatesortcopy(HashMap map);
    public int insertsort(HashMap map);
    public void deletesort(HashMap map);
    public java.util.List<CategoryArea> getListCateAreaByCateIdBYid(int cateId);
    public List<CategoryArea> getListCateAreaByCateIdSub(int cateId);
    public List<CategoryArea> getListByCateIdSub(Map map);
}

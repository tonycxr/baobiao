package com.sungcor.baobiao.dao;

import com.sungcor.baobiao.entity.CategoryArea;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public interface ICategoryAreaDao {
    public java.util.List<CategoryArea> getListCateAreaByCateId(int cateId);

    public int insert(CategoryArea area);

    public void delete(int areaId);

    public java.util.List<CategoryArea> getAreaById(int areaId);

    public void update(CategoryArea area);

    public List<CategoryArea> getListBySource(int source);

    public void deleteFromCateIdAndSource(Map map);

    public List<CategoryArea> getAreaByCateIdAndSource(Map map);

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

    public List<CategoryArea> getListCateAreaByName(Map map);
    public  List<HashMap> getsorttabcount(HashMap map);
    public List<Map<String, Object>> queryJsonsorttabList(HashMap map);
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
}

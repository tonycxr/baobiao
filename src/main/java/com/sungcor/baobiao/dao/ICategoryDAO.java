package com.sungcor.baobiao.dao;

import com.sungcor.baobiao.entity.Category;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public interface ICategoryDAO {
    public java.util.List<Category> getCategoryListByParentID(int id);

    public java.util.List<Category> listAllCategory();

    public java.util.List<Category>  getCategoryById(int id);

    public void insertCategory(Category cat);

    public void deleteCategoryByID(int id);

    public int updateCategoryByID(Category cat);

    public Map getCategoryCountByParentID(Map map);

    public java.util.List<Category>  getChildrenCategorys(Map map);

    public Map getMaxCategorySortByParentID(Map map);

    public void updateSortBefore1(Map map);

    public void updateSortBefore2(Map map);

    public void updateSortAfter1(Map map);

    public void updateSortAfter2(Map map) ;

    public void startOrStopCategoryByID(Map map);

    public void updateSortBeforeAntoherCat(Map map) ;

    public void updateSortAfterAntoherCat(Map map) ;

    public List<HashMap> getRelatedCats(Integer[] item);

    public List<HashMap> getRelatedChildrenCats(Integer[] item);

    public List<Category> SelectType();

    public List<Category> findStartChildrenCategory(int cateId);

    public List<Category> findStopParentCategory(int cateId);

    public List<Category> getCategoryByName(String name);

    public List<Category> getCategoryListByParentIDAndLockFlag(HashMap parm);

    public List<Category> listAllCategoryByLockFlag(HashMap parm);

    //字段管理查询引用次数
    public List<Category> countFieldGroupNum(Map map);

    //获取人员下所有数据域包含的分类
    public List<Category> getAllRegionCategory(HashMap map);

    public java.util.List<Category> getCategoryListByParentIDNoCI(int id);
}

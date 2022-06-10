package com.sungcor.baobiao.service;

import com.sungcor.baobiao.entity.Category;
import com.sungcor.baobiao.entity.CategoryArea;
import com.sungcor.baobiao.entity.UserInfoBean;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public interface ICategoryService {
    /**
     * 加载分类树数据
     */
    public List<Category> initCategoryTreeDate();

    /**
     * 加载简单分类数据
     * @return
     */
    public List<Category> initCategoryTreeSimpleDate();

    /**
     * 加载分类树数据
     * @param operUser
     */
    public   List<Category> initCategoryTreeDate(UserInfoBean operUser);

    /**
     * 查询子分类数量
     * @param map
     * @return
     */
    public int getChildrenCategoryCount(Map map);

    /**
     * 查询子分类列表
     * @param map
     * @return
     */
    public List<Category> listChildrenCategorys(Map map);

    /**
     * 调整分类树位置
     * @param nodeID
     * @param tragetNodeID
     * @param parentNode
     * @param tragetNodeID
     * @param moveType "inner"：成为目标节点子节点，"prev"：成为目标节点前一个节点，"next"：成为目标节点后一个节点
     */
    public boolean updatePositionCategory(String nodeID,String tragetNodeID,String parentNode,String targetParentNode,String nodeSort ,String targetNodeSort,String moveType);

    /**
     * 新增分类
     * @param cat
     * @return
     */
    public void insertCategory(Category  cat);

    /**
     * 编辑分类
     * @param cat
     * @return
     */
    public void updateCategory(Category  cat);

    /**
     * 根据ID查询分类
     * @param id
     * @return
     */
    public Category getCategoryByID(Integer id);

    /**
     * 分类删除验证
     * @param ids
     * @return
     */
    public java.util.Map checkDelete(String ids);

    /**
     * 删除分类
     * @param id
     */
    public void deleteCategoryByID(int id);

    public java.util.List<Category> getCategoryListByParentID(int id);

    public void startOrStopCategoryByID(int id,String flag);

    public void startOrStopSubCategoryByID(int id,String flag);


    public java.util.List<Category> listAllCategory();

    public List<Category> getCategoryTreebyID(int id);

    //find all children category
    public String findChildrenCategory(String catFid);

    public List<HashMap> getRelatedCats(Integer[] item);

    public List<HashMap> getRelatedChildrenCats(Integer[] item);

    public List<Category> SelectType();//时间报表时间分类查询

    /**
     * 递归为子孙分类添加栏目
     * @param cateId
     * @param area
     */
    public void addSubArea(int cateId, CategoryArea area, String isSubForm);

    public List<Category> findStartChildrenCategory(int cateId);

    public List<Category> findStopParentCategory(int cateId);

    public Category getCategoryByName(String name);

    public List<Category> getCategoryTreebyIDAndLockFlag(HashMap parm);

    public List<Category> initCategoryTreeSimpleDateByLockFlag(HashMap parm);

    /**
     * 加载资产分类树数据
     */
    public List<Category> initCMDBCategoryTreeDate(HttpServletRequest request);

    public List<Category> countFieldGroupNum(HashMap map);

    public List<Category> listTreeByCategory(HashMap map);

    /**
     * 加载分类树数据
     */
    public List<Category> initCategoryTreeNoCIDate();
}

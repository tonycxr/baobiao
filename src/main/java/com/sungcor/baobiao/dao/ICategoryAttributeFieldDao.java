package com.sungcor.baobiao.dao;

import com.sungcor.baobiao.entity.CISubDataKey;
import com.sungcor.baobiao.entity.CategoryAttributeField;
import com.sungcor.baobiao.entity.SysField;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public interface ICategoryAttributeFieldDao {
    public List<CategoryAttributeField> getAttrListByAreaId(int areaId);

    public void insert(CategoryAttributeField field);

    public void delete(CategoryAttributeField field);

    /**
     * 获取子属性
     * @param fieldID
     * @return
     */
    public java.util.List<CategoryAttributeField> getSubField(Integer fieldID);

    public List<CategoryAttributeField> getById(int fieldID);

    public void update(CategoryAttributeField field);

    public List<CategoryAttributeField> listParentFieldList(int areaId);

    /**
     * 获取同一来源属性
     * @param source
     */
    public void deleteBySource(int source);

    public void deleteFromAreaIdAndSource(Map map);

    public List<CategoryAttributeField> getByAreaIdAndSource(Map map);

    /**
     * 查询字段集合，分页方法
     * @return
     */
    public List<HashMap> getSortCount(Map id);

    /**
     * 查询字段集合，分页方法
     * @param object
     * @return
     */
    public  List<SysField> getsortList(Object object);

    /**
     * 向上移动
     * @param  map
     * @return
     */
    public CategoryAttributeField getUpward(Map map);

    /**
     * 查询当前数据
     * @param  map
     * @return
     */
    public CategoryAttributeField getsysupordow(Map map);

    /**
     * 修改参数
     * @param Group
     */
    public void updateSort(CategoryAttributeField Group);

    /**
     * 向下移动
     * @param  map
     * @return
     */
    public CategoryAttributeField getDownward(Map map);

    /**
     * 最上
     * @param map
     * @return
     */
    public List<CategoryAttributeField> getListUpward(Map map);
    /**
     * 最下
     * @param map
     * @return
     */
    public List<CategoryAttributeField> getListDownward(Map map);

    public List<CategoryAttributeField> getListByNameAndAreaId(Map map);

    public List<CategoryAttributeField> queryFormFields(HashMap map);

    public CategoryAttributeField getAttByNameAndAreaId(Map map);

    public CategoryAttributeField queryParentField(HashMap map);

    public List<HashMap> querySysSubCount(HashMap map);

    public List<CategoryAttributeField> querySysSubByCMDB(HashMap map);
    public List<CategoryAttributeField> getAttrListByAreaIdSearch(int areaId);
    public  List<CategoryAttributeField> getListByAreaAndAreaId(Map map);
    public  List<CategoryAttributeField> getListByAreaList(Map map);
    public  List<CISubDataKey> getListBySubCiid(Map map);
    public void updateSubNOTAudit(Map map);
    public  List<CategoryAttributeField>  getListByCateIdSub(Map map);

    public void insertSub(Map map);
    public void insertSubfield(Map map);
    public void insertSubNOTAudit(Map map);
    public void insertSubNOTAuditfield(Map map);
    public void  deleteSub(Map map);
    public void  deleteSubFile(Map map);

    public  List<HashMap>  getCount(HashMap map);
    public List<HashMap<String, Object>> getList(HashMap map);
    public List<HashMap<String, Object>> getListByID(HashMap map);
    public void deleteSubField(Map map);
}

package com.sungcor.baobiao.service;

import com.sungcor.baobiao.entity.CISubDataKey;
import com.sungcor.baobiao.entity.CategoryAttributeField;
import com.sungcor.baobiao.entity.SysField;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public interface ICategoryAttributeFieldService {
    /**
     * 根据栏目ID获取字段
     * @param areaId
     * @return
     */
    public List<CategoryAttributeField> getAttrListByAreaId(int areaId);

    /**
     * 新增字段
     * @param field
     */
    public void insert(CategoryAttributeField field);

    /**
     * 删除字段
     * @param field
     */
    public void delete(CategoryAttributeField field);

    /**
     * 获取子属性
     * @param fieldID
     * @return
     */
    public java.util.List<CategoryAttributeField> getSubField(Integer fieldID);

    /**
     * 获取字段
     * @param fieldID
     * @return
     */
    public CategoryAttributeField getById(int fieldID);
    public void update(CategoryAttributeField field);
    public List<CategoryAttributeField> listParentFieldList(int areaId);

    /**
     * 删除同一来源的属性
     * @param source
     */
    public void deleteBySource(int source);

    /**
     *  递归删除子孙属性
     * @param cateId
     * @param areaSource
     * @param source
     */
    public void deleteSubAtt(String cateId,int areaSource,String source);

    public void updateSubAtt(String cateId,int areaSource,CategoryAttributeField field);

    public void insertSubAtt(CategoryAttributeField field,int cateId,int areaSource);

    public int getSortCount(Map id);

    /**
     * 查询字段集合，分页方法
     * @param object
     * @return
     */
    public  List<SysField> getsortList(Object object);

    /**
     * 向上移动
     * @param map
     * @return
     */
    public CategoryAttributeField getUpward(Map map);

    /**
     * 查询当前数据
     * @param map
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
     * @param map
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

    public Boolean getIsRename(String name,int areaId);


    public Boolean getSubIsRename(String name,String areaId);

    public void insertOrEditSubAtt(CategoryAttributeField field,int cateId,int areaSource);

    public List<CategoryAttributeField> queryFormFields(HashMap map);

    public List<CategoryAttributeField> getAttrListByAreaIdSearch(int areaId);
    public  List<CategoryAttributeField> getListByAreaAndAreaId(Map map);
    public  List<CategoryAttributeField> getListByAreaList(Map map);
    public  List<CISubDataKey> getListBySubCiid(Map map);

    public void updateSubNOTAudit(Map map);
    public  List<CategoryAttributeField>  getListByCateIdSub(Map map);
    public boolean save(String data,Map map);
    public boolean updata(String data,Map map);

    public void  deleteSub(Map map);

    public int getCount(HashMap map);
    public List<HashMap<String, Object>> getList(HashMap map);
    public List<HashMap<String, Object>> getListByID(HashMap map);
    String getResultJson(HashMap map);
}

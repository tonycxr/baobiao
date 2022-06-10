package com.sungcor.baobiao.mapper;

import com.sungcor.baobiao.entity.FMFormArea;
import com.sungcor.baobiao.entity.FMFormField;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Mapper
@Component
@Repository
public interface FMFormFieldMapper {
    public void  insert(FMFormField field);

    public void  update(FMFormField field);

    public void  delete(FMFormField field);

    public java.util.List<FMFormField> search(Integer areaID);

    public FMFormField getFMFormField(Integer fieldID);

    public java.util.List<FMFormField> getSubField(Integer fieldID);

    public java.util.List<FMFormField> getSubFields(HashMap map);

    public java.util.List<FMFormField> getSysConSubField(HashMap map);

    public java.util.List<FMFormField> getCmdbCiSubField(HashMap map);

    public java.util.List<FMFormField> listParentFieldList(Integer areaID);

    public Integer getMaxFieldSortByAreaID(Integer areaID);

    public void updateSortPre(FMFormField field);

    public void updateSortNext(FMFormField field);

    public void updateAreaSortPre(FMFormArea area);

    public void updateAreaSortNext(FMFormArea area);

    public int checkFieldUnique(Map param);

    public int getVersionIDMapFieldID(int versionID);

    public  void updateVersionIDByParentID(HashMap param);

    public List<FMFormField> queryFormFields(HashMap map);
    /**
     * 查询字段集合，分页方法
     *
     * @param object
     * @return
     */
    public List<FMFormField> getsortList(Object object);
    /**
     * 查询字段集合，分页方法
     *
     * @return
     */
    public List<HashMap> getSortCount(Map id);
    /**
     * 向上移动
     *
     * @param map
     * @return
     */
    public FMFormField getUpward(Map map);

    /**
     * 向下移动
     *
     * @param map
     * @return
     */
    public FMFormField getDownward(Map map);

    /**
     * 最上
     *
     * @param map
     * @return
     */
    public List<FMFormField> getListUpward(Map map);

    /**
     * 最下
     *
     * @param map
     * @return
     */
    public List<FMFormField> getListDownward(Map map);

    /**
     * 查询当前数据
     *
     * @param map
     * @return
     */
    public FMFormField getsysupordow(Map map);

    /**
     * 修改参数
     *
     * @param Group
     */
    public void updateSort(FMFormField Group);
    /**
     * 修改参数
     * @param Group
     */
    public void updateSortTemp(FMFormField Group);

    /**
     * 栏目向上移动
     *
     * @param map
     * @return
     */
    public FMFormArea getAreaUpward(Map map);

    /**
     * 栏目向下移动
     *
     * @param map
     * @return
     */
    public FMFormArea getAreaDownward(Map map);

    /**
     * 栏目最上
     *
     * @param map
     * @return
     */
    public List<FMFormArea> getAreaListUpward(Map map);

    /**
     * 栏目最下
     *
     * @param map
     * @return
     */
    public List<FMFormArea> getAreaListDownward(Map map);

    /**
     * 栏目查询当前数据
     *
     * @param map
     * @return
     */
    public FMFormArea getsysAreaupordow(Map map);
    /**
     * 栏目修改参数
     * @param Group
     */
    public void updateAreaSort(FMFormArea Group);
}

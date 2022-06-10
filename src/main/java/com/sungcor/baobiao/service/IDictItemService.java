/**
 * 
 */
package com.sungcor.baobiao.service;


import com.sungcor.baobiao.entity.DictItem;

import java.util.List;

/**
 * @author 袁啸川
 * 2012-4-20
 * 字典项业务层接口
 */
public interface IDictItemService {
	/**
     * 添加字典项
     * @param dictItem 字典项对象
     */
    public void insertDictItem(DictItem dictItem);

    /**
     * 根据主键ID查询字典字典项对对象
     * @param id 主键ID
     * @return  字典项对对象
     */
    public DictItem getDictItemById(int id);

    /**
     * 根据主键ID删除字典项
     * @param id  主键ID
     */
    public void deleteDictItemById(int id);

    /**
     * 修改字典项
     * @param dictItem 字典项
     */
    public void updateDictItem(DictItem dictItem);

    /**
     * 根据字典分类ID得到字典项的数量
     * @param id 字典分类ID
     * @return  某字典分类的字典项数量
     */
	public int getDictsItemCount(int id);

    /**
     * 得到字典项集合，分页
     * @param object  参数MAP对象
     * @return 字典项集合
     */
    public List<DictItem> getDictItemsPaging(Object object);
    /**
     * data.clist
     * @param object
     * @return
     */
    public List<DictItem> getDictItems(Object object);
    /**
     * 检查编号是否存在
     * @param code
     * @return
     */
    public boolean checkCode(String code);

    public boolean checkName(Object object);

    public List<DictItem> getDictItemByDictCode(String code);
}

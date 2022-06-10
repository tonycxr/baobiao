package com.sungcor.baobiao.mapper;

import com.sungcor.baobiao.entity.DictItem;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;

@Mapper
@Component
@Repository
public interface DictItemMapper {
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
     * 根据字典分类ID删除所属的字典项
     * @param dictId
     */
    public void deleteDictItemByDictId(int id);

    /**
     * 修改字典项
     * @param dict 字典项
     */
    public void updateDictItem(DictItem dictItem);

    /**
     * 根据字典分类ID得到字典项的数量
     * @param id 字典分类ID
     * @return  某字典分类的字典项数量
     */
    @SuppressWarnings("unchecked")
    public List<HashMap> getDictItemsCount(int dictId);

    /**
     * 得到字典项集合，分页
     * @param object  参数MAP对象
     * @return 字典项集合
     */
    public List<DictItem> getDictItemsPaging(Object object);
    /**
     * 根据字典分类ID得到字典项集合，不分页
     * @param object  参数MAP对象
     * @return 字典项集合
     */
    public List<DictItem> getDictItems(Object object);
    /**
     * 根据编号获取字典项目
     * @param code 编号
     * @return
     */
    public DictItem getDictItemByCode(String code);

    public DictItem getDictItemByName(Object object);

    public List<DictItem> getDictItemByDictCode(String code);
}

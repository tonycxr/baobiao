package com.sungcor.baobiao.service.Impl;

import com.sungcor.baobiao.STSMConstant;
import com.sungcor.baobiao.dao.ICategoryAreaDao;
import com.sungcor.baobiao.dao.ICategoryDAO;
import com.sungcor.baobiao.entity.Category;
import com.sungcor.baobiao.entity.CategoryArea;
import com.sungcor.baobiao.entity.CategoryAttributeField;
import com.sungcor.baobiao.service.ICategoryAreaService;
import com.sungcor.baobiao.service.ICategoryAttributeFieldService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Transactional
@Service(STSMConstant.SPRING_BEAN_NAME_CMDB_AREA_SERVICE)
public class CategoryAreaServiceImpl implements ICategoryAreaService {

    private ICategoryAreaDao categoryAreaDao;

    private ICategoryAttributeFieldService categoryAttributeFieldService;

    private ICategoryDAO categoryDao;

    /**
     * 根据分类ID获取该分类下所有栏目
     * @param cateId
     * @return
     */
    public List<CategoryArea> getListCateAreaByCateId(int cateId){
        List<CategoryArea> categoryAreas=categoryAreaDao.getListCateAreaByCateId(cateId);
        for (int i=0;i<categoryAreas.size();i++){
            List<CategoryAttributeField> attrs=new ArrayList<CategoryAttributeField>();
            attrs=categoryAttributeFieldService.getAttrListByAreaId(categoryAreas.get(i).getId());
            categoryAreas.get(i).setFieldList(attrs);
        }
        return categoryAreas;
    }

    /**
     * 新增栏目
     * @param area
     * @return
     */
    public int insert(CategoryArea area){
        return  categoryAreaDao.insert(area);
    }

    /**
     * 删除栏目
     * @param areaId
     */
    public void delete(int areaId){
        categoryAreaDao.delete(areaId);
    }

    /**
     * 根据栏目ID获取栏目
     * @param areaId
     * @return
     */
    public CategoryArea getAreaById(int areaId){
        return categoryAreaDao.getAreaById(areaId).get(0);
    }

    /**
     * 更新栏目
     * @param area
     */
    public void update(CategoryArea area){
        categoryAreaDao.update(area);
    }

    /**
     * 获取相同来源的栏目
     * @param source
     * @return
     */
    public List<CategoryArea> getListBySource(String source){
        return categoryAreaDao.getListBySource(Integer.parseInt(source));
    }

    /**
     * 递归删除同一来源的子孙分栏
     * @param cateId
     * @param sourceId
     */
    public void deleteSubArea(String cateId,String sourceId){
        List<Category> childrenList = this.categoryDao.getCategoryListByParentID(Integer.parseInt(cateId));
        for(int i=0;i<childrenList.size();i++){
            Map map = new HashMap();
            map.put("cateId",childrenList.get(i).getId());
            map.put("sourceId",sourceId);
            CategoryArea area=categoryAreaDao.getAreaByCateIdAndSource(map).get(0);
            List<CategoryAttributeField> fields=categoryAttributeFieldService.getAttrListByAreaId(area.getId());
            if(fields.size()<1){
                categoryAreaDao.deleteFromCateIdAndSource(map);
            }
        }
        if (childrenList.size() > 0){
            for (Category childrenOrg : childrenList){
                deleteSubArea(childrenOrg.getId()+"",sourceId);
            }
        }
    }

    /**
     * 递归修改分栏
     * @param cateId
     * @param area
     */
    public void updateSubArea(String cateId,CategoryArea area){
        List<Category> childrenList = this.categoryDao.getCategoryListByParentID(Integer.parseInt(cateId));
        for(int i=0;i<childrenList.size();i++){
            Map map = new HashMap();
            map.put("cateId",childrenList.get(i).getId());
            map.put("sourceId",area.getSource());
            if(categoryAreaDao.getAreaByCateIdAndSource(map).size()>0){
                CategoryArea area_one=categoryAreaDao.getAreaByCateIdAndSource(map).get(0);
                area_one.setTitle(area.getTitle());
                area_one.setColumnCount(area.getColumnCount());
                area_one.setDisplayFlag(area.getDisplayFlag());
                area_one.setTitle(area.getTitle());
                categoryAreaDao.update(area_one);
            }
        }
        if (childrenList.size() > 0){
            for (Category childrenOrg : childrenList){
                updateSubArea(childrenOrg.getId()+"",area);
            }
        }
    }
    @Override
    public CategoryArea getAreaUpward(Map map) {
        return categoryAreaDao.getAreaUpward(map);
    }
    @Override
    public CategoryArea getsysAreaupordow(Map map) {
        return categoryAreaDao.getsysAreaupordow(map);
    }

    @Override
    public void updateAreaSort(CategoryArea Group) {
        categoryAreaDao.updateAreaSort(Group);
    }
    @Override
    public CategoryArea getAreaDownward(Map map) {
        return categoryAreaDao.getAreaDownward(map);
    }
    @Override
    public List<CategoryArea> getAreaListUpward(Map map) {
        return categoryAreaDao.getAreaListUpward(map);
    }
    @Override
    public List<CategoryArea> getAreaListDownward(Map map) {
        return categoryAreaDao.getAreaListDownward(map);
    }

    public List<CategoryArea> getListCateAreaByName(String name,String cateId,List<CategoryArea> areas){
        List<Category> childrenList = this.categoryDao.getCategoryListByParentID(Integer.parseInt(cateId));
        for(int i=0;i<childrenList.size();i++){
            Map map = new HashMap();
            map.put("cateId",childrenList.get(i).getId());
            map.put("name",name);
            areas =categoryAreaDao.getListCateAreaByName(map);
            if(areas.size()>0){
                break;
            }
        }
        if (childrenList.size() > 0&&areas.size()<1){
            for (Category childrenOrg : childrenList){
                getListCateAreaByName(name, childrenOrg.getId()+"",areas);
            }
        }
        return areas;
    }

    public List<CategoryArea> getListCateAreaByNameAndCateId(String name,String cateId){
        Map map = new HashMap();
        map.put("cateId",cateId);
        map.put("name",name);
        return categoryAreaDao.getListCateAreaByName(map);
    }
    /**
     * 递归新增或更新子孙分类栏目
     * @param cateId
     * @param area
     */
    public void addOrUpdateSubArea(int cateId,CategoryArea area,String isSubForm){
        List<Category> childrenList = this.categoryDao.getCategoryListByParentID(cateId);
        for(int i=0;i<childrenList.size();i++){
            List<CategoryArea> the_areas=new ArrayList<CategoryArea>();
            Map map = new HashMap();
            map.put("cateId",childrenList.get(i).getId());
            map.put("name",area.getTitle());
            the_areas =categoryAreaDao.getListCateAreaByName(map);
            if(the_areas.size()>0){
                CategoryArea the_area=the_areas.get(0);
                the_area.setSource(area.getSource());
                the_area.setIsImport(area.getIsImport());
                the_area.setColumnCount(area.getColumnCount());
                the_area.setDisplayFlag(area.getDisplayFlag());
                if("1".equals(isSubForm)) {
                    the_area.setIsSubForm(isSubForm);
                }
                this.update(the_area);
            }else {
                area.setCategoryId(childrenList.get(i).getId()+"");
                if("1".equals(isSubForm)) {
                    area.setIsSubForm(isSubForm);
                }
                this.insert(area);
                area.setSort(area.getId());
                this.update(area);
            }
        }
        if (childrenList.size() > 0){
            for (Category childrenOrg : childrenList){
                addOrUpdateSubArea(childrenOrg.getId(), area,isSubForm);
            }
        }
    }
    public void updateSubAreaByName(String cateId,CategoryArea area){
        List<Category> childrenList = this.categoryDao.getCategoryListByParentID(Integer.parseInt(cateId));
        for(int i=0;i<childrenList.size();i++){
            Map map = new HashMap();
            map.put("cateId",childrenList.get(i).getId());
            map.put("name",area.getTitle());
            List<CategoryArea> areas =categoryAreaDao.getListCateAreaByName(map);
            if(areas.size()>0){
                CategoryArea area_one=areas.get(0);
                area_one.setTitle(area.getTitle());
                area_one.setColumnCount(area.getColumnCount());
                area_one.setDisplayFlag(area.getDisplayFlag());
                area_one.setTitle(area.getTitle());
                area_one.setSource(area.getSource());
                categoryAreaDao.update(area_one);
            }
        }
        if (childrenList.size() > 0){
            for (Category childrenOrg : childrenList){
                updateSubAreaByName(childrenOrg.getId()+"",area);
            }
        }
    }

    @Override
    public int getsorttabcount(HashMap map) {
        int count = 0;
        try{
            count  = Integer.parseInt(categoryAreaDao.getsorttabcount(map).get(0).get("count").toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return count;
    }

    @Override
    public List<Map<String, Object>> queryJsonsorttabList(HashMap map) {
        return categoryAreaDao.queryJsonsorttabList(map);
    }

    @Override
    public int getsorttabCopycount(HashMap map) {
        int count = 0;
        try{
            count  = Integer.parseInt(categoryAreaDao.getsorttabcount(map).get(0).get("count").toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return count;
    }

    @Override
    public List<Map<String, Object>> queryJsonsorttabCopyList(HashMap map) {
        return categoryAreaDao.queryJsonsorttabCopyList(map);
    }

    @Override
    public List<Map<String, Object>> queryJsonsorttabCopyCount(HashMap map) {
        return categoryAreaDao.queryJsonsorttabCopyCount(map);
    }

    @Override
    public void updataFlag(Map map) {
        categoryAreaDao.updataFlag(map);
    }

    @Override
    public void updataFlagIsnull(String categoryid) {
        categoryAreaDao.updataFlagIsnull(categoryid);
    }

    @Override
    public int insertsortcopy(HashMap map) {
        return categoryAreaDao.insertsortcopy(map);
    }

    @Override
    public void updatesort(HashMap map) {
        categoryAreaDao.updatesort(map);
    }

    @Override
    public void deletesortcopy(HashMap map) {
        categoryAreaDao.deletesortcopy(map);
    }

    @Override
    public Map<String, Object> getUpward(Map map) {
        return categoryAreaDao.getUpward(map);
    }

    @Override
    public Map<String, Object> gettabupordow(Map map) {
        return categoryAreaDao.gettabupordow(map);
    }

    @Override
    public Map<String, Object> getDownward(Map map) {
        return categoryAreaDao.getDownward(map);
    }

    @Override
    public List<Map<String, Object>> getTabListUpward(Map map) {
        return categoryAreaDao.getTabListUpward(map);
    }

    @Override
    public List<Map<String, Object>> getTabListDownward(Map map) {
        return categoryAreaDao.getTabListDownward(map);
    }

    @Override
    public void updatesortcopy(HashMap map) {
        categoryAreaDao.updatesortcopy(map);
    }
    @Override
    public int insertsort(HashMap map) {
        return categoryAreaDao.insertsort(map);
    }
    @Override
    public void deletesort(HashMap map) {
        categoryAreaDao.deletesort(map);
    }

    @Override
    public List<CategoryArea> getListCateAreaByCateIdBYid(int cateId) {
        return categoryAreaDao.getListCateAreaByCateIdBYid(cateId);
    }

    @Override
    public List<CategoryArea> getListCateAreaByCateIdSub(int cateId) {
        List<CategoryArea> categoryAreas=categoryAreaDao.getListCateAreaByCateIdBYid(cateId);
        if(categoryAreas.size()>0){
            List<CategoryAttributeField> attrs=  categoryAttributeFieldService.getAttrListByAreaId(categoryAreas.get(0).getId());
            categoryAreas.get(0).setFieldList(attrs);
        }
        return categoryAreas;
    }

    @Override
    public List<CategoryArea> getListByCateIdSub(Map map) {
        List<CategoryArea> categoryAreas=categoryAreaDao.getListCateAreaByCateIdBYid(Integer.valueOf(map.get("areaid").toString()));
        List<CategoryAttributeField>  attrs=categoryAttributeFieldService.getListByCateIdSub(map);
        categoryAreas.get(0).setFieldList(attrs);
        return categoryAreas;
    }
}

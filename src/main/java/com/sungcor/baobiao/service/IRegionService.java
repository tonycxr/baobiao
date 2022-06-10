package com.sungcor.baobiao.service;

import com.sungcor.baobiao.entity.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public interface IRegionService {
    //区域
    public static String REGION_AREA = "region_area";
    //类型
    public static String REGION_CATEGORY = "region_category";
    //CI
    public static String REGION_CI= "region_ci";
    /**
     * 加载数据域树
     */
    public List<Region> initRegionTreeDate();

//    public List<Region> initRegionTreeDate(Integer orgid);

    public List<Region> initRegionTreeDate(ServiceProduct serviceProduct);

    /**
     * 查询子数据域数量
     * @param map
     * @return
     */
    public int getChildrenRegionCount(Map map);
    /**
     * 根据编号查询数据域数量
     * @param map
     * @return
     */
    public int getRegionCountByCode(Map map);

    /**
     * 根据名称查询数据域数量
     * @param map
     * @return
     */
    public int getRegionCountByName(Map map);

//    public int getRegionUpdateCountByName(Map map);

    /**
     * 查询子数据域列表(带分页)
     * @param map
     * @return
     */
    public List<Region> listChildrenRegions(Map map);

    /**
     * 调整数据域树位置
     * @param nodeID
     * @param tragetNodeID
     * @param parentNode
     * @param tragetNodeID
     * @param moveType "inner"：成为子节点，"prev"：成为同级前一个节点，"next"：成为同级后一个节点
     */
    public boolean updatePositionRegion(String nodeID, String tragetNodeID, String parentNode, String targetParentNode, String nodeSort, String targetNodeSort, String moveType);

    /**
     * 新增数据域
     * @param region
     * @return
     */
    public int insertRegion(Region region);

    /**
     * 编辑数据域
     * @param region
     * @return
     */
    public void updateRegion(Region region);

    /**
     * 根据ID查询数据域
     * @param id
     * @return
     */
    public Region getRegionByID(Integer id);

    /**
     * 数据域删除验证
     * @param ids
     * @return
     */
    public Map checkDelete(String ids);

    /**
     * 删除数据域
     * @param id
     */
    public void deleteRegionByID(int id);

    /**
     * 加载简单数据域数据
     * @return
     */
    public List<Region> initRegionTreeSimpleDate();

    public void insertOrganise(Map map);
    public void insertArea(Map map);
    public void insertUserRegion(Map map);
    public void saveUserRegion(HashMap map);

    public List<HashMap> getOrganise(String regionId);
    public List<HashMap> getAreaChecked(String regionId);
    public List<HashMap> getUserRegion(String userid);

    public List<Integer> getAllRegionId(UserInfoBean operUser);  //获取所有数据域的子类id

    /**
     * 获取服务台分组
     */
    public List<RegionItem> getRegionItems(Object object);

    public List<Region> getRegionList(Object object);

    public Region getRegionByCode(String code);

    public void deleteUserForRegion(HashMap map);

    //获取人员所含数据域中所有区域
    public List<Area> getAllRegionArea(HashMap map);

    public void insertQuickCreateDefault(HashMap map);

    public List<HashMap> getQuickCreateDefault(int userId);

    public void updateQuickCreateDefault(HashMap map);

    public void insertCategory(Map map);

    public List<HashMap> getCategoryChecked(String regionId);
    //获取人员所含数据域中所有资产类型
    public List<Category> getAllRegionCategory(HashMap map);
    //获取人员所含数据域中所有CI
    public List<CIBaseInfo> getAllRegionCI(HashMap map);
    //通过用户ID获取该登录用户的所有数据域下的关于CI的权限
    public HashMap<String,String> getAllRegionByUserId(HashMap map);
}

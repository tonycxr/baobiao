/**
 *
 */
package com.sungcor.baobiao.service;


import com.sungcor.baobiao.entity.ServiceProduct;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author 袁啸川
 * 2012-5-7
 * 服务产品业务层接口
 */

public interface IServiceProductService {

    public   List<ServiceProduct> getServiceProduct();
    /**
     * 得到服务产品的数量
     * @return  服务产品的数量
     */
    public int getServiceProductCount(Object object);

    /**
     * 得到服务产品集合，分页
     * @param object  参数MAP对象
     * @return 服务产品集合
     */
    public List<ServiceProduct> getServiceProductPaging(Object object);
    /**
     * 得到服务产品集合，不分页
     * @param object  参数MAP对象:
     *  list组织ID集合（当前组织和以下的组织的ID集合）；
     *  productGroupId   分组ID；
     *  serviceCategoryId 分类ID；
     *
     * @return 服务产品集合
     */
    public List<ServiceProduct> getServiceProducts(Object object);
    /**
     * 添加服务产品
     * @param serviceProduct 服务产品对象
     */
    public void insertServiceProduct(ServiceProduct serviceProduct);

    /**
     * 修改服务产品
     * @param serviceProduct 服务产品对象
     */
    public void updateServiceProduct(ServiceProduct serviceProduct);
    /**
     * 通过ID获取服务产品对象
     * @param id
     * @return
     */
    public ServiceProduct getServiceProductById(int id);
    /**
     * 手机端获取服务产品对象
     * @param map
     * @return
     */
    public ServiceProduct getServiceProductByMob(HashMap map);
    /**
     * 通过编号获取服务产品对象
     * @param code
     * @return
     */
    public ServiceProduct getServiceProductByCode(String code);
//    /**
//     * 授权
//     * @param spaList
//     */
//    public void license(List<ServiceProductRegion> spaList,int spId);
//    /**
//     * 根据产品ID查询授权集合
//     * @param id 产品ID
//     * @return
//     */
//    public List<ServiceProductRegion> getServiceProductRegionsBySPID(int id);
    /**
     /**
     * 删除产品
     * @param ids
     */
    public String deleteServiceProduct(String ids);

    /**
     * 保存服务产品与服务流程关系
     * @param productId
     * @param processInstanceId
     */

    public Integer addProductProcessInstance(Integer productId, Integer processInstanceId);

//    public ServiceProductProcessInstance getSPPIByPIID(Integer pid);
    /**
     * 验证名称
     * @param name
     * @return
     */
    public boolean checkName(String name);

    /**
     * @param id
     * @return
     */
    public List<ServiceProduct> getServiceProductByFlowId(int id);

    /**
     * 通过查询条件获取得到区域授权的服务产品
     * @param object   查询条件
     * @return
     */
    public List<ServiceProduct> queryAuthServiceProducts(Map object);

//    /**
//     * 根据产品id得到version id
//     * @param id
//     * @return
//     */
//    public List<CodeItem> getServiceVersion(Integer id);

    /**
     * 获取可用的服务产品创建数量
     * @param serviceCategoryId   服务产品类型id
     * @return  >=0
     */
    public int  availableCountOfProduct(Integer serviceCategoryId);

    public HashMap<String, String> getProductNumbersOnCategory();

    public List<ServiceProduct> getProductByRegion(HashMap map);
}

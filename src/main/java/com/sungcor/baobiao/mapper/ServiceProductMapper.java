package com.sungcor.baobiao.mapper;

import com.sungcor.baobiao.entity.ServiceProduct;
import com.sungcor.baobiao.entity.ServiceProductProcessInstance;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Mapper
@Component
@Repository
public interface ServiceProductMapper {
    /**
     * 得到服务产品的数量
     * @return  服务产品的数量
     */
    @SuppressWarnings("unchecked")
    public List<HashMap> getServiceProductCount(Object object);

    /**
     * 得到服务产品集合，分页
     * @param object  参数MAP对象
     * @return 服务产品集合
     */
    public List<ServiceProduct> getServiceProductPaging(Object object);

    /**
     * 得到服务产品集合，不分页
     * @param object  参数MAP对象：
     *  list组织ID集合（当前组织的以下的组织的ID集合）；
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
     * 通过编号获取服务产品对象
     * @param
     * @return
     */
    public ServiceProduct getServiceProductByCode(String code);
    /**
     * 通过名称获取服务产品对象
     * @param
     * @return
     */
    public ServiceProduct getServiceProductByName(String name);
    /**
     * 根据产品ID删除 授权
     * @param id
     */
    public void deleteServiceProductAuthBySPID(int id);
    /**
     * 根据产品ID删除 授权
     * @param id
     */
    public void deleteServiceProductRegionBySPID(int id);
    /**
     * 添加授权
     * @param spa
     */
//    public void insertServiceProductAuth(ServiceProductAuth spa);
    /**
     * 添加授权
     * @param spa
     */
//    public void insertServiceProductRegion(ServiceProductRegion spa);
    /**
     * 根据产品ID查询授权集合
     * @param id 产品ID
     * @return
     */
//    public List<ServiceProductRegion> getServiceProductRegionsBySPID(int id);
    /**
     * 添加服务产品流程关系
     * @param sppi
     */
//    public void insertServiceProductProcessInstance(ServiceProductProcessInstance sppi);
    /**
     * 根据产品ID删除流程关系
     * @param id 产品ID
     */
    public void deleteProductProcessInstanceByProductId(int id);
    /**
     * 修改产品和流程的关系
     * @param ppi
     */
//    public void updateProductProcessInstance(ServiceProductProcessInstance ppi);
    /**
     * 检查产品关联，关联表为：产品流程关系 以及故障策略
     * @param productId
     * @return
     */
    @SuppressWarnings("unchecked")
    public List<HashMap> checkRelated(int productId);
    /**
     * 删除产品
     * @param id
     */
    public void deleteServiceProduct(int id);

    /**
     * 根据服务产品id或流程实例ID查询关联对象
     * @param
     * @return
     */
    public ServiceProductProcessInstance getServiceProduceProcessInstance(Map para);

    public List<ServiceProduct> getServiceProductByFlowId(int id);

    /**
     * 通过查询条件获取得到区域授权的服务产品
     * @param object   查询条件
     * @return
     */
    public List<ServiceProduct> queryAuthServiceProducts(Map object);

    /**
     * 根据产品ID和组织id删除 授权
     * @param para
     */
    public void deleteServiceProductAuthByPIDAndOrgId(Map para);

    public   List<ServiceProduct> getServiceProduct();

//    public List<CodeItem> getServiceVersion(Integer id);

    public List<HashMap<String,String>> getProductNumbersOnCategory();

    public List<ServiceProduct> getProductByRegion(HashMap map);

    public List<ServiceProduct> getServiceProductByMob(HashMap map);
}

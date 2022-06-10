/**
 *
 */
package com.sungcor.baobiao.service.Impl;

import com.sungcor.baobiao.entity.ServiceProduct;
import com.sungcor.baobiao.mapper.ServiceProductMapper;
import com.sungcor.baobiao.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author 袁啸川
 * 2012-5-7
 * 服务产品业务实现类
 */

@Service
public class ServiceProductServiceImpl implements IServiceProductService {

    @Autowired
    private ServiceProductMapper serviceProductMapper;

    @Autowired
    private IServiceCategoryService serviceCategoryService;

    private IDictItemService dictItemService;

    @Autowired
    private IOrganiseService orgService;

    private UserService userService;

    @Override
    public List<ServiceProduct> getServiceProduct() {
        return      serviceProductMapper.getServiceProduct();
    }

    @Override
    public int getServiceProductCount(Object object) {
        int count = Integer.parseInt(serviceProductMapper.getServiceProductCount(object).get(0)
                .get("serviceProductCount").toString());
        return count;
    }

    @Override
    public List<ServiceProduct> getServiceProductPaging(Object object) {
        List<ServiceProduct> list = serviceProductMapper.getServiceProductPaging(object);
        String categoryName = "";
        String groupName = "";
        String organization = "";
        for(ServiceProduct sp : list){
            categoryName  =  serviceCategoryService.getCategoryById(sp.getServiceCategoryId()).getName();
            sp.setCategoryName(categoryName);
//            groupName = dictItemService.getDictItemById(sp.getProductGroupId()).getName();
//            sp.setGroupName(groupName);
            organization = orgService.getOrganiseByID(sp.getOrganizationId()).getName();
            sp.setOrganization(organization);
        }
        return list;
    }

    @Override
    public List<ServiceProduct> getServiceProducts(Object object) {
        return serviceProductMapper.getServiceProducts(object);
    }

    @Override
    public void insertServiceProduct(ServiceProduct serviceProduct) {

    }

    @Override
    public void updateServiceProduct(ServiceProduct serviceProduct) {

    }

    @Override
    public ServiceProduct getServiceProductById(int id) {
        return serviceProductMapper.getServiceProductById(id);
    }

    @Override
    public ServiceProduct getServiceProductByCode(String code) {
        return serviceProductMapper.getServiceProductByCode(code);
    }

    @Override
    public String deleteServiceProduct(String ids) {
        return null;
    }

    @Override
    public Integer addProductProcessInstance(Integer productId, Integer processInstanceId) {
        return null;
    }

//    @Override
//    public ServiceProduct getServiceProductById(int id) {
//        IMemcache<ServiceProduct> serviceProductCache = MemCacheFactory.getRemoteMemCache(ServiceProduct.class);
//        try{
//
//            ServiceProduct sp = serviceProductCache.get("ServiceProduct_"+id);
//            if(null != sp){
//                return sp;
//            }
//        }catch (Exception e){
//            e.printStackTrace();
//        }
//        ServiceProduct serviceProduct = serviceProductMapper.getServiceProductById(id);
//        serviceProductCache.set("ServiceProduct_" + id, serviceProduct);
//        return serviceProduct;
//    }

    public ServiceProduct getServiceProductByMob(HashMap map){
        List<ServiceProduct> list=serviceProductMapper.getServiceProductByMob(map);
        ServiceProduct serviceProduct=null;
        if(list!=null&&list.size()>0){
            serviceProduct=list.get(0);
        }
        return serviceProduct;
    }

//    @Override
//    public void insertServiceProduct(ServiceProduct serviceProduct) {
//        //增加产品
//        serviceProductMapper.insertServiceProduct(serviceProduct);
//        try{
//            IMemcache<ServiceProduct> serviceProductCache = MemCacheFactory.getRemoteMemCache(ServiceProduct.class);
//            if(null != serviceProduct.getId()){
//                serviceProductCache.set("ServiceProduct_" + serviceProduct.getId().toString(), serviceProduct);
//            }
//            //修改服务登记的缓存
//            updateCache();
//        }catch (Exception e){
//            e.printStackTrace();
//        }
//    }

//    @Override
//    public void updateServiceProduct(ServiceProduct serviceProduct) {
//        //修改产品
//        serviceProductMapper.updateServiceProduct(serviceProduct);
//        try{
//            IMemcache<ServiceProduct> serviceProductCache = MemCacheFactory.getRemoteMemCache(ServiceProduct.class);
//            if(null == serviceProductCache.get("ServiceProduct_" + serviceProduct.getId())){
//                serviceProductCache.set("ServiceProduct_" + serviceProduct.getId().toString(), getServiceProductById(serviceProduct.getId()));
//            }else {
//                serviceProductCache.delete("ServiceProduct_" + serviceProduct.getId().toString());
//                serviceProductCache.set("ServiceProduct_" + serviceProduct.getId().toString(), getServiceProductById(serviceProduct.getId()));
//            }
//            //修改服务登记的缓存
//            updateCache();
//        }catch (Exception e){
//            e.printStackTrace();
//        }
//    }

//    private void updateCache(){
//        List<Organise> list = orgService.getAll();
//        for(int i=0;i<list.size();i++){
//            IMemcache<DictItem> cacheL= MemCacheFactory.getRemoteMemCache(DictItem.class);
//            if(cacheL.getCollection("serviceSignProduct_"+list.get(i).getId())!=null){
//                cacheL.delete("serviceSignProduct_"+list.get(i).getId());
//            }
//        }
//    }

//    private void updateCache(){
////        List<Organise> list = orgService.getAll();
//        List<UserInfoBean> list=userService.getAllUserBean();
//        for(int i=0;i<list.size();i++){
//            IMemcache<RegionItem> cacheL= MemCacheFactory.getRemoteMemCache(RegionItem.class);
//            if(cacheL.getCollection("serviceSignProduct_"+list.get(i).getUserId())!=null){
////                cacheL.delete("serviceSignProduct_"+list.get(i).getUserId());
//                cacheL.setCollection("serviceSignProduct_"+list.get(i).getUserId(),null);
//            }
//        }
//    }

//    @Override
//    public void license(List<ServiceProductRegion> spaList,int spId) {
//        serviceProductMapper.deleteServiceProductRegionBySPID(spId);
//        for(ServiceProductRegion spa : spaList){
//            serviceProductMapper.insertServiceProductRegion(spa);
//        }
//        updateCache();
//    }
//
//    @Override
//    public List<ServiceProductRegion> getServiceProductRegionsBySPID(int id) {
//        return serviceProductMapper.getServiceProductRegionsBySPID(id);
//    }

    @SuppressWarnings("unchecked")
    private String checkRelated(int productId) {
        List<HashMap> mapList  = serviceProductMapper.checkRelated(productId);
        if(mapList.size() == 0){
            return null;
        }
        return mapList.get(0).get("name").toString();
    }
//
//    @Override
//    public String deleteServiceProduct(String ids) {
//        String ida[]  = ids.split(",");
//        int ida1[] = new int[ida.length];
//        for(int i=0;i<ida.length;i++){
//            ida1[i] =  Integer.parseInt(ida[i]);
//        }
//        boolean  flag = true;
//        StringBuffer sb = new StringBuffer();
//        for(int i=0;i<ida1.length;i++){
//            String name = checkRelated(ida1[i]);
//            if(null != name){
//                flag = false;
//                sb.append(name);
//                sb.append(",");
//            }
//        }
//        IMemcache<ServiceProduct> serviceProductCache = null;
//        try{
//            serviceProductCache =  MemCacheFactory.getRemoteMemCache(ServiceProduct.class);
//        }catch (Exception e){
//            e.printStackTrace();
//        }
//        if(flag){
//            for(int i=0;i<ida1.length;i++){
//                serviceProductMapper.deleteServiceProduct(ida1[i]);
//                if(null != serviceProductCache){
//                    serviceProductCache.delete("ServiceProduct_"+ida1[i]);
//                }
//                //修改服务登记的缓存
//                updateCache();
//                serviceProductMapper.deleteServiceProductAuthBySPID(ida1[i]);
//            }
//            return null;
//        }else{
//            String result = sb.toString().substring(0,sb.toString().lastIndexOf(","));
//            return result;
//        }
//    }

//    public Integer addProductProcessInstance(Integer productId, Integer processInstanceId){
//        //增加产品和流程实例关系
//        ServiceProductProcessInstance sppi = new ServiceProductProcessInstance();
//        sppi.setProcessInstanceId(processInstanceId);
//        sppi.setServiceProductId(productId);
//        serviceProductMapper.insertServiceProductProcessInstance(sppi);
//        return sppi.getId();
//    }

//    @SuppressWarnings("unchecked")
//    public ServiceProductProcessInstance getSPPIByPIID(Integer pid){
//        Map para = new HashMap();
//        para.put("pid",pid);
//        return  serviceProductMapper.getServiceProduceProcessInstance(para);
//    }

    @Override
    public boolean checkName(String name) {
        ServiceProduct  sp = serviceProductMapper.getServiceProductByName(name);
        if(sp == null){
            return true;
        }
        return false;
    }
    @Override
    public List<ServiceProduct> getServiceProductByFlowId(int id){
        return  serviceProductMapper.getServiceProductByFlowId(id);
    }

    @Override
    public List<ServiceProduct> queryAuthServiceProducts(Map object) {
        return serviceProductMapper.queryAuthServiceProducts(object);
    }

    @Override
    public int availableCountOfProduct(Integer serviceCategoryId) {
        return 0;
    }

//    @Override
//    public List<CodeItem> getServiceVersion(Integer id) {
//        return serviceProductMapper.getServiceVersion(id);
//    }
//    /**
//     * 获取可用的服务产品创建数量
//     *
//     * @param serviceCategoryId 服务产品类型id
//     * @return >=0
//     */
//    @Override
//    public int availableCountOfProduct(Integer serviceCategoryId) {
//        LicenseModule module=null;
//        int lc_count=0;
//        int ps_count=0;
//        try {
//            HashMap ps= getProductNumbersOnCategory();
//            Long l=(Long)ps.get(serviceCategoryId);
//            ps_count= (null != l)?l.intValue():0;
//            switch (serviceCategoryId){
//                case 1:
//                    module=LicenseManager.getLicenseModule(LicenseManager.ITSM_PRODUCT_KEY, SCLCEnum.SQ_LIC_CODE);
//                    break;
//                case 2:
//                    module=LicenseManager.getLicenseModule(LicenseManager.ITSM_PRODUCT_KEY, SCLCEnum.IN_LIC_CODE);
//                    break;
//                case 3:
//                    module=LicenseManager.getLicenseModule(LicenseManager.ITSM_PRODUCT_KEY, SCLCEnum.CH_LIC_CODE);
//                    break;
//                case 4:
//                    module=LicenseManager.getLicenseModule(LicenseManager.ITSM_PRODUCT_KEY, SCLCEnum.PR_LIC_CODE);
//                    break;
//                case 5:
//                    module=LicenseManager.getLicenseModule(LicenseManager.ITSM_PRODUCT_KEY, SCLCEnum.RE_LIC_CODE);
//                    break;
//
//
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        if(module!=null){
//           lc_count= module.getModuleCount();
//        }else{
//            lc_count=99999;
//        }
//
//        return lc_count-ps_count;
//    }

    @Override
    public HashMap<String, String> getProductNumbersOnCategory() {
        HashMap<String, String> map=new HashMap<String, String>();
        for(Map<String,String> m:serviceProductMapper.getProductNumbersOnCategory()){
            map.put(m.get("catid"), m.get("count"));
        }
        return map;
    }

    public List<ServiceProduct> getProductByRegion(HashMap map){
        List<ServiceProduct> list=serviceProductMapper.getProductByRegion(map);
        return list;
    }
}

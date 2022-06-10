package com.sungcor.baobiao.service.Impl;

import com.sungcor.baobiao.STSMConstant;
import com.sungcor.baobiao.entity.ServiceCategory;
import com.sungcor.baobiao.mapper.ServiceCategoryMapper;
import com.sungcor.baobiao.service.IServiceCategoryService;
import com.sungcor.baobiao.utils.LicenseControl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ServiceCategoryServiceImpl implements IServiceCategoryService {
    @Autowired
    private ServiceCategoryMapper serviceCategoryMapper;
    @Override
    public ServiceCategory getCategoryById(int id) {
//        IMemcache<ServiceCategory> cacheL= MemCacheFactory.getRemoteMemCache(ServiceCategory.class);
//        if(cacheL.get("serviceCategory"+id)!=null){
//            return cacheL.get("serviceCategory"+id);
//        }else{
            ServiceCategory sc = serviceCategoryMapper.getCategoryById(id);
//            cacheL.set("serviceCategory"+id,sc);
            return sc;
//        }
    }
    @Override
    public List<ServiceCategory> getAll() {
//        IMemcache<ServiceCategory> cacheL= MemCacheFactory.getRemoteMemCache(ServiceCategory.class);
//        if(cacheL.getCollection("serviceCategory")!=null){
//            return cacheL.getCollection("serviceCategory");
//        }else{
            List<ServiceCategory> list=new ArrayList<ServiceCategory>();

            List<ServiceCategory> clists = serviceCategoryMapper.getAll();
            for(ServiceCategory serviceCategory : clists){
                if(!LicenseControl.isCaseProblem()){  //LIC 权限控制处理
                    if ( STSMConstant.NUM_FOUR ==  serviceCategory.getId()) {//排除问题类型
                        continue;
                    }
                }
                if(!LicenseControl.isCaseChange()){
                    if (STSMConstant.NUM_THREE == serviceCategory.getId()
                            || STSMConstant.NUM_FIVE == serviceCategory.getId()  ) {//排除问题和发布
                        continue;
                    }
                }
                if(!LicenseControl.isSparepartsManagement()){
                    if (serviceCategory.getId() == STSMConstant.NUM_SEVEN
                    ) {//排除备件
                        continue;
                    }
                }
                if(!LicenseControl.isTask()){
                    if (STSMConstant.NUM_SIX == serviceCategory.getId()
                    ) {//排除问题和发布
                        continue;
                    }
                }
                list.add(serviceCategory);
//            }



//            cacheL.setCollection("serviceCategory",list);

        }
        return list;
    }

}

package com.sungcor.baobiao.mapper;

import com.sungcor.baobiao.entity.ServiceCategory;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Component
@Repository
public interface ServiceCategoryMapper {
    /**
     * 根据ID获取服务产品分类对象
     * @param id
     * @return
     */
    public ServiceCategory getCategoryById(int id);
    /**
     * 得到所有的服务产品分类对象
     * @return
     */
    public List<ServiceCategory> getAll();
}

package com.sungcor.baobiao.mapper;

import com.sungcor.baobiao.entity.ServiceProduct;
import com.sungcor.baobiao.entity.SungcorProduct;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Mapper
@Component
@Repository
public interface SungcorProductMapper {
    SungcorProduct getSungcorProduct(String productName);

}

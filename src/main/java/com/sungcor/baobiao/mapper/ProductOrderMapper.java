package com.sungcor.baobiao.mapper;

import com.sungcor.baobiao.entity.Customer;
import com.sungcor.baobiao.entity.Product;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;


@Mapper
@Component
@Repository
public interface ProductOrderMapper {
    Customer getTheCustomer(String Id);
    Product getTheProduct(String Id);
    void updateCustomer(Customer customer);
    void updateProduct(Product product);
    void getGoods();
    void getMoney();
}

package com.sungcor.baobiao.mapper;

import com.sungcor.baobiao.entity.ReviewInfo;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Mapper
@Component
@Repository
public interface ReviewInfoMapper {
    void save(ReviewInfo rw);

    ReviewInfo getRevieInfoByNhAndOp(Map para);

    List<ReviewInfo> listRevieInfoByNh(Map para);

    List<ReviewInfo> listRevieInfoByNh2(Map para);

    void update(ReviewInfo reviewInfo);
}

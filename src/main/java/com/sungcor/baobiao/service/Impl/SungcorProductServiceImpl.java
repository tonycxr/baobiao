package com.sungcor.baobiao.service.Impl;

import com.sungcor.baobiao.entity.SungcorProduct;
import com.sungcor.baobiao.mapper.SungcorProductMapper;
import com.sungcor.baobiao.service.ISungcorProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;

@Transactional
@Service
public class SungcorProductServiceImpl implements ISungcorProductService {
    @Autowired
    private SungcorProductMapper sungcorProductMapper;

    @Override
    public SungcorProduct getSungcorProduct(String productName) {
        return sungcorProductMapper.getSungcorProduct(productName);
    }

    @Override
    public Integer getProductProfit(Map map){
        SungcorProduct target = sungcorProductMapper.getSungcorProduct(map.get("productName").toString());
        try{
            Integer seasonNumber = (Integer) map.get("seasonNumber");
            switch (seasonNumber) {
                case 1:
                    return target.getFirstSeason();
                case 2:
                    return target.getSecondSeason();
                case 3:
                    return target.getThirdSeason();
                case 4:
                    return target.getFourthSeason();
            }
        }catch (Exception e){
            e.printStackTrace();
            return 0;
        }
        return 0;

    }
}

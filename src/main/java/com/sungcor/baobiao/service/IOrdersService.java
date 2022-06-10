package com.sungcor.baobiao.service;

import com.sungcor.baobiao.entity.FMFormArea;

import java.util.List;
import java.util.Map;

public interface IOrdersService {
   Map<String, String> setTypeValue(List<FMFormArea> areas, Map<String, String> map, String flag);
}

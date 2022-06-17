package com.sungcor.baobiao.service;

import com.runqian.report4.dataset.DataSet;
import com.runqian.report4.model.ReportDefine;
import com.runqian.report4.usermodel.Context;
import com.runqian.report4.usermodel.DataSetConfig;
import com.runqian.report4.usermodel.IReport;
import com.sungcor.baobiao.entity.Result;
import com.sungcor.baobiao.entity.SungcorProduct;

import java.io.IOException;
import java.util.Map;

public interface ISungcorProductService {
    SungcorProduct getSungcorProduct(String productName);
    Integer getProductProfit(Map map);
    IReport getTheRaq(String path) throws Exception;
    DataSet createDataSet(Map map);
}

package com.chinawiserv.onecenter.itsm.customReport.dataSet;

import com.runqian.report4.dataset.DataSet;
import com.runqian.report4.dataset.IDataSetFactory;
import com.runqian.report4.usermodel.Context;
import com.runqian.report4.usermodel.DataSetConfig;
import com.sungcor.baobiao.report.service.IReportService;
import com.sungcor.baobiao.utils.ReportUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 * Date: 16-5-31
 * Time: 下午5:54
 * To change this template use File | Settings | File Templates.
 */
@Service
public class DataNumDataSet extends ReportUtil implements IDataSetFactory {

    @Autowired
    public IReportService reportService;

    @Override
    public DataSet createDataSet(Context context, DataSetConfig dataSetConfig, boolean b) {

        Map map = context.getParamMap(false);
        DataSet ds1 =  reportService.findSubReportDataSet(map);

        return ds1;  //To change body of implemented methods use File | Settings | File Templates.
    }
}

package com.sungcor.baobiao.report.service;
import com.runqian.report4.dataset.DataSet;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public interface IReportService {

    public List<HashMap> findReportModelQuery(HttpServletRequest request,String modelId,String userId,String flag,int modelType);

    //缓存默认的查询条件(时间，服务类型，服务产品)
    public void defaultModelQuery(String modelId,String userId,String beginTime,String endTime,int modelType);

    public DataSet findReportDataSet(List<HashMap> dimensionList,
                                     List<HashMap> statIndexList,
                                     List<HashMap> queryList, String modelId, String userId, List<HashMap> customIndexList);

    public DataSet findSubReportDataSet(Map map);

    HashMap<String, String> getQueryDate(String datePurview);
}

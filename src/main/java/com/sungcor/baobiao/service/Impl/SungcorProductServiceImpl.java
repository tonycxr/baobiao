package com.sungcor.baobiao.service.Impl;

import com.runqian.report4.ide.usermodel.ReportExporter;
import com.runqian.report4.model.ReportDefine;
import com.runqian.report4.usermodel.Context;
import com.runqian.report4.usermodel.Engine;
import com.runqian.report4.usermodel.IReport;
import com.runqian.report4.util.ReportUtils;
import com.sungcor.baobiao.entity.ExportType;
import com.sungcor.baobiao.entity.Result;
import com.sungcor.baobiao.entity.SungcorProduct;
import com.sungcor.baobiao.entity.UserInfoBean;
import com.sungcor.baobiao.mapper.SungcorProductMapper;
import com.sungcor.baobiao.mapper.UserMapper;
import com.sungcor.baobiao.report.bean.ReportModelBean;
import com.sungcor.baobiao.report.service.*;
import com.sungcor.baobiao.service.ISungcorProductService;
import com.sungcor.baobiao.utils.DateUtil;
import java.io.File;


import com.sungcor.baobiao.utils.ExcelUtil;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.OutputStream;
import java.lang.reflect.Field;
import java.util.*;

@Transactional
@Service
public class SungcorProductServiceImpl implements ISungcorProductService {
    @Autowired
    private SungcorProductMapper sungcorProductMapper;

    @Autowired
    private IReportModelService reportModelService;

    @Autowired
    private UserMapper userMapper;

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

    @Override
    public Result getTheRaq(Map map) {
        String path = map.get("path").toString();
        try {
            File file =new File(path);
            String taskId = "cxrTest";
            //报表模板ID
            Integer modelId = 1;
            //获取模板信息
            ReportModelBean reportModelBean = reportModelService.findBeanById(String.valueOf(modelId));
            Context cxt = new Context();
            cxt.setParamValue("modelId",modelId);
            cxt.setParamValue("userId","admin");
            cxt.setParamValue("taskId",taskId);
            cxt.setParamValue("reportName",reportModelBean.getReportName());
            cxt.setParamValue("showTable",reportModelBean.getShowTable()+"");
            cxt.setParamValue("showChart",reportModelBean.getShowChart()+"");
            cxt.setParamValue("skin","/css/default");
            ReportDefine rd = (ReportDefine) ReportUtils.read(String.valueOf(file));
            String beginTime = DateUtil.getCurrentTime("yyyy-MM-dd hh:mm:ss");
            String endTime =  DateUtil.parse(DateUtil.getNowWeekSunday(new Date()),"yyyy-MM-dd hh:mm:ss");
            String reportTime = "统计时间:"+ beginTime+"至"+endTime;
            cxt.setParamValue("reportTime",reportTime);
            Engine engine = new Engine(rd, cxt);
            IReport iReport = engine.calc();
            String exportType = map.get("exportType").toString();
            for (ExportType type : ExportType.values()){
                if(exportType.equals(type.toString())){
                    Byte n = type.getNumber().byteValue();
                    String targetPath = map.get("targetPath").toString() + "/"+file.getName()+"target."+type.toString();
                    try{
                        ReportExporter re = new ReportExporter(targetPath,n);
                        re.export(iReport);
                    }catch (Throwable throwable){
                        throwable.printStackTrace();
                        return Result.error("导出文件失败");
                    }

                    return Result.ok("导出报表文件成功");
                }
            }
        }catch (FileNotFoundException e){
            return Result.error("文件不存在");
        }catch (Exception e){
            return Result.error("读取文件错误");
        }
        return Result.error("文件类型输入错误");
    }

    @Override
    public Result getTheExcel(HttpServletResponse response){
        try {
            String fileName = "export";
            String sheetName = "sheet";
            List<String> titleList = new ArrayList<>();
            for(Field field:SungcorProduct.class.getDeclaredFields()){
                titleList.add(field.getName());
            }
            List<SungcorProduct> resultList = sungcorProductMapper.getAllSungcorProduct();
            HSSFWorkbook workbook = ExcelUtil.createExcel(sheetName, titleList, resultList );
            OutputStream output = response.getOutputStream();
            response.reset();
            //中文名称要进行编码处理
            response.setHeader("Content-disposition", "attachment; filename=" + new String(fileName.getBytes("GB2312"), "ISO8859-1") + ".xls");
            workbook.write(output);
            output.close();
            return Result.ok();

        } catch (Exception e) {
            e.printStackTrace();
            return Result.error(e.toString());
        }

    }


}


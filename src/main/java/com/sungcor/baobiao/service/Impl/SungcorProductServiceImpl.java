package com.sungcor.baobiao.service.Impl;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.write.style.column.LongestMatchColumnWidthStyleStrategy;
import com.runqian.report4.model.ReportDefine;
import com.runqian.report4.usermodel.Context;
import com.runqian.report4.usermodel.Engine;
import com.runqian.report4.usermodel.IReport;
import com.runqian.report4.util.ReportUtils;
import com.sungcor.baobiao.entity.Result;
import com.sungcor.baobiao.entity.SungcorProduct;
import com.sungcor.baobiao.mapper.SungcorProductMapper;
import com.sungcor.baobiao.mapper.UserMapper;
import com.sungcor.baobiao.report.bean.ReportHandler;
import com.sungcor.baobiao.report.bean.ReportModelBean;
import com.sungcor.baobiao.report.service.*;
import com.sungcor.baobiao.service.ISungcorProductService;
import com.sungcor.baobiao.utils.DateUtil;

import java.io.*;
import com.sungcor.baobiao.utils.ExcelUtil;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
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
    public Integer getProductProfit(Map map) {
        SungcorProduct target = sungcorProductMapper.getSungcorProduct(map.get("productName").toString());
        try {
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
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
        return 0;

    }

    @Override
    public Result getTheRaq(HttpServletResponse response,Map map) throws Exception {
//            MultipartResolver resolver = new CommonsMultipartResolver(request.getSession().getServletContext());
//
//            MultipartHttpServletRequest multipartHttpServletRequest = resolver.resolveMultipart(request);
//
//            Map<String, MultipartFile> fileMap = multipartHttpServletRequest.getFileMap();
//
//            for (Map.Entry<String,MultipartFile> entry : fileMap.entrySet()){
//                MultipartFile file =entry.getValue();
//
//            }
        String path = map.get("path").toString();
        File file = new File(path);
        String taskId = "cxrTest";
        //报表模板ID
        Integer modelId = 1;
        //获取模板信息
        ReportModelBean reportModelBean = reportModelService.findBeanById(String.valueOf(modelId));
        Context cxt = new Context();
        cxt.setParamValue("modelId", modelId);
        cxt.setParamValue("userId", "admin");
        cxt.setParamValue("taskId", taskId);
        cxt.setParamValue("reportName", reportModelBean.getReportName());
        cxt.setParamValue("showTable", reportModelBean.getShowTable() + "");
        cxt.setParamValue("showChart", reportModelBean.getShowChart() + "");
        cxt.setParamValue("skin", "/css/default");
        ReportDefine rd = (ReportDefine) ReportUtils.read(String.valueOf(file));
        Date now = new Date();
        String beginTime = DateUtil.parse(now,"yyyy-MM-dd HH:mm:ss");
        String endTime = DateUtil.parse(DateUtil.getNowWeekSunday(now), "yyyy-MM-dd HH:mm:ss");
        String reportTime = "统计时间:" + beginTime + "至" + endTime;
        cxt.setParamValue("reportTime", reportTime);
        Engine engine = new Engine(rd, cxt);
        IReport iReport = engine.calc();
        String targetPath = map.get("targetPath").toString() + "/" + file.getName().substring(0,file.getName().lastIndexOf("."))+".xls";
        try {
            ReportHandler rh = new ReportHandler(targetPath, (byte) 1);
            rh.export(iReport);
            return Result.ok();
        } catch (Throwable throwable) {
            throwable.printStackTrace();
            return Result.error(throwable.toString());
        }

    }

    @Override
    public Result getTheExcel(HttpServletResponse response, String name) {
        try {
            String fileName = name;
            String sheetName = "sheet";
            List<String> titleList = new ArrayList<>();
            for (Field field : SungcorProduct.class.getDeclaredFields()) {
                titleList.add(field.getName());
            }
            List<SungcorProduct> resultList = sungcorProductMapper.getAllSungcorProduct();
            HSSFWorkbook workbook = ExcelUtil.createExcel(sheetName, titleList, resultList);
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

    @Override
    public Result getAnnotationExcel(Map map) {
        try{
            String fileName = map.get("path").toString()+ "/" + System.currentTimeMillis() +"SungcorProduct.xlsx";
            // 这里 需要指定写用哪个class去读，然后写到第一个sheet，名字为模板 然后文件流会自动关闭
            // 如果这里想使用03 则 传入excelType参数即可
            EasyExcel.write(fileName, SungcorProduct.class).sheet("模板").doWrite(sungcorProductMapper.getAllSungcorProduct());
            return Result.ok();
        }catch (Exception e){
            e.printStackTrace();
            return Result.error(e.toString());
        }
    }

}


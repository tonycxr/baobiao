package com.sungcor.baobiao.utils;

import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import java.lang.reflect.Field;
import java.util.List;



public class ExcelUtil {

    public static HSSFWorkbook createExcel(String sheetName,
                                           List<String> titleList, List dataList) throws IllegalAccessException {

        HSSFWorkbook wb = new HSSFWorkbook();
        HSSFSheet sheet=wb.createSheet(sheetName);
        HSSFRow rowTitle=sheet.createRow(0);
        for (int i = 0; i < titleList.size(); i++) {
            rowTitle.createCell(i).setCellValue(titleList.get(i));
        }
        for (int i = 0; i < dataList.size(); i++) {
            HSSFRow rowData = sheet.createRow(i+1);
            Class cl = dataList.get(i).getClass();
            Field[] fields = cl.getDeclaredFields();
            for (int j = 0; j < fields.length; j++) {
                fields[j].setAccessible(true);
                if(fields[j].get(dataList.get(i))!=null){
                    rowData.createCell(j).setCellValue(fields[j].get(dataList.get(i)).toString());
                }else {
                    rowData.createCell(j).setCellValue("null");
                }
            }
        }
        return wb;
    }
}

package com.sungcor.baobiao.entity;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.write.style.ColumnWidth;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("sungcorproduct")
@ColumnWidth(25)
public class SungcorProduct {

    @ExcelProperty("产品名称")
    private String productName;
    @ExcelProperty("第一季度利润")
    private Integer firstSeason;
    @ExcelProperty("第二季度利润")
    private Integer secondSeason;
    @ExcelProperty("第三季度利润")
    private Integer thirdSeason;
    @ExcelProperty("第四季度利润")
    private Integer fourthSeason;
}

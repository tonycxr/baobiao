package com.sungcor.baobiao.entity;

public enum ExportType {
    doc(6),xls(1),pdf(4),txt(8),xml(9),html(10);
    private final Integer number;
    private ExportType(Integer number){
        this.number= number;
    }
    public Integer getNumber(){
        return this.number;
    }
}

package com.sungcor.baobiao.report.bean;

import lombok.Data;
//import com.runqian.report4.dataset.DataSet;

@Data
public class Function {

    private String code;
    private String name;
    private String type;
    private String parent;
    private int sn;
    private String icon;
    private String open_model;
    private String open_address;
    private int width;
    private int height;
    private String available;
    private String empower;
//    private DataSet dataSet;

    public Function(String code, String name, String type, String parent,
                    int sn,  String empower , String available) {
        super();
        this.code = code;
        this.name = name;
        this.type = type;
        this.parent = parent;
        this.sn = sn;
        this.empower = empower;
        this.available = available;
    }

    public Function(String code, String name, String type, String parent,
                    int sn, String icon, String open_model, String open_address,
                    int width, int height, String available , String empower) {
        super();
        this.code = code;
        this.name = name;
        this.type = type;
        this.parent = parent;
        this.sn = sn;
        this.icon = icon;
        this.open_model = open_model;
        this.open_address = open_address;
        this.width = width;
        this.height = height;
        this.available = available;
        this.empower = empower;
    }


}

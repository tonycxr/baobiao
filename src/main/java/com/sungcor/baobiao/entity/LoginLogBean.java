package com.sungcor.baobiao.entity;

import lombok.Data;

import java.util.Date;

@Data
public class LoginLogBean {
    private String id;
    private String user;
    private String ip;
    private Date loginTime = null;
    private Date logoutTime = null;
}

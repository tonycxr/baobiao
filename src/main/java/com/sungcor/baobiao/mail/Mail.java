package com.sungcor.baobiao.mail;

import lombok.Data;

@Data
public final class Mail {
    private String host;
    private String port;
    private String account;
    private String user;
    private String pwd;
}

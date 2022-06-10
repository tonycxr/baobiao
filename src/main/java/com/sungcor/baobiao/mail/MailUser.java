package com.sungcor.baobiao.mail;

public final class MailUser {
    private String mailAddress;
    private String nickName;

    public MailUser(String mailAddress){
        this.mailAddress = mailAddress;
    }

    public String getMailAddress() {
        return mailAddress;
    }

    public void setMailAddress(String mailAddress) {
        this.mailAddress = mailAddress;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    @Override
    public String toString() {
        return mailAddress;
    }
}

package com.sungcor.baobiao.mail;

public class MailSender {
    private Mail mail;
    private MailMessage mailMessage;

    public MailSender(Mail mail, MailMessage mailMessage) {
        this.mailMessage = mailMessage;
        this.mail = mail;
    }

    public Mail getMail() {
        return mail;
    }

    public MailMessage getMailMessage() {
        return mailMessage;
    }
}

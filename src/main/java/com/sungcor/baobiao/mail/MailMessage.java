package com.sungcor.baobiao.mail;

public final class MailMessage {
    private MailUser from;
    private MailUser[] to;
    private MailUser[] cc;
    private String subject;
    private StringBuilder content;

    public MailUser getFrom() {
        return from;
    }

    public void setFrom(MailUser from) {
        this.from = from;
    }

    public MailUser[] getTo() {
        return to;
    }

    public void setTo(MailUser[] to) {
        this.to = to;
    }

    public MailUser[] getCc() {
        return cc;
    }

    public void setCc(MailUser[] cc) {
        this.cc = cc;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public StringBuilder getContent() {
        return content;
    }

    public void setContent(StringBuilder content) {
        this.content = content;
    }

    @Override
    public String toString() {
        StringBuilder str = new StringBuilder("");
        str.append("from [").append(from.getMailAddress()).append("] to [");
        boolean isFirst = true;
        for(MailUser toU : to){
            if(isFirst){
                str.append(toU.getMailAddress());
                isFirst = false;
            }else{
                str.append(",").append(toU.getMailAddress());
            }
        }
        str.append("] cc [");
        isFirst = true;
        for(MailUser ccU : cc){
            if(isFirst){
                str.append(ccU.getMailAddress());
            }else{
                str.append(",").append(ccU.getMailAddress());
            }
        }
        str.append("] subject [").append(subject).append("]");

        return str.toString();
    }
}

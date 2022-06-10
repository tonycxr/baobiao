package com.sungcor.baobiao.entity;

import org.apache.commons.lang.StringUtils;

import java.util.Date;

/**
 * User: FanLei
 * Date: 2012-04-27
 * Time: 09:32
 * Descript:
 */
public class ReviewInfo {

    private Integer id;

    private Integer nodeHistoryID;

    private Integer processInstanceID;


    private NodeHistory nodeHistory;

    private String optPersonID;

    private UserInfoBean optPerson;

    private Date optTime;
    /**
     * 用于手机终端显示
     */
    private String optTimeStr;
    private String optReason;

    private String status;  //处理状态（主要用于判断签收）

    public String getOptTimeStr() {
        return optTimeStr;
    }

    public void setOptTimeStr(String optTimeStr) {
        this.optTimeStr = optTimeStr;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public int getNodeHistoryID() {
        return nodeHistoryID;
    }

    public void setNodeHistoryID(int nodeHistoryID) {
        this.nodeHistoryID = nodeHistoryID;
    }

    public int getProcessInstanceID() {
        return processInstanceID;
    }

    public void setProcessInstanceID(int processInstanceID) {
        this.processInstanceID = processInstanceID;
    }

    public NodeHistory getNodeHistory() {
        return nodeHistory;
    }

    public void setNodeHistory(NodeHistory nodeHistory) {
        this.nodeHistory = nodeHistory;
    }

    public String getOptPersonID() {
        return optPersonID;
    }

    public void setOptPersonID(String optPersonID) {
        this.optPersonID = optPersonID;
    }

    public UserInfoBean getOptPerson() {
        return optPerson;
    }

    public void setOptPerson(UserInfoBean optPerson) {
        this.optPerson = optPerson;
    }

    public Date getOptTime() {
        return optTime;
    }

    public void setOptTime(Date optTime) {
        this.optTime = optTime;
    }

    public String getOptReason() {
        return optReason;
    }

    public void setOptReason(String optReason) {
        this.optReason = optReason;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    //id字符串 用于oracle映射返回id值
    private String idstr;

    public String getIdstr() {
        return idstr;
    }

    public void setIdstr(String idstr) {
        this.idstr = idstr;
        try{
            if(StringUtils.isNotBlank(idstr)){
                this.id = Integer.parseInt(idstr);
            }
        }catch (Exception e){
            e.printStackTrace();
        }

    }
}

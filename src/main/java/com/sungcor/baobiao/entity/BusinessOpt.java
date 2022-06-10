package com.sungcor.baobiao.entity;

import org.apache.commons.lang.StringUtils;

import java.util.Date;
import java.util.List;

/**
 * User: FanLei
 * Date: 2012-04-27
 * Time: 09:33
 * Descript:
 */
public class BusinessOpt {

    private Integer id;

    private String optPersonID;

    private UserInfoBean optPerson;

    private Date optTime;

    private String optType;

    private int nodeHistoryID;

    private int processInstanceID;

    private NodeHistory nodeHistory;

    private String optReason;

    private String appendAtt;

    private List<ReviewInfo> reviewInfos;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public String getOptType() {
        return optType;
    }

    public void setOptType(String optType) {
        this.optType = optType;
    }

    public int getNodeHistoryID() {
        return nodeHistoryID;
    }

    public void setNodeHistoryID(int nodeHistoryID) {
        this.nodeHistoryID = nodeHistoryID;
    }

    public NodeHistory getNodeHistory() {
        return nodeHistory;
    }

    public void setNodeHistory(NodeHistory nodeHistory) {
        this.nodeHistory = nodeHistory;
    }

    public String getOptReason() {
        return optReason;
    }

    public void setOptReason(String optReason) {
        this.optReason = optReason;
    }

    public String getAppendAtt() {
        return appendAtt;
    }

    public void setAppendAtt(String appendAtt) {
        this.appendAtt = appendAtt;
    }

    public int getProcessInstanceID() {
        return processInstanceID;
    }

    public void setProcessInstanceID(int processInstanceID) {
        this.processInstanceID = processInstanceID;
    }

    public List<ReviewInfo> getReviewInfos() {
        return reviewInfos;
    }

    public void setReviewInfos(List<ReviewInfo> reviewInfos) {
        this.reviewInfos = reviewInfos;
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

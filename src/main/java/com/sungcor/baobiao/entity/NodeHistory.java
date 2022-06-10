package com.sungcor.baobiao.entity;


import org.apache.commons.lang.StringUtils;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * User: FanLei
 * Date: 2012-04-26
 * Time: 20:50
 * Descript:
 */


public class NodeHistory {


    private Integer id;


    private String nodeName;


    private String implementerID;


    private UserInfoBean implementer;


    private String implementGroupID;

    //当前前处理组内处理人集合
    private Map<String,UserInfoBean> implementStaffs;

    //当前处理人状态集合
    private Map<String,String> statusStaffs;

    private Date startTime;


    private Date endTime;

    private Date responseTime;

    private String status;

    private Integer processInstanceID;

    private Integer runFlag;

    private Integer nodeInstanceID;

    private ProcessInstance processInstance;

    private List<BusinessOpt> businessOpts;

    //前置节点ID，如果无前置节点则默认值为-1
    private int preNodeHistoryID = -1;

    //节点状态名
    private String statusName;


    private int asideFlag;


    private int decisionFlag;


    private int timerFlag;


    private String includedDeal;

    private String dealFlag;

    private int hasLinkProcess;

    private int hacFlag;


    //是否按区域过滤标识
    private int roleByarea;

    private String execRoleType;

    //搁置审批人
    public String asideAuditor;

    //搁置审批组
    public String asideAuditorGroup;


    //搁置审批人实体
    private UserInfoBean asideAuditorBean;

    //搁置时限
    public String asideAuditMinutes;

    //是否关联配置项
    public int relatedCI;

    private int preNodeHistoryId;

    public int getRelatedCI() {
        return relatedCI;
    }

    public void setRelatedCI(int relatedCI) {
        this.relatedCI = relatedCI;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNodeName() {
        return nodeName;
    }

    public void setNodeName(String nodeName) {
        this.nodeName = nodeName;
    }

    public String getImplementerID() {
        return implementerID;
    }

    public void setImplementerID(String implementerID) {
        this.implementerID = implementerID;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public Date getResponseTime() {
        return responseTime;
    }

    public void setResponseTime(Date responseTime) {
        this.responseTime = responseTime;
    }

    public String getStatus() {
        return (status != null)?this.status:"" ;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getProcessInstanceID() {
        return processInstanceID;
    }

    public void setProcessInstanceID(int processInstanceID) {
        this.processInstanceID = processInstanceID;
    }

    public int getRunFlag() {
        return runFlag;
    }

    public void setRunFlag(Integer runFlag) {
        this.runFlag = runFlag;
    }

    public Integer getNodeInstanceID() {
        return nodeInstanceID;
    }

    public void setNodeInstanceID(int nodeInstanceID) {
        this.nodeInstanceID = nodeInstanceID;
    }

    public ProcessInstance getProcessInstance() {
        return processInstance;
    }

    public void setProcessInstance(ProcessInstance processInstance) {
        this.processInstance = processInstance;
    }

    public List<BusinessOpt> getBusinessOpts() {
        return businessOpts;
    }

    public void setBusinessOpts(List<BusinessOpt> businessOpts) {
        this.businessOpts = businessOpts;
    }

    public int getPreNodeHistoryID() {
        return preNodeHistoryID;
    }

    public void setPreNodeHistoryID(int preNodeHistoryID) {
        this.preNodeHistoryID = preNodeHistoryID;
    }

    public String getStatusName() {
        return statusName;
    }

    public void setStatusName(String statusName) {
        this.statusName = statusName;
    }

    public int getAsideFlag() {
        return asideFlag;
    }

    public void setAsideFlag(int asideFlag) {
        this.asideFlag = asideFlag;
    }

    public String getIncludedDeal() {
        return includedDeal;
    }

    public void setIncludedDeal(String includedDeal) {
        this.includedDeal = includedDeal;
    }

    public String getDealFlag() {
        return dealFlag;
    }

    public void setDealFlag(String dealFlag) {
        this.dealFlag = dealFlag;
    }

    public UserInfoBean getImplementer() {
        return implementer;
    }

    public void setImplementer(UserInfoBean implementer) {
        this.implementer = implementer;
    }

    public String getImplementGroupID() {
        return implementGroupID;
    }

    public void setImplementGroupID(String implementGroupID) {
        this.implementGroupID = implementGroupID;
    }


    public Map<String, UserInfoBean> getImplementStaffs() {
        if(implementStaffs==null){
            implementStaffs = new HashMap<String,UserInfoBean>();
        }
        return implementStaffs;
    }

    public void setImplementStaffs(Map<String, UserInfoBean> implementStaffs) {
        this.implementStaffs = implementStaffs;
    }

    public int getDecisionFlag() {
        return decisionFlag;
    }

    public void setDecisionFlag(int decisionFlag) {
        this.decisionFlag = decisionFlag;
    }

    public int getTimerFlag() {
        return timerFlag;
    }

    public void setTimerFlag(int timerFlag) {
        this.timerFlag = timerFlag;
    }

    public int getHasLinkProcess() {
        return hasLinkProcess;
    }

    public void setHasLinkProcess(int hasLinkProcess) {
        this.hasLinkProcess = hasLinkProcess;
    }

    public int getHacFlag() {
        return hacFlag;
    }

    public void setHacFlag(int hacFlag) {
        this.hacFlag = hacFlag;
    }

    public int getRoleByarea() {
        return roleByarea;
    }

    public void setRoleByarea(int roleByarea) {
        this.roleByarea = roleByarea;
    }

    public String getAsideAuditor() {
        return asideAuditor;
    }

    public void setAsideAuditor(String asideAuditor) {
        this.asideAuditor = asideAuditor;
    }

    public String getAsideAuditorGroup() {
        return asideAuditorGroup;
    }

    public void setAsideAuditorGroup(String asideAuditorGroup) {
        this.asideAuditorGroup = asideAuditorGroup;
    }

    public UserInfoBean getAsideAuditorBean() {
        return asideAuditorBean;
    }

    public void setAsideAuditorBean(UserInfoBean asideAuditorBean) {
        this.asideAuditorBean = asideAuditorBean;
    }

    public String getAsideAuditMinutes() {
        return asideAuditMinutes;
    }

    public void setAsideAuditMinutes(String asideAuditMinutes) {
        this.asideAuditMinutes = asideAuditMinutes;
    }

    public int getPreNodeHistoryId() {
        return preNodeHistoryId;
    }

    public void setPreNodeHistoryId(int preNodeHistoryId) {
        this.preNodeHistoryId = preNodeHistoryId;
    }

    public Map<String, String> getStatusStaffs() {
        return statusStaffs;
    }

    public void setStatusStaffs(Map<String, String> statusStaffs) {
        this.statusStaffs = statusStaffs;
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
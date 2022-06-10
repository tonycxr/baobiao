package com.sungcor.baobiao.entity;


import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * User: FanLei
 * Date: 2012-04-26
 * Time: 21:04
 * Descript:
 */
public class ProcessInstance {

    private Integer id;

    private Integer processID;

    private Process process;

    private String states;

    //创建人ID
    private String creatorID;

    private UserInfoBean creator;

    private Date createTime;

    private String stringCreateTime;

    private String currentNodeName;

    private Date enterTime;

    private Date leaveTime;

    private Integer processDefinitionID;

    private ProcessDefinition processDefinition;

    private Integer formInstanceID;

    //FORM 版本ID

    private Integer formVersionID;

    private String jbpmProcessInstanceID;


    private String statesName;

    private String forkFlag;

    private Integer parentProcessInsID;


    private Integer hangNodeInstanceID;

    private Integer hangProcessInstanceID;

    private String hangMode;


    private List<NodeInstance> nodeInstances;

    private List<NodeHistory> nodeHistorys;

    private String dealTime; //数值（单位分钟）

    private String dealFlag;//处理标识

    private String responseTime;//响应时间

    private String caseNO;//case number


    private List<UserInfoBean> ownersLeaderList;

    private String relaType;
    private Integer reporter;
    private String reporterSource;
    private String reporterName;
    private String caseSubject;
    private Integer casePriority;
    private String priorityName;
    private Integer taskPriority;
    private Date signTime;
    private Integer caseUrgency;
    private Integer caseArea;
    private String olaResponseTime;
    private String olaSoluteTime;
    private String slaResponseTime;
    private String slaSoluteTime;
    //工单SLA响应到期时间
    private String slaResponseEndTime;
    //工单SLA解决到期时间
    private String slaSoluteEndTime;

    private String ownersLeaderListName;
    private long totalAsideMinutes;//搁置时间

    private Integer toKnowledgeFlag;
    private String outKnow;
    private String incidentDescription;
    private String diagnoseResult;
    private String soluteResult;

    public String getSelfdesk() {
        return selfdesk;
    }

    public void setSelfdesk(String selfdesk) {
        this.selfdesk = selfdesk;
    }

    private String selfdesk;
    /**
     * 供应商ID
     */
    private String supplierId;
    private String supplierName;
    private String incidenttype;//事件类型
    private Date planendtime;//问题计划结束时间

    private String slaStatus;
    private String surplusTime;
    private Integer serviceCategoryID;
    private String closeFlag;
    private Integer regionId;
    private Integer organizationId;

    private String reopenFlag;
    private String reopenCaseNO;
    private String reopenReason;

    public String getSlaResponseEndTime() {
        return slaResponseEndTime;
    }

    public void setSlaResponseEndTime(String slaResponseEndTime) {
        this.slaResponseEndTime = slaResponseEndTime;
    }

    public String getSlaSoluteEndTime() {
        return slaSoluteEndTime;
    }

    public void setSlaSoluteEndTime(String slaSoluteEndTime) {
        this.slaSoluteEndTime = slaSoluteEndTime;
    }

    public String getReopenFlag() {
        return reopenFlag;
    }

    public void setReopenFlag(String reopenFlag) {
        this.reopenFlag = reopenFlag;
    }

    public String getReopenCaseNO() {
        return reopenCaseNO;
    }

    public void setReopenCaseNO(String reopenCaseNO) {
        this.reopenCaseNO = reopenCaseNO;
    }

    public String getReopenReason() {
        return reopenReason;
    }

    public void setReopenReason(String reopenReason) {
        this.reopenReason = reopenReason;
    }

    public Integer getOrganizationId() {
        return organizationId;
    }

    public void setOrganizationId(Integer organizationId) {
        this.organizationId = organizationId;
    }

    public Integer getRegionId() {
        return regionId;
    }

    public void setRegionId(Integer regionId) {
        this.regionId = regionId;
    }

    public String getCloseFlag() {
        return closeFlag;
    }

    public void setCloseFlag(String closeFlag) {
        this.closeFlag = closeFlag;
    }

    public Integer getServiceCategoryID() {
        return serviceCategoryID;
    }

    public void setServiceCategoryID(Integer serviceCategoryID) {
        this.serviceCategoryID = serviceCategoryID;
    }

    public String getIncidentDescription() {
        return incidentDescription;
    }

    public void setIncidentDescription(String incidentDescription) {
        this.incidentDescription = incidentDescription;
    }

    public String getDiagnoseResult() {
        return diagnoseResult;
    }

    public void setDiagnoseResult(String diagnoseResult) {
        this.diagnoseResult = diagnoseResult;
    }

    public String getSoluteResult() {
        return soluteResult;
    }

    public void setSoluteResult(String soluteResult) {
        this.soluteResult = soluteResult;
    }

    public String getOutKnow() {
        return outKnow;
    }

    public void setOutKnow(String outKnow) {
        this.outKnow = outKnow;
    }

    public Integer getToKnowledgeFlag() {
        return toKnowledgeFlag;
    }

    public void setToKnowledgeFlag(Integer toKnowledgeFlag) {
        this.toKnowledgeFlag = toKnowledgeFlag;
    }

    public Integer getCaseArea() {
        return caseArea;
    }

    public void setCaseArea(Integer caseArea) {
        this.caseArea = caseArea;
    }

    public String getOlaResponseTime() {
        return olaResponseTime;
    }

    public void setOlaResponseTime(String olaResponseTime) {
        this.olaResponseTime = olaResponseTime;
    }

    public String getOlaSoluteTime() {
        return olaSoluteTime;
    }

    public void setOlaSoluteTime(String olaSoluteTime) {
        this.olaSoluteTime = olaSoluteTime;
    }

    public String getSlaResponseTime() {
        return slaResponseTime;
    }

    public void setSlaResponseTime(String slaResponseTime) {
        this.slaResponseTime = slaResponseTime;
    }

    public String getSlaSoluteTime() {
        return slaSoluteTime;
    }

    public void setSlaSoluteTime(String slaSoluteTime) {
        this.slaSoluteTime = slaSoluteTime;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getProcessID() {
        return processID;
    }

    public void setProcessID(Integer processID) {
        this.processID = processID;
    }

    public Process getProcess() {
        return process;
    }

    public void setProcess(Process process) {
        this.process = process;
    }

    public String getStates() {
        return (states != null)?this.states:"" ;
    }

    public void setStates(String states) {
        this.states = states;
    }

    public String getCreatorID() {
        return creatorID;
    }

    public void setCreatorID(String creatorID) {
        this.creatorID = creatorID;
    }

    public UserInfoBean getCreator() {
        return creator;
    }

    public void setCreator(UserInfoBean creator) {
        this.creator = creator;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getCurrentNodeName() {
        return currentNodeName;
    }

    public void setCurrentNodeName(String currentNodeName) {
        this.currentNodeName = currentNodeName;
    }

    public Date getEnterTime() {
        return enterTime;
    }

    public void setEnterTime(Date enterTime) {
        this.enterTime = enterTime;
    }

    public Date getLeaveTime() {
        return leaveTime;
    }

    public void setLeaveTime(Date leaveTime) {
        this.leaveTime = leaveTime;
    }

    public Integer getProcessDefinitionID() {
        return processDefinitionID;
    }

    public void setProcessDefinitionID(Integer processDefinitionID) {
        this.processDefinitionID = processDefinitionID;
    }

    public ProcessDefinition getProcessDefinition() {
        return processDefinition;
    }

    public void setProcessDefinition(ProcessDefinition processDefinition) {
        this.processDefinition = processDefinition;
    }

    public Integer getFormInstanceID() {
        return formInstanceID;
    }

    public void setFormInstanceID(Integer formInstanceID) {
        this.formInstanceID = formInstanceID;
    }

    public Integer getFormVersionID() {
        return formVersionID;
    }

    public void setFormVersionID(Integer formVersionID) {
        this.formVersionID = formVersionID;
    }

    public String getJbpmProcessInstanceID() {
        return jbpmProcessInstanceID;
    }

    public void setJbpmProcessInstanceID(String jbpmProcessInstanceID) {
        this.jbpmProcessInstanceID = jbpmProcessInstanceID;
    }


    public String getStatesName() {

        return statesName;
    }

    public void setStatesName(String statesName) {
        this.statesName = statesName;
    }

    public String getForkFlag() {
        return forkFlag;
    }

    public void setForkFlag(String forkFlag) {
        this.forkFlag = forkFlag;
    }

    public Integer getParentProcessInsID() {
        return parentProcessInsID;
    }

    public void setParentProcessInsID(Integer parentProcessInsID) {
        this.parentProcessInsID = parentProcessInsID;
    }

    public Integer getHangNodeInstanceID() {
        return hangNodeInstanceID;
    }

    public void setHangNodeInstanceID(Integer hangNodeInstanceID) {
        this.hangNodeInstanceID = hangNodeInstanceID;
    }


    public String getDealTime() {
        return dealTime;
    }

    public void setDealTime(String dealTime) {
        this.dealTime = dealTime;
    }

    public String getDealFlag() {
        return dealFlag;
    }

    public void setDealFlag(String dealFlag) {
        this.dealFlag = dealFlag;
    }

    public String getResponseTime() {
        return responseTime;
    }

    public void setResponseTime(String responseTime) {
        this.responseTime = responseTime;
    }


    public List<NodeInstance> getNodeInstances() {
        if(nodeInstances == null){
            nodeInstances = new ArrayList<NodeInstance>();
        }
        return nodeInstances;
    }

    public void setNodeInstances(List<NodeInstance> nodeInstances) {
        this.nodeInstances = nodeInstances;
    }

    public List<NodeHistory> getNodeHistorys() {
        if(nodeHistorys == null){
            nodeHistorys = new ArrayList<NodeHistory>();
        }
        return nodeHistorys;
    }

    public void setNodeHistorys(List<NodeHistory> nodeHistorys) {
        this.nodeHistorys = nodeHistorys;
    }


    //    public WorkOrderVO getWorkOrder() {
//        return workOrder;
//    }
//
//    public void setWorkOrder(WorkOrderVO workOrder) {
//        this.workOrder = workOrder;
//    }

    public String getRelaType() {
        return relaType;
    }

    public void setRelaType(String relaType) {
        this.relaType = relaType;
    }

    public Integer getHangProcessInstanceID() {
        return hangProcessInstanceID;
    }

    public void setHangProcessInstanceID(Integer hangProcessInstanceID) {
        this.hangProcessInstanceID = hangProcessInstanceID;
    }

    public String getHangMode() {
        return hangMode;
    }

    public void setHangMode(String hangMode) {
        this.hangMode = hangMode;
    }

    public String getCaseNO() {
        return caseNO;
    }

    public void setCaseNO(String caseNO) {
        this.caseNO = caseNO;
    }

    public List<UserInfoBean> getOwnersLeaderList() {
        if (null == this.ownersLeaderList)
            this.ownersLeaderList = new ArrayList<UserInfoBean>();
        return ownersLeaderList;
    }

    public void setOwnersLeaderList(List<UserInfoBean> ownersLeaderList) {
        this.ownersLeaderList = ownersLeaderList;
    }
    public void addOwnersLeader(List<UserInfoBean> list){
        for (UserInfoBean u : list){
            if (!checkDuplicateUser(this.getOwnersLeaderList(),u))
                this.getOwnersLeaderList().add(u);
        }
    }

    /**
     * 检查添加的用户是否重复
     * @param list
     * @param uib
     * @return
     */
    private boolean checkDuplicateUser(List<UserInfoBean> list , UserInfoBean uib){
        for (UserInfoBean u : list){
            if (uib.getUserId().equals(u.getUserId()))
                return true;
        }
        return false;
    }

    public Integer getReporter() {
        return reporter;
    }

    public void setReporter(Integer reporter) {
        this.reporter = reporter;
    }

    public String getCaseSubject() {
        return caseSubject;
    }

    public void setCaseSubject(String caseSubject) {
        this.caseSubject = caseSubject;
    }

    public Integer getCasePriority() {
        return casePriority;
    }

    public void setCasePriority(Integer casePriority) {
        this.casePriority = casePriority;
    }

    public Date getSignTime() {
        return signTime;
    }

    public void setSignTime(Date signTime) {
        this.signTime = signTime;
    }

    public Integer getCaseUrgency() {
        return caseUrgency;
    }

    public void setCaseUrgency(Integer caseUrgency) {
        this.caseUrgency = caseUrgency;
    }

    public String getOwnersLeaderListName() {
        return ownersLeaderListName;
    }

    public void setOwnersLeaderListName(String ownersLeaderListName) {
        this.ownersLeaderListName = ownersLeaderListName;
    }

    public Integer getTaskPriority() {
        return taskPriority;
    }

    public void setTaskPriority(Integer taskPriority) {
        this.taskPriority = taskPriority;
    }

    public long getTotalAsideMinutes() {
        return totalAsideMinutes;
    }

    public void setTotalAsideMinutes(long totalAsideMinutes) {
        this.totalAsideMinutes = totalAsideMinutes;
    }

    public String getReporterSource() {
        return reporterSource;
    }

    public void setReporterSource(String reporterSource) {
        this.reporterSource = reporterSource;
    }

    public String getReporterName() {
        return reporterName;
    }

    public void setReporterName(String reporterName) {
        this.reporterName = reporterName;
    }

    public String getSupplierId() {
        return supplierId;
    }

    public void setSupplierId(String supplierId) {
        this.supplierId = supplierId;
    }

    public String getSupplierName() {
        return supplierName;
    }

    public void setSupplierName(String supplierName) {
        this.supplierName = supplierName;
    }

    public String getIncidenttype() {
        return incidenttype;
    }

    public void setIncidenttype(String incidenttype) {
        this.incidenttype = incidenttype;
    }

    public Date getPlanendtime() {
        return planendtime;
    }

    public void setPlanendtime(Date planendtime) {
        this.planendtime = planendtime;
    }

    public String getSlaStatus() {
        return slaStatus;
    }

    public void setSlaStatus(String slaStatus) {
        this.slaStatus = slaStatus;
    }

    public String getSurplusTime() {
        return surplusTime;
    }

    public void setSurplusTime(String surplusTime) {
        this.surplusTime = surplusTime;
    }

    public String getPriorityName() {
        return priorityName;
    }

    public void setPriorityName(String priorityName) {
        this.priorityName = priorityName;
    }

    public String getStringCreateTime() {
        return stringCreateTime;
    }

    public void setStringCreateTime(String stringCreateTime) {
        this.stringCreateTime = stringCreateTime;
    }
}


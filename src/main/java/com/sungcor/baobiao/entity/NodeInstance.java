package com.sungcor.baobiao.entity;

import org.apache.commons.lang.StringUtils;

/**
 * User: FanLei
 * Date: 2012-04-27
 * Time: 09:28
 * Descript:
 */
public class NodeInstance{

    private Integer id;

    private Integer nodeDefinitionID;

    private String name;

    private String implementerID;

    private String implementGroupID;


    private Integer processInstanceID;

    private ProcessInstance processInstance;

    private String forkJoin;

    private Integer sourceForkNode;

    private int runFlag;

    private int hangFlag;

    private String hangModel;

    private String auditModel;

    private String includedDeal;

    private String dealFlag;

    private int dynamicRoleFlag;

    private int performAllFlag;

    private int autoAssignFlag;

    private String autoAssignPloy;

    private String nodeUniqueID ;

    private int decisionFlag;

    private int timerFlag;

    private int hasLinkProcess;

    //是否关联配置项
    public int relatedCI;
    //是否允许退回
    public int sendBack;

    private int hacFlag;

    private String execRoleType;

    private String muchRunFlag;

    private UserInfoBean implementer;


    //是否按区域过滤标识
    private int roleByarea;

    //搁置审批组
    public String asideAuditorGroup;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getNodeDefinitionID() {
        return nodeDefinitionID;
    }

    public void setNodeDefinitionID(Integer nodeDefinitionID) {
        this.nodeDefinitionID = nodeDefinitionID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImplementerID() {
        return implementerID;
    }

    public void setImplementerID(String implementerID) {
        this.implementerID = implementerID;
    }

    public String getImplementGroupID() {
        return implementGroupID;
    }

    public void setImplementGroupID(String implementGroupID) {
        this.implementGroupID = implementGroupID;
    }

    public int getProcessInstanceID() {
        return processInstanceID;
    }

    public void setProcessInstanceID(int processInstanceID) {
        this.processInstanceID = processInstanceID;
    }

    public ProcessInstance getProcessInstance() {
        return processInstance;
    }

    public void setProcessInstance(ProcessInstance processInstance) {
        this.processInstance = processInstance;
    }

    public String getForkJoin() {
        return forkJoin;
    }

    public void setForkJoin(String forkJoin) {
        this.forkJoin = forkJoin;
    }

    public int getSourceForkNode() {
        return sourceForkNode;
    }

    public void setSourceForkNode(int sourceForkNode) {
        this.sourceForkNode = sourceForkNode;
    }

    public int getRunFlag() {
        return runFlag;
    }

    public void setRunFlag(int runFlag) {
        this.runFlag = runFlag;
    }

    public int getHangFlag() {
        return hangFlag;
    }

    public void setHangFlag(int hangFlag) {
        this.hangFlag = hangFlag;
    }

    public String getAuditModel() {
        return auditModel;
    }

    public void setAuditModel(String auditModel) {
        this.auditModel = auditModel;
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

    public String getHangModel() {
        return hangModel;
    }

    public void setHangModel(String hangModel) {
        this.hangModel = hangModel;
    }

    public int getDynamicRoleFlag() {
        return dynamicRoleFlag;
    }

    public void setDynamicRoleFlag(int dynamicRoleFlag) {
        this.dynamicRoleFlag = dynamicRoleFlag;
    }

    public int getAutoAssignFlag() {
        return autoAssignFlag;
    }

    public void setAutoAssignFlag(int autoAssignFlag) {
        this.autoAssignFlag = autoAssignFlag;
    }

    public String getAutoAssignPloy() {
        return autoAssignPloy;
    }

    public void setAutoAssignPloy(String autoAssignPloy) {
        this.autoAssignPloy = autoAssignPloy;
    }

    public String getNodeUniqueID() {
        return nodeUniqueID;
    }

    public void setNodeUniqueID(String nodeUniqueID) {
        this.nodeUniqueID = nodeUniqueID;
    }

    public String getMuchRunFlag() {
        return muchRunFlag;
    }

    public void setMuchRunFlag(String muchRunFlag) {
        this.muchRunFlag = muchRunFlag;
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

    public UserInfoBean getImplementer() {
        return implementer;
    }

    public void setImplementer(UserInfoBean implementer) {
        this.implementer = implementer;
    }

    public int getRelatedCI() {
        return relatedCI;
    }

    public void setRelatedCI(int relatedCI) {
        this.relatedCI = relatedCI;
    }

    public int getSendBack() {
        return sendBack;
    }

    public void setSendBack(int sendBack) {
        this.sendBack = sendBack;
    }

    public int getRoleByarea() {
        return roleByarea;
    }

    public void setRoleByarea(int roleByarea) {
        this.roleByarea = roleByarea;
    }

    public String getAsideAuditorGroup() {
        return asideAuditorGroup;
    }

    public void setAsideAuditorGroup(String asideAuditorGroup) {
        this.asideAuditorGroup = asideAuditorGroup;
    }

    public int getPerformAllFlag() {
        return performAllFlag;
    }

    public void setPerformAllFlag(int performAllFlag) {
        this.performAllFlag = performAllFlag;
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

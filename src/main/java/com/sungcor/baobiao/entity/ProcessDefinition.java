package com.sungcor.baobiao.entity;

import com.sungcor.baobiao.utils.DateUtil;
import org.apache.commons.lang.StringUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ProcessDefinition {
    private Integer id;

    //id字符串 用于oracle映射返回id值
    private String idstr;

    //关联流程定义ID
    private Integer processID;

    private Process qbpmProcess;

    private Integer version;

    /**
     * '是否锁定，0为未锁定，1为锁定'
     */
    private Integer lock;

    private String creatorID;

    private UserInfoBean creator;

    private UserInfoBean updateor;

    private Date createTime;
    //流程模板id
    private int processtempId;

    private String updateorID;

    private Date updateTime;

    private String updateTimeStr;

    public String getUpdateTimeStr() {
        return updateTimeStr;
    }

    public void setUpdateTimeStr(String updateTimeStr) {
        this.updateTimeStr = updateTimeStr;
    }

    /**
     * '是否启用，0为未启用，1为启用'
     */
    private Integer using;


    //对应JBPM流程定义ID
    private String jbpmProcessDefID;

    //对应JBPM流程部署ID
    private String jbpmProcessDeployID;

    //对应JBPM流程KEY
    private String jbpmProcessKEY;

    private String lockedStaff;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }


    public Process getQbpmProcess() {
        return qbpmProcess;
    }

    public void setQbpmProcess(Process qbpmProcess) {
        this.qbpmProcess = qbpmProcess;
    }

    public int getProcesstempId() {
        return processtempId;
    }

    public void setProcesstempId(int processtempId) {
        this.processtempId = processtempId;
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




    public String getUpdateorID() {
        return updateorID;
    }

    public void setUpdateorID(String updateorID) {
        this.updateorID = updateorID;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {

        this.updateTime = updateTime;
        if (updateTime != null) {
            this.updateTimeStr = DateUtil.parse(updateTime, DateUtil.DEFAULT_FORMAT);
        }
    }

    public Integer getProcessID() {
        return processID;
    }

    public void setProcessID(Integer processID) {
        this.processID = processID;
    }

    public Integer getUsing() {
        return using;
    }

    public void setUsing(Integer using) {
        this.using = using;
    }

    public Integer getLock() {
        return lock;
    }

    public void setLock(Integer lock) {
        this.lock = lock;
    }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    public String getJbpmProcessDefID() {
        return jbpmProcessDefID;
    }

    public void setJbpmProcessDefID(String jbpmProcessDefID) {
        this.jbpmProcessDefID = jbpmProcessDefID;
    }

    public String getJbpmProcessKEY() {
        return jbpmProcessKEY;
    }

    public void setJbpmProcessKEY(String jbpmProcessKEY) {
        this.jbpmProcessKEY = jbpmProcessKEY;
    }

    public ProcessDefinition clone() {
        ProcessDefinition o = null;
        try {
            o = (ProcessDefinition) super.clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
        return o;
    }

    public String getLockedStaff() {
        return lockedStaff;
    }

    public void setLockedStaff(String lockedStaff) {
        this.lockedStaff = lockedStaff;
    }

    public UserInfoBean getUpdateor() {
        return updateor;
    }

    public void setUpdateor(UserInfoBean updateor) {
        this.updateor = updateor;
    }

    public String getJbpmProcessDeployID() {
        return jbpmProcessDeployID;
    }

    public void setJbpmProcessDeployID(String jbpmProcessDeployID) {
        this.jbpmProcessDeployID = jbpmProcessDeployID;
    }

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

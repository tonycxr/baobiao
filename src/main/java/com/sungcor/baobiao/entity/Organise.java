package com.sungcor.baobiao.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Organise {
    private Integer id;

    private String name;

    private String description;

    //组织编码
    private String code;

    //上级组织id
    private Integer parentOrgID;
    //上级组织名称
    private String parentOrgName;
    //所属管理部门（组织）id
    private Integer  governingUnitID;
    //所属管理部门（组织）名称
    private String governingUnitName;

    //创建人
    private String createUser;
    //修改人
    private String modifyUser;
    //创建时间
    private Date createTime;
    //修改时间
    private Date modifyTime;
    //排序号，同级下有效
    private Integer sort;

    private Integer userOrgID;


    private List<Organise> childrenOrgs =new ArrayList<Organise>();

    private Organise managedOrg;

    private String thirdorganizationId;

    //该组织在树上是否属于当前操作人管理
    private int operFlag;
    //该组织所处于层级
    private int level;

    private String canUse;
    private String canQuery;
    public String getCanUse() {
        return canUse;
    }
    public void setCanUse(String canUse) {
        this.canUse = canUse;
    }
    public String getCanQuery() {
        return canQuery;
    }
    public void setCanQuery(String canQuery) {
        this.canQuery = canQuery;
    }
    public static Organise organization2organise(Organization organization){
        Organise organise=new Organise();
        organise.setName(organization.getName());
        organise.setCode(organization.getCode());
        organise.setGoverningUnitID(organization.getGoverningunit()==null?-1:Integer.valueOf(organization.getGoverningunit()));
        organise.setDescription(organization.getDescription());
        organise.setParentOrgID(organization.getParentid()==null?-1:Integer.valueOf(organization.getParentid()));
        organise.setSort(organization.getSort()==null?0:Integer.valueOf(organization.getSort()));
        return organise;
    }
    public static Organization organise2organization(Organise organise){
        Organization organization=new Organization();
        organization.setId(String.valueOf(organise.getId()));
        organization.setName(organise.getName());
        organization.setCode(organise.getCode());
        organization.setDescription(organise.getDescription());
        organization.setParentid(String.valueOf(organise.getParentOrgID()));
        organization.setGoverningunit(String.valueOf(organise.getGoverningUnitID()));
        organization.setSort(String.valueOf(organise.getSort()));
        return organization;
    }
    public Integer getParentOrgID() {
        return parentOrgID;
    }

    public void setParentOrgID(Integer parentOrgID) {
        this.parentOrgID = parentOrgID;
    }

    public String getParentOrgName() {
        return parentOrgName;
    }

    public void setParentOrgName(String parentOrgName) {
        this.parentOrgName = parentOrgName;
    }

    public Integer getGoverningUnitID() {
        return governingUnitID;
    }

    public void setGoverningUnitID(Integer governingUnitID) {
        this.governingUnitID = governingUnitID;
    }

    public String getGoverningUnitName() {
        return governingUnitName;
    }

    public void setGoverningUnitName(String governingUnitName) {
        this.governingUnitName = governingUnitName;
    }


    public String getCreateUser() {
        return createUser;
    }

    public void setCreateUser(String createUser) {
        this.createUser = createUser;
    }

    public String getModifyUser() {
        return modifyUser;
    }

    public void setModifyUser(String modifyUser) {
        this.modifyUser = modifyUser;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getModifyTime() {
        return modifyTime;
    }

    public void setModifyTime(Date modifyTime) {
        this.modifyTime = modifyTime;
    }

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }

    public Organise getManagedOrg() {
        return managedOrg;
    }

    public void setManagedOrg(Organise managedOrg) {
        this.managedOrg = managedOrg;
    }

    public int getOperFlag() {
        return operFlag;
    }

    public void setOperFlag(int operFlag) {
        this.operFlag = operFlag;
    }

    public List<Organise> getChildrenOrgs() {
//        if (null == this.childrenOrgs)
//            childrenOrgs = new ArrayList<Organise>();
        return childrenOrgs;
    }

    public void setChildrenOrgs(List<Organise> childrenOrgs) {
        this.childrenOrgs = childrenOrgs;
    }

    public Integer getUserOrgID() {
        return userOrgID;
    }

    public void setUserOrgID(Integer userOrgID) {
        this.userOrgID = userOrgID;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public String getThirdorganizationId() {
        return thirdorganizationId;
    }

    public void setThirdorganizationId(String thirdorganizationId) {
        this.thirdorganizationId = thirdorganizationId;
    }


    @Override
    public int hashCode() {
        final int prime = 31;
        int result = super.hashCode();
        result = prime * result
                + ((parentOrgID == null) ? 0 : parentOrgID.hashCode());
        return result;
    }
    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (!super.equals(obj))
            return false;
        if (getClass() != obj.getClass())
            return false;
        Organise other = (Organise) obj;
        if (parentOrgID == null) {
            if (other.parentOrgID != null)
                return false;
        } else if (!parentOrgID.equals(other.parentOrgID))
            return false;
        return true&&super.equals(obj);
    }
    @Override
    public String toString() {
        return "Organise [code=" + code + ", parentOrgID=" + parentOrgID
                + ", parentOrgName=" + parentOrgName + ", id=" + id
                + ", name=" + name + "]";
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}

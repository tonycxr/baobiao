package com.sungcor.baobiao.service.Impl;


import com.sungcor.baobiao.STSMConstant;
import com.sungcor.baobiao.entity.Organise;
import com.sungcor.baobiao.entity.UserInfoBean;
import com.sungcor.baobiao.mapper.OrganiseMapper;
import com.sungcor.baobiao.service.IOrganiseService;
import com.sungcor.baobiao.utils.UtilTools;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * Created by IntelliJ IDEA. User: fanlei Date: 2012-4-17 Time: 14:35:29 To
 * change this template use File | Settings | File Templates.
 */


@Service
public class OrganiseServiceImpl implements IOrganiseService {

	@Autowired
	private OrganiseMapper organiseMapper;


//    @Resource(name = "regApplicationService")
//    private IRegApplicationService regApplicationService;

	public List<Organise> initOrganiseTreeDate() {
		List<Organise> topOrgList = this.loadTopOrg();
		for (Organise org : topOrgList) {
			this.initChildrenOrg(org);
		}
		return topOrgList;
	}



    public List<Organise> initOrganiseTreeParentIDDate(int level) {
        List<Organise> topOrgList = this.loadTopOrgParentID(level);
        for (Organise org : topOrgList) {
            org.setLevel(level);
            this.initChildrenOrg(org,level);
        }
        return topOrgList;
    }


    public List<Organise> initOrganiseTreeDate(int level) {
        List<Organise> topOrgList = this.loadTopOrg();
        for (Organise org : topOrgList) {
            org.setLevel(level);
            this.initChildrenOrg(org,level);
        }
        return topOrgList;
    }

    public List<Organise> initOrganiseTreeDate(Integer orgid) {
        List<Organise> topOrgList = this.loadTopOrg(orgid);
        for (Organise org : topOrgList) {

            this.initChildrenOrg(org);
        }
        return topOrgList;
    }

    public List<Organise> initOrganiseTreeDate(Integer orgid, int level) {
        List<Organise> topOrgList = this.loadTopOrg(orgid);
        for (Organise org : topOrgList) {
            org.setLevel(level);
            this.initChildrenOrg(org,level);
        }
        return topOrgList;
    }

	public List<Organise> initOrganiseTreeDate(UserInfoBean operUser) {
		List<Organise> topOrgList = this.loadTopOrg();
		for (Organise org : topOrgList) {

			/*if (org.getId() == operUser.getOrganizationId()) {
				org.setUserOrgID(operUser.getOrganizationId());
				org.setOperFlag(1);
			} else if (operUser.getOrganizationId() == -1
                    ||("-1").equals(operUser.getUserId())) // admin管理员权限*/
		     org.setOperFlag(1);
			this.initChildrenOrg(org, operUser);
		}
		return topOrgList;
	}

    public List<Organise> initOrganiseTreeDate(UserInfoBean operUser,int parentOrgID){
        List<Organise> topOrgList =new ArrayList<Organise>();
        if(parentOrgID==0 || parentOrgID==-1 ){
            topOrgList=organiseMapper.getOrganiseListByParentID(-1);
        }else{
            topOrgList = this.loadTopOrg(parentOrgID);
        }
        for (Organise org : topOrgList) {

            /*if (org.getId() == operUser.getOrganizationId()) {
                org.setUserOrgID(operUser.getOrganizationId());
                org.setOperFlag(1);
            } else if (operUser.getOrganizationId() == -1||("-1").equals(operUser.getUserId()))*/ // admin管理员权限
                org.setOperFlag(1);

            org.setChildrenOrgs(organiseMapper.getOrganiseListByParentID(org.getId()));
           // this.initChildrenOrg(org, operUser,parentOrgID);
        }
        return topOrgList;
    }

	public int getChildrenOrganiseCount(Map map) {
		Map<String, Object> resultMap = this.organiseMapper
				.getOrganiseCountByParentID(map);
		if (resultMap.size() == 0) {
			return -1;
		}
		return Integer.parseInt(resultMap.get("organiseCount").toString());
	}

	public List<Organise> listChildrenOrganises(Map map) {
		return this.organiseMapper.getChildrenOrganises(map);
	}

	public Organise insertOrganise(Organise organise)  throws Exception{
		Map hp = new HashMap();
		hp.put("parentID", organise.getParentOrgID());
		Map<String, Object> resultMap = this.organiseMapper
                .getMaxOrganiseSortByParentID(hp);
		if (null != resultMap) {
			if (resultMap.size() == 0) {
				organise.setSort(0);
			} else
				organise.setSort(Integer.parseInt(resultMap.get("organiseMaxSort").toString()) + 1);

		} else{
			organise.setSort(0);}
        organise.setCreateTime(new Date());
		this.organiseMapper.insertOrganise(organise);
//		deleteCacheId();
        List<Organise> organiseList = this.organiseMapper.getOrgsByCode(organise.getCode());
        if(organiseList!=null && organiseList.size()>=1){

//			SynchronizationOrganizationTask task = new SynchronizationOrganizationTask(SynchronizationOrganizationTask.ADD_OPERATE, organise.getId().toString(),organise.getParentOrgID().toString());
//			task.start();
           return organiseList.get(0);
        }
        return null;

	}

    @Override
    public Boolean checkInsert(String code) {
        List<Organise> orgs = this.organiseMapper.getOrgsByCode(code);
        if(orgs==null||orgs.size()==0)
            return true;
        else
            return false;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public Organise getOrganiseByID(Integer id) {
		List<Organise> orgs = this.organiseMapper.getOrganiseById(id);
		Organise ret;
		if (orgs.size() > 0) {
			ret = orgs.get(0);
			if (null != ret.getGoverningUnitID()) { // 设置管理部门
				List<Organise> govUnit = this.organiseMapper.getOrganiseById(ret
						.getGoverningUnitID());
				if (govUnit.size() > 0)
					ret.setGoverningUnitName(govUnit.get(0).getName());
			}
		} else
			ret = null;
		return ret;
	}

	public void updateOrganise(Organise organise)  throws Exception{
		this.organiseMapper.updateOrganiseByID(organise);

	}

	public boolean updatePositionOrganise(String nodeID, String tragetNodeID,
			String parentNode, String targetParentNode, String nodeSort,
			String targetNodeSort, String moveType) {
		// moveType "inner"：成为子节点，"prev"：成为同级前一个节点，"next"：成为同级后一个节点
		Organise node = this.organiseMapper.getOrganiseById(
				Integer.parseInt(nodeID)).get(0);
		Organise targetNode = this.organiseMapper.getOrganiseById(
				Integer.parseInt(tragetNodeID)).get(0);
		// 同一个父节点内移动
		if (parentNode.equals(targetParentNode)) {
			if (moveType.equalsIgnoreCase("inner")) {
				// 移动到同级组织内部作为同级目标组织子组织
				Organise org = new Organise();
				org.setParentOrgID(Integer.parseInt(tragetNodeID));
				org.setId((Integer.parseInt(nodeID)));
				Map hp = new HashMap();
				hp.put("parentID", tragetNodeID);
				Map<String, Object> resultMap = this.organiseMapper
						.getMaxOrganiseSortByParentID(hp);
				if (null != resultMap) {
					if (resultMap.size() == 0) {
						org.setSort(0);
					} else
						org
								.setSort(((Integer) resultMap
										.get("organiseMaxSort")).intValue() + 1);

				} else
					org.setSort(0);
				this.organiseMapper.updateOrganiseByID(org);
				return true;
			} else if (moveType.equalsIgnoreCase("prev")) { // 移动到同级目标组织节点前

				this.insertBefore(Integer.parseInt(nodeID), Integer
						.parseInt(parentNode), node.getSort(), targetNode
						.getSort());
				return true;
			} else if (moveType.equalsIgnoreCase("next")) {
				// 移动到同级目标组织节点后
				// int targetSort =
				// this.organiseMapper.getOrganiseById(Integer.parseInt(tragetNodeID))
				// .get(0) .getSort();
				this.insertAfter(Integer.parseInt(nodeID), Integer
						.parseInt(parentNode), node.getSort(), targetNode
						.getSort());

				return true;
			}
		} else { // 不同同父节点之间移动
			if (moveType.equalsIgnoreCase("inner")) {
				// 移动到不同级组织内部作为目标组织子组织
				// 移动到同级组织内部作为同级目标组织子组织
				Organise org = new Organise();
				org.setParentOrgID(Integer.parseInt(tragetNodeID));
				org.setId((Integer.parseInt(nodeID)));
				Map hp = new HashMap();
				hp.put("parentID", tragetNodeID);

				Map<String, Object> resultMap = this.organiseMapper
						.getMaxOrganiseSortByParentID(hp);
				if (null != resultMap) {
					if (resultMap.size() == 0) {
						org.setSort(0);
					} else
						org
								.setSort(((Integer) resultMap
										.get("organiseMaxSort")).intValue() + 1);

				} else
					org.setSort(0);
				this.organiseMapper.updateOrganiseByID(org);
				return true;
			} else if (moveType.equalsIgnoreCase("prev")) {
				// 移动到不同级目标组织节点前
				this.insertBeforeAnotherOrg(Integer.parseInt(nodeID), Integer
						.parseInt(tragetNodeID), Integer.parseInt(parentNode),
						Integer.parseInt(targetParentNode), node.getSort(),
						targetNode.getSort());

				return true;
			} else if (moveType.equalsIgnoreCase("next")) {
				// 移动到不同级目标组织节点后
				this.insertAfterAnotherOrg(Integer.parseInt(nodeID), Integer
						.parseInt(tragetNodeID), Integer.parseInt(parentNode),
						Integer.parseInt(targetParentNode), node.getSort(),
						targetNode.getSort());
				return true;
			}
		}
		return false;
	}

	/**
	 * 在不同级节点之前插入
	 *
	 * @param nodeID
	 * @param parentID
	 * @param sort
	 * @param targetSort
	 */
	private void insertBeforeAnotherOrg(int nodeID, int targetNode,
			int parentID, int targetParentID, int sort, int targetSort) {
		Map param = new HashMap();
		param.put("parentID", targetParentID);
		param.put("sortID", sort);
		param.put("targetSortID", targetSort);

		this.organiseMapper.updateSortBeforeAntoherOrg(param);
		Organise org = new Organise();
		org.setParentOrgID(targetParentID);
		org.setId(nodeID);
		org.setSort(targetSort);
		this.organiseMapper.updateOrganiseByID(org);

	}

	/**
	 * 在不同级节点之后插入
	 *
	 * @param nodeID
	 * @param parentID
	 * @param sort
	 * @param targetSort
	 */
	private void insertAfterAnotherOrg(int nodeID, int targetNode,
			int parentID, int targetParentID, int sort, int targetSort) {
		Map param = new HashMap();
		param.put("parentID", targetParentID);
		param.put("sortID", sort);
		param.put("targetSortID", targetSort);

		this.organiseMapper.updateSortAfterAntoherOrg(param);
		Organise org = new Organise();
		org.setId(nodeID);
		org.setParentOrgID(targetParentID);
		org.setSort(targetSort + 1);
		this.organiseMapper.updateOrganiseByID(org);

	}

	/**
	 * 在同级节点之前插入
	 *
	 * @param nodeID
	 * @param parentID
	 * @param sort
	 * @param targetSort
	 */
	private void insertBefore(int nodeID, int parentID, int sort, int targetSort) {
		Map param = new HashMap();
		param.put("parentID", parentID);
		param.put("sortID", sort);
		param.put("targetSortID", targetSort);

		if (sort > targetSort) {
			this.organiseMapper.updateSortBefore1(param);
			Organise org = new Organise();
			org.setId(nodeID);
			org.setSort(targetSort);
			this.organiseMapper.updateOrganiseByID(org);
		} else {
			this.organiseMapper.updateSortBefore2(param);
			Organise org = new Organise();
			org.setId(nodeID);
			org.setSort(targetSort - 1);
			this.organiseMapper.updateOrganiseByID(org);
		}
	}

	/**
	 * 在同级节点之前插入
	 *
	 * @param nodeID
	 * @param parentID
	 * @param sort
	 * @param targetSort
	 */
	private void insertAfter(int nodeID, int parentID, int sort, int targetSort) {
		Map param = new HashMap();
		param.put("parentID", parentID);
		param.put("sortID", sort);
		param.put("targetSortID", targetSort);
		if (sort > targetSort) {
			this.organiseMapper.updateSortAfter1(param);
			Organise org = new Organise();
			org.setId((nodeID));
			org.setSort(targetSort + 1);
			this.organiseMapper.updateOrganiseByID(org);
		} else {
			this.organiseMapper.updateSortAfter2(param);
			Organise org = new Organise();
			org.setId(nodeID);
			org.setSort(targetSort);
			this.organiseMapper.updateOrganiseByID(org);
		}
	}

	public void deleteOrganiseByID(int id) {
		List<Organise> orgs = this.organiseMapper.getOrganiseById(id);
		String parent = STSMConstant.STR_EMPTY;
		if(!orgs.isEmpty()){
			parent = orgs.get(STSMConstant.NUM_ZERO).getParentOrgID().toString();
		}
//		SynchronizationOrganizationTask task = new SynchronizationOrganizationTask(SynchronizationOrganizationTask.DELETE_OPERATE, Integer.toString(id),parent);
		//		task.start();
		this.organiseMapper.deleteOrganiseByID(id);
//		deleteCacheId();
	}

	public Map checkDelete(String ids) {

		String ids1[] = ids.split(",");
		Integer[] ids2 = new Integer[ids1.length];
		for (int i = 0; i < ids1.length; i++) {
			ids2[i] = Integer.parseInt(ids1[i]);
		}
		List<HashMap> chindrenOrgRelatedMapList = this.organiseMapper
				.getRelatedChildrenOrgs(ids2);
		List<HashMap> userRelatedMapList = this.organiseMapper
				.getRelatedUserOrgs(ids2);
		Map retMap = new HashMap();
		retMap.put("corm", chindrenOrgRelatedMapList);
		retMap.put("urm", userRelatedMapList);
		return retMap;
	}

	/**
	 * 递归加载子组织
	 *
	 * @param org
	 * @param operUser
	 */
	private void initChildrenOrg(Organise org, UserInfoBean operUser) {
		List<Organise> childrenList = this.organiseMapper
				.getOrganiseListByParentID(org.getId());
		org.setChildrenOrgs(childrenList);
		// 如果当前父组织能被操作则设置所有子组织也能被操作
		if (org.getOperFlag() == 1) {
			for (Organise corg : org.getChildrenOrgs()) {
				corg.setOperFlag(1);
				corg.setUserOrgID(org.getUserOrgID());
			}
		} else {
			for (Organise corg : org.getChildrenOrgs()) {
				if (corg.getId() == operUser.getOrganizationId()) {
					corg.setOperFlag(1);
					corg.setUserOrgID(operUser.getOrganizationId());
				}
			}
		}
		if (childrenList.size() > 0) {
			for (Organise childrenOrg : childrenList) {
				initChildrenOrg(childrenOrg, operUser);
			}
		}
	}
    /**
     * 递归加载子组织 不包含当前组织
     *
     * @param org
     * @param operUser
     * @param parentOrgID
     */
    private void initChildrenOrg(Organise org, UserInfoBean operUser,int parentOrgID) {
      //  if(org.getId() != parentOrgID && -1 != parentOrgID){
        List<Organise> childrenList = this.organiseMapper.getOrganiseListByParentID(org.getId());
        org.setChildrenOrgs(childrenList);
        // 如果当前父组织能被操作则设置所有子组织也能被操作
        if (org.getOperFlag() == 1) {
            for (Organise corg : org.getChildrenOrgs()) {
                corg.setOperFlag(1);
                corg.setUserOrgID(org.getUserOrgID());
            }
        } else {
            for (Organise corg : org.getChildrenOrgs()) {
                if (corg.getId() == operUser.getOrganizationId()) {
                    corg.setOperFlag(1);
                    corg.setUserOrgID(operUser.getOrganizationId());
                }
            }
        }
        if (childrenList.size() > 0) {
            for (Organise childrenOrg : childrenList) {
                if(childrenOrg.getId() == parentOrgID){
                   continue;
                }
                initChildrenOrg(childrenOrg, operUser);
            }
        }
      //  }

    }

	/**
	 * 递归加载子组织
	 *
	 * @param org
	 */
	private void initChildrenOrg(Organise org) {
		List<Organise> childrenList = this.organiseMapper
				.getOrganiseListByParentID(org.getId());
		org.setChildrenOrgs(childrenList);
		if (childrenList.size() > 0) {
			for (Organise childrenOrg : childrenList) {
				initChildrenOrg(childrenOrg);
			}
		}
	}

    private void initChildrenOrg(Organise org,int level) {
        List<Organise> childrenList = this.organiseMapper
                .getOrganiseListByParentID(org.getId());
        org.setChildrenOrgs(childrenList);
        if (childrenList.size() > 0) {
            for (Organise childrenOrg : childrenList) {
                childrenOrg.setLevel(level+1);
                initChildrenOrg(childrenOrg, level+1);
            }
        }
    }

	/**
	 * 查询第一级组织
	 *
	 * @return
	 */
	public List<Organise> loadTopOrg() {
		List<Organise> topOrgList = organiseMapper.getAll();
		return topOrgList;
	}

    private List<Organise> loadTopOrgParentID(int id) {
        List<Organise> topOrgList = organiseMapper.getOrganiseListByParentID(id);
        return topOrgList;
    }


    /**
     * 查询第一级组织
     *
     * @return
     */
    private List<Organise> loadTopOrg(Integer orgid) {
        List<Organise> topOrgList = organiseMapper.getOrganiseById(orgid);
        return topOrgList;
    }


    List<Integer> idList;
	@Override
	public List<Integer> getOrganiseTreeIds(int id) {
		idList = new ArrayList<Integer>();
		idList = getOrgIdsById(id);
//		if(-1 == id){
//			List<Organise> orgs = this.organiseMapper.getAll();
//			for(Organise o : orgs){
//				idList.add(o.getId());
//			}
////			Organise org = this.organiseMapper.getOrganiseById(id).get(0);
////			if(org.getGoverningUnitID().equals(id)){
////				List<Organise> orgs1  =  this.organiseMapper.getOrgsByGovId(id);
////				for(Organise o : orgs1){
////					idList.add(o.getId());
////				}
////			}
//
//		}else{
//			List<Organise> orgs = this.organiseMapper.getOrganiseById(id);
//			Organise org;
//			if (orgs.size() > 0) {
//				org = orgs.get(0);
//				idList.add(org.getId());
//				//查询下级组织
//				initChildrenIds(org);
//				if(null != org.getGoverningUnitID()){
//					if(org.getGoverningUnitID().equals(id)){
//						List<Organise> orgs1  =  this.organiseMapper.getOrgsByGovId(id);
//						for(Organise o : orgs1){
//							idList.add(o.getId());
//						}
//					}
//				}
//			}
//		}
//        idList.add(id);
		return idList;
	}

	private void initChildrenIds(Organise org) {
		List<Organise> childrenList = this.organiseMapper
				.getOrganiseListByParentID(org.getId());
		System.out.println(org.getName()+"-- c:"+childrenList.size());
		org.setChildrenOrgs(childrenList);
		if (childrenList.size() > 0) {
			for (Organise childrenOrg : childrenList) {
				idList.add(childrenOrg.getId());
				initChildrenIds(childrenOrg);
			}
		}
	}

    public List<Organise> getAll(){
        return organiseMapper.getAll();
    }

    @Override
    public List<Organise> findAllChildOrg(int id) {
        return organiseMapper.findAllChildOrg(id);  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public Object getSystemIdBy3rdId(int id) {
        return organiseMapper.getSysIdBy3rdId(id);
    }

    @Override
    public List<Organise> getOrgsByCode(String code) {
        return organiseMapper.getOrgsByCode(code);
    }

    @Override
    public int checkByName(String name) {
        List<Organise> orgs = this.organiseMapper.getOrgsByName(name);
        if(orgs==null||orgs.size()==0)
            return -1;
        else
            return orgs.get(0).getId();
    }

    @Override
    public Organise getRootOrgByCurrentOrgId(int id){
        if (id != -1){
            Organise curOrg = this.getOrganiseByID(id);
            while(!curOrg.getParentOrgID().equals(-1)){
                curOrg = this.getOrganiseByID(curOrg.getParentOrgID());
            }
            return curOrg;
        }else{
            return null;
        }
    }

//	public void deleteCacheId(){
//		IMemcache<Integer> cacheL= MemCacheFactory.getRemoteMemCache(Integer.class);
//		List<Organise> list = getAll();
//		for(Organise organise:list){
//			cacheL.delete("getOrgIdsById_"+organise.getId());
//		}
//
//	}
//
	public List<Integer> getOrgIdsById(int id){
		List<Integer> ids = new ArrayList<Integer>();
//		IMemcache<Integer> cacheL= MemCacheFactory.getRemoteMemCache(Integer.class);
//		if(cacheL.getCollection("getOrgIdsById_"+id)!=null){
//			return cacheL.getCollection("getOrgIdsById_"+id);
//		}else
			ids.add(id);
			ids.addAll(getChildIds(getAll(),id));
//			cacheL.setCollection("getOrgIdsById_"+id,ids);
			return ids;

	}

	public List<Integer> getChildIds(List<Organise> list,int id){
		List<Integer> ids = new ArrayList<Integer>();
		for(Organise organise:list){
			if(organise.getParentOrgID()==id){
				ids.add(organise.getId());
				ids.addAll(getChildIds(list,organise.getId()));
			}
		}
		return ids;
	}

	public String importOrganise(HashMap map) throws Exception {
		HSSFSheet sheet =((HSSFWorkbook)map.get("workBook")).getSheetAt(0);
		HSSFRow row = null;
		HSSFCell cell = null;
		String user=String.valueOf(map.get("user"));
		int rows=sheet.getLastRowNum();
		List<Organise> organiseList=new ArrayList<Organise>();
		int flag=0;
		int errotCount=0;
		int rootCount=0;
		int nullCount=0;
		for(int i=1;i<=rows;i++){
			row=sheet.getRow(i);
			if(row!=null){
				List<Organise> organiseList1=getOrgsByCode(row.getCell(0).getStringCellValue());   //验证系统中是否有重复code
				if(organiseList1.size()>0){
					if(organiseList1.get(0).getId()==1){
						rootCount++;
						continue;
					}else{
						flag=1;
						errotCount++;
						continue;
					}
				}
				if("".equals(row.getCell(0).getStringCellValue())&&"".equals(row.getCell(1).getStringCellValue())&&"".equals(row.getCell(2).getStringCellValue())&&"".equals(row.getCell(3).getStringCellValue())){
					nullCount++;
					continue;
				}
				//验证必填项是否为空
				if(row.getCell(0)==null||row.getCell(1)==null||"".equals(row.getCell(0).getStringCellValue())||"".equals(row.getCell(1).getStringCellValue())){
					flag=2;
					errotCount++;
					continue;
				}
				String code=row.getCell(0).getStringCellValue();
				boolean charFlag=code.matches("^[A-Za-z0-9]+$");
				if(!charFlag){
					flag=2;
					errotCount++;
					continue;
				}
				Organise organise=new Organise();
				organise.setCode(row.getCell(0).getStringCellValue());
				organise.setName(row.getCell(1).getStringCellValue());
				organise.setDescription(row.getCell(3)==null?"":row.getCell(3).getStringCellValue());
				organise.setCreateUser(user);
				String createTime= UtilTools.fmtDate(new Date(), "");
				organise.setCreateTime(UtilTools.getTime(createTime,""));
				organise.setSort(0);
				if("".equals(row.getCell(2).getStringCellValue())){   //父编码为空为一级组织
					organise.setParentOrgID(1);
				}else{
					int count=0;
					int rcount=0;
					int index=0;
					int scount=0;
					for(int j=0;j<organiseList.size();j++){    //验证模版中是否有存在该上级code
						if(organiseList.get(j).getCode().equals(row.getCell(2).getStringCellValue())){
							count++;
							index=j;
						}
						if(organiseList.get(j).getCode().equals(row.getCell(0).getStringCellValue())){  //验证模版中是否有重复code
							rcount++;
						}
					}
					if(getOrgsByCode(row.getCell(2).getStringCellValue()).size()>0){   //验证上级code在系统中是否存在
						scount++;
					}
					if(count>1||rcount>0){
						flag=1;
						errotCount++;
						continue;
					}else if(count==0&&scount==0){   //导入项存在上级code错误的情况(模版与系统中都找不到)
						flag=2;
						errotCount++;
						continue;
					}else{
						List<Organise> organiseList2=getOrgsByCode(row.getCell(2).getStringCellValue());
						if(organiseList2!=null&&organiseList2.size()>0){
							organise.setParentOrgID(organiseList2.get(0).getId());
						}else if(organiseList!=null&&organiseList.size()>0){
							organise.setParentOrgID(organiseList.get(index).getId());
						}else{
							flag=2;
							errotCount++;
							continue;
						}
					}
				}
				organise=insertOrganise(organise);
				organiseList.add(organise);        //存插入后的区域信息
			}
		}
		if((rows-nullCount-rootCount)==errotCount){    //数据全部错误的情况
			return "导入失败!请检查数据正确性后重试。";
		}
		if(flag!=0){
			return "导入成功!模版中存在"+errotCount+"条异常信息未导入。<br/><div><font color=\"red\">异常信息指信息存在编码重复，必填项未填，上级编码错误，编码为非数字和字母等情况。</font></div>";
		}
		return "导入成功!";
	}

	public List<Organise> getOrganiseByName(String name){
		List<Organise> list=organiseMapper.getOrgsByName(name);
		return list;
	}
}

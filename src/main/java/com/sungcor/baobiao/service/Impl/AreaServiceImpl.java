package com.sungcor.baobiao.service.Impl;

import com.sungcor.baobiao.dao.IAreaDAO;
import com.sungcor.baobiao.entity.Area;
import com.sungcor.baobiao.service.IAreaService;
import com.sungcor.baobiao.utils.UtilTools;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import java.util.*;

@Transactional
@Service
public class AreaServiceImpl implements IAreaService {

    private IAreaDAO areaDao;

    @SuppressWarnings("unchecked")
    @Override
    public Map checkDelete(String ids) {
        String ids1[] = ids.split(",");
        Integer[] ids2 = new Integer[ids1.length];
        for (int i = 0; i < ids1.length; i++) {
            ids2[i] = Integer.parseInt(ids1[i]);
        }
        List<HashMap> chindrenOrgRelatedMapList = this.areaDao
                .getRelatedChildrenAreas(ids2);
        Map retMap = new HashMap();
        retMap.put("corm", chindrenOrgRelatedMapList);
        return retMap;
    }

    @Override
    public void deleteAreaByID(int id) {
        this.areaDao.deleteAreaByID(id);
    }

    @Override
    public Area getAreaByID(Integer id) {
        return areaDao.getAreasById(id);
    }

    @SuppressWarnings("unchecked")
    @Override
    public int getChildrenAreaCount(Map map) {
        Map<String, Object> resultMap = this.areaDao
                .getAreaCountByParentID(map);
        if (resultMap.size() == 0) {
            return -1;
        }
        return Integer.parseInt((resultMap.get("areaCount")).toString());
    }

    @SuppressWarnings("unchecked")
    @Override
    public int getAreaCountByCode(Map map) {
        Map<String, Object> resultMap = this.areaDao
                .getAreaCountByCode(map);
        if (resultMap.size() == 0) {
            return -1;
        }
        return Integer.parseInt((resultMap.get("areaCount")).toString());
    }

    @SuppressWarnings("unchecked")
    @Override
    public int getAreaCountByName(Map map) {
        Map<String, Object> resultMap = this.areaDao
                .getAreaCountByName(map);
        if (resultMap.size() == 0) {
            return -1;
        }
        return Integer.parseInt((resultMap.get("areaCount")).toString());
    }
    @SuppressWarnings("unchecked")
    @Override
    public int getAreaUpdateCountByName(Map map){
        Map<String, Object> resultMap = this.areaDao
                .getAreaUpdateCountByName(map);
        if (resultMap.size() == 0) {
            return -1;
        }
        return Integer.parseInt((resultMap.get("areaCount")).toString());
    }

    @SuppressWarnings("unchecked")
    @Override
    public void insertArea(Area area) {
        Map hp = new HashMap();
        hp.put("parentId", area.getParentId());
        Map<String, Object> resultMap = this.areaDao
                .getMaxAreaSortByParentID(hp);
        if (null != resultMap) {
            if (resultMap.size() == 0) {
                area.setSort(0);
            } else
                area.setSort((Integer.parseInt(resultMap.get("areaMaxSort").toString()))+1);
        } else {
            area.setSort(0);
        }
        this.areaDao.insertArea(area);
    }

    public Area insertAreaReturn(Area area) {
        Map hp = new HashMap();
        hp.put("parentId", area.getParentId());
        Map<String, Object> resultMap = this.areaDao
                .getMaxAreaSortByParentID(hp);
        if (null != resultMap) {
            if (resultMap.size() == 0) {
                area.setSort(0);
            } else
                area.setSort((Integer.parseInt(resultMap.get("areaMaxSort").toString()))+1);
        } else {
            area.setSort(0);
        }
        this.areaDao.insertArea(area);
        return area;
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<Area> listChildrenAreas(Map map) {
        return this.areaDao.getChildrenAreas(map);
    }

    @Override
    public void updateArea(Area area) {
        this.areaDao.updateAreaByID(area);
    }

    @SuppressWarnings("unchecked")
    @Override
    public boolean updatePositionArea(String nodeID, String tragetNodeID,
                                      String parentNode, String targetParentNode, String nodeSort,
                                      String targetNodeSort, String moveType) {
        // moveType "inner"?????????????????????"prev"?????????????????????????????????"next"??????????????????????????????
        Area node = this.areaDao.getAreasById(Integer.parseInt(nodeID));
        Area targetNode = this.areaDao.getAreasById(Integer
                .parseInt(tragetNodeID));
        // ???????????????????????????
        if (parentNode.equals(targetParentNode)) {
            if (moveType.equalsIgnoreCase("inner")) {
                // ????????????????????????????????????????????????????????????
                Area area = new Area();
                area.setParentId(Integer.parseInt(tragetNodeID));
                area.setId((Integer.parseInt(nodeID)));
                Map hp = new HashMap();
                hp.put("parentId", tragetNodeID);
                Map<String, Object> resultMap = this.areaDao
                        .getMaxAreaSortByParentID(hp);
                if (null != resultMap) {
                    if (resultMap.size() == 0) {
                        area.setSort(0);
                    } else {
                        area.setSort((Integer.parseInt(resultMap.get("areaMaxSort").toString()))+1);
                    }
                } else {
                    area.setSort(0);
                }
                this.areaDao.updateAreaByID(area);
                return true;
            } else if (moveType.equalsIgnoreCase("prev")) { // ????????????????????????????????????
                this.insertBefore(Integer.parseInt(nodeID), Integer
                        .parseInt(parentNode), node.getSort(), targetNode
                        .getSort());
                return true;
            } else if (moveType.equalsIgnoreCase("next")) {
                // ????????????????????????????????????
                // int targetSort =
                // this.organiseDao.getOrganiseById(Integer.parseInt(tragetNodeID))
                // .get(0) .getSort();
                this.insertAfter(Integer.parseInt(nodeID), Integer
                        .parseInt(parentNode), node.getSort(), targetNode
                        .getSort());

                return true;
            }
        } else { // ??????????????????????????????
            if (moveType.equalsIgnoreCase("inner")) {
                // ?????????????????????????????????????????????????????????
                // ????????????????????????????????????????????????????????????
                Area area = new Area();
                area.setParentId(Integer.parseInt(tragetNodeID));
                area.setId((Integer.parseInt(nodeID)));
                Map hp = new HashMap();
                hp.put("parentId", tragetNodeID);
                Map<String, Object> resultMap = this.areaDao
                        .getMaxAreaSortByParentID(hp);
                if (null != resultMap) {
                    if (resultMap.size() == 0) {
                        area.setSort(0);
                    } else
                        area.setSort((Integer.parseInt(resultMap.get("areaMaxSort").toString()))+1);
                } else
                    area.setSort(0);
                this.areaDao.updateAreaByID(area);
                return true;
            } else if (moveType.equalsIgnoreCase("prev")) {
                // ???????????????????????????????????????
                this.insertBeforeAnotherOrg(Integer.parseInt(nodeID), Integer
                                .parseInt(tragetNodeID), Integer.parseInt(parentNode),
                        Integer.parseInt(targetParentNode), node.getSort(),
                        targetNode.getSort());

                return true;
            } else if (moveType.equalsIgnoreCase("next")) {
                // ???????????????????????????????????????
                this.insertAfterAnotherCat(Integer.parseInt(nodeID), Integer
                                .parseInt(tragetNodeID), Integer.parseInt(parentNode),
                        Integer.parseInt(targetParentNode), node.getSort(),
                        targetNode.getSort());
                return true;
            }
        }
        return false;
    }

    /**
     * ????????????
     */
    @Override
    public List<Area> initAreaTreeDate() {
        List<Area> topOrgList = this.loadTopOrg();
        for (Area area : topOrgList) {
            this.initChildrenArea(area);
        }
        return topOrgList;
    }

    /**
     * ?????????????????????
     *
     * @param area
     */
    private void initChildrenArea(Area area) {
        List<Area> childrenList = this.areaDao.getAreaListByParentID(area
                .getId());
        area.setChildrenAreas(childrenList);
        if (childrenList.size() > 0) {
            for (Area childrenArea : childrenList) {
                initChildrenArea(childrenArea);
            }
        }
    }

    /**
     * ?????????????????????
     *
     * @return
     */
    private List<Area> loadTopOrg() {
        List<Area> topOrgList = areaDao.getAreaListByParentID(-1);
        return topOrgList;
    }

    @Override
    public List<Area> initAreaTreeSimpleDate() {
        return areaDao.getAllArea();
    }

    /**
     * ??????????????????????????????
     *
     * @param nodeID
     * @param parentID
     * @param sort
     * @param targetSort
     */
    @SuppressWarnings("unchecked")
    private void insertBeforeAnotherOrg(int nodeID, int targetNode,
                                        int parentID, int targetParentID, int sort, int targetSort) {
        Map param = new HashMap();
        param.put("parentID", targetParentID);
        param.put("sortID", sort);
        param.put("targetSortID", targetSort);

        this.areaDao.updateSortBeforeAntoherArea(param);
        Area area = new Area();
        area.setParentId(targetParentID);
        area.setId(nodeID);
        area.setSort(targetSort);
        this.areaDao.updateAreaByID(area);
    }

    /**
     * ??????????????????????????????
     *
     * @param nodeID
     * @param parentID
     * @param sort
     * @param targetSort
     */
    @SuppressWarnings("unchecked")
    private void insertAfterAnotherCat(int nodeID, int targetNode,
                                       int parentID, int targetParentID, int sort, int targetSort) {
        Map param = new HashMap();
        param.put("parentID", targetParentID);
        param.put("sortID", sort);
        param.put("targetSortID", targetSort);
        this.areaDao.updateSortAfterAntoherArea(param);
        Area area = new Area();
        area.setId(nodeID);
        area.setParentId(targetParentID);
        area.setSort(targetSort + 1);
        this.areaDao.updateAreaByID(area);

    }

    /**
     * ???????????????????????????
     *
     * @param nodeID
     * @param parentID
     * @param sort
     * @param targetSort
     */
    @SuppressWarnings("unchecked")
    private void insertBefore(int nodeID, int parentID, int sort, int targetSort) {
        Map param = new HashMap();
        param.put("parentID", parentID);
        param.put("sortID", sort);
        param.put("targetSortID", targetSort);
        if (sort > targetSort) {
            this.areaDao.updateSortBefore1(param);
            Area area = new Area();
            area.setId(nodeID);
            area.setSort(targetSort);
            this.areaDao.updateAreaByID(area);
        } else {
            this.areaDao.updateSortBefore2(param);
            Area area = new Area();
            area.setId(nodeID);
            area.setSort(targetSort - 1);
            this.areaDao.updateAreaByID(area);
        }
    }

    /**
     * ???????????????????????????
     *
     * @param nodeID
     * @param parentID
     * @param sort
     * @param targetSort
     */
    @SuppressWarnings("unchecked")
    private void insertAfter(int nodeID, int parentID, int sort, int targetSort) {
        Map param = new HashMap();
        param.put("parentID", parentID);
        param.put("sortID", sort);
        param.put("targetSortID", targetSort);
        if (sort > targetSort) {
            this.areaDao.updateSortAfter1(param);
            Area area = new Area();
            area.setId((nodeID));
            area.setSort(targetSort + 1);
            this.areaDao.updateAreaByID(area);
        } else {
            this.areaDao.updateSortAfter2(param);
            Area area = new Area();
            area.setId(nodeID);
            area.setSort(targetSort);
            this.areaDao.updateAreaByID(area);
        }
    }

    @Override
    public List findAppByArea(int areaid) {
        return areaDao.findAppByArea(areaid);
    }

    @Override
    public void saveupdateAppArea(int areaid, int[] applist) {
        if(areaid!=0){
            areaDao.deleteAppsByArea(areaid);
            HashMap param=new HashMap();
            param.put("areaid",areaid);
            if (applist!=null) {
                for(int i:applist){
                    param.put("appid",i);
                    areaDao.saveAppsByArea(param);
                }
            }

        }
    }

    @Override
    public Object getSystemIdBy3rdId(int id) {
        return areaDao.getSysIdBy3rdId(id);
    }

    @Override
    public List<Area> selectAreaList(String areaName) {
        HashMap map = new HashMap();
        map.put("name",areaName);
        return areaDao.selectAreaList(map);
    }

    @Override
    public String importArea(HashMap map){
        HSSFSheet sheet =((HSSFWorkbook)map.get("workBook")).getSheetAt(0);
        HSSFRow row = null;
        HSSFCell cell = null;
        String user=String.valueOf(map.get("user"));
        int rows=sheet.getLastRowNum();
        List<Area> areaList=new ArrayList<Area>();
        int flag=0;
        int errotCount=0;
        int nullCount=0;
        for(int i=1;i<=rows;i++){
            row=sheet.getRow(i);
            if(row!=null){
                List<Area> areaList1=queryAreaCountByCode(row.getCell(0).getStringCellValue());   //??????????????????????????????code
                if(areaList1.size()>0){
                    flag=1;
                    errotCount++;
                    continue;
                }
                if("".equals(row.getCell(0).getStringCellValue())&&"".equals(row.getCell(1).getStringCellValue())&&"".equals(row.getCell(2).getStringCellValue())){
                    nullCount++;
                    continue;
                }
                //???????????????????????????
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
                Area area=new Area();
                area.setCode(row.getCell(0).getStringCellValue());
                area.setName(row.getCell(1).getStringCellValue());
                area.setCreateUser(user);
                String createTime=UtilTools.fmtDate(new Date(),"");
                area.setCreateTime(UtilTools.getTime(createTime,""));
                area.setSort(0);
                if("".equals(row.getCell(2).getStringCellValue())){   //??????????????????????????????
                    area.setParentId(-1);
                }else{
                    int count=0;
                    int rcount=0;
                    int index=0;
                    int scount=0;
                    for(int j=0;j<areaList.size();j++){    //???????????????????????????????????????code
                        if(areaList.get(j).getCode().equals(row.getCell(2).getStringCellValue())){
                            count++;
                            index=j;
                        }
                        if(areaList.get(j).getCode().equals(row.getCell(0).getStringCellValue())){  //??????????????????????????????code
                            rcount++;
                        }
                    }
                    if(queryAreaCountByCode(row.getCell(2).getStringCellValue()).size()>0){   //????????????code????????????????????????
                        scount++;
                    }
                    if(count>1||rcount>0){
                        flag=1;
                        errotCount++;
                        continue;
                    }else if(count==0&&scount==0){   //?????????????????????code???????????????(??????????????????????????????)
                        flag=2;
                        errotCount++;
                        continue;
                    }else{
                        List<Area> areaList2=queryAreaCountByCode(row.getCell(2).getStringCellValue());
                        if(areaList2!=null&&areaList2.size()>0){
                            area.setParentId(areaList2.get(0).getId());
                        }else if(areaList!=null&&areaList.size()>0){
                            area.setParentId(areaList.get(index).getId());
                        }else{
                            flag=2;
                            errotCount++;
                            continue;
                        }
                    }
                }
                area=insertAreaReturn(area);
                areaList.add(area);        //???????????????????????????
            }
        }
        if((rows-nullCount)==errotCount){
            return "????????????!????????????????????????????????????";
        }
        if(flag!=0){
            return "????????????!???????????????"+errotCount+"???????????????????????????<br/><div><font color=\"red\">????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????</font></div>";
        }
        return "????????????!";
    }

    public List<Area> queryAreaCountByCode(String code){
        List<Area> list=areaDao.queryAreaCountByCode(code);
        return list;
    }
}

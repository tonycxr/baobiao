package com.sungcor.baobiao.service;

import com.sungcor.baobiao.entity.EntityObject;
import com.sungcor.baobiao.utils.FormAttributeUtil;
import com.sungcor.baobiao.utils.FormFileUtil;

import java.util.List;

public class EntityObjectProxyService implements IEntityObjectService{
    private IEntityObjectService entityObjectService = null;
    public EntityObjectProxyService(String id) {
        entityObjectService = getEntityObjectInstance(id);
    }

    /**
     *
     * @param id
     * @return
     */
    private IEntityObjectService getEntityObjectInstance(String id) {
        Object obj = null;
        try{
            String path = FormFileUtil.getFormCfgFile();
            FormAttributeUtil util = new FormAttributeUtil(path);
            if(util.isContainNode("entityObject", "local")) {
                obj = util.getObjByXml(id);
            }else {
                return null;
            }
        }catch (Exception e) {
            e.printStackTrace();
        }
        return (IEntityObjectService) obj;
    }

    /**
     *
     * @return
     * @throws Exception
     */
    public List<EntityObject> listAllObj() throws Exception{
        return entityObjectService.listAllObj();
    }

    /**
     *
     * @param id
     * @return
     * @throws Exception
     */
    public EntityObject getObjById(String id) throws Exception{
        if(entityObjectService ==null){
            return null;
        }
        return entityObjectService.getObjById(id);
    }

    /**
     *
     * @param subId
     * @return
     * @throws Exception
     */
    public EntityObject getObjBySubId(String subId) throws Exception {
        return entityObjectService.getObjBySubId(subId);
    }

    /**
     *
     * @param pId
     * @return
     * @throws Exception
     */
    public List<EntityObject> listByParentId(String pId) throws Exception {
        return entityObjectService.listByParentId(pId);
    }

    /**
     *
     * @param condition
     * @return
     * @throws Exception
     */
    public List<EntityObject> listByCondition(String condition) throws Exception {
        return entityObjectService.listByCondition(condition);
    }

}

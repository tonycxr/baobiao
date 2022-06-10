package com.sungcor.baobiao.service;

import com.sungcor.baobiao.entity.EntityObject;

import java.util.List;

public interface IEntityObjectService {
    /**
     *
     * @return
     * @throws Exception
     */
    public List<EntityObject> listAllObj() throws Exception;

    /**
     *
     * @param id
     * @return
     * @throws Exception
     */
    public EntityObject getObjById(String id) throws Exception;

    /**
     *
     * @param subId
     * @return
     * @throws Exception
     */
    public EntityObject getObjBySubId(String subId) throws Exception;

    /**
     *
     * @param pId
     * @return
     * @throws Exception
     */
    public List<EntityObject> listByParentId(String pId) throws Exception;

    /**
     *
     * @param condition
     * @return
     * @throws Exception
     */
    public List<EntityObject> listByCondition(String condition) throws Exception;
}

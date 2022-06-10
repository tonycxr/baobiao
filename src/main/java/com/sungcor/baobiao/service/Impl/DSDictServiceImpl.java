package com.sungcor.baobiao.service.Impl;

import com.sungcor.baobiao.entity.DictItem;
import com.sungcor.baobiao.entity.EntityObject;
import com.sungcor.baobiao.service.IDictItemService;
import com.sungcor.baobiao.service.IEntityObjectService;
import com.sungcor.baobiao.utils.SpringHelper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class DSDictServiceImpl implements IEntityObjectService {

//    private IDictService dictService;

    private IDictItemService dictItemService;

    public List<EntityObject> listAllObj() throws Exception {
        return null;
    }

    @Override
    public EntityObject getObjById(String id) throws Exception {
        return null;
    }

//    public EntityObject getObjById(String id) throws Exception {
//        dictService = (IDictService) SpringHelper.getBean("dict/dictService");
//        dictItemService = (IDictItemService)SpringHelper.getBean("dictItem/dictItemService");
//        HashMap parm = new HashMap();
//        parm.put("id", Integer.parseInt(id));
//        List<DictItem> dictItemList = dictItemService.getDictItems(parm);
//        Dict dict = dictService.getDictById(Integer.parseInt(id));
//        EntityObject entityObj = new EntityObject();
//        List<EntityObject> list = new ArrayList<EntityObject>();
//        entityObj.setId(String.valueOf(dict.getId()));
//        entityObj.setName(dict.getName());
//        for(DictItem dictItem : dictItemList) {
//            EntityObject sub = new EntityObject();
//            sub.setId(String.valueOf(dictItem.getId()));
//            sub.setName(dictItem.getName());
//            list.add(sub);
//        }
//        entityObj.setSubList(list);
//        return entityObj;
//    }

    public EntityObject getObjBySubId(String subId) throws Exception {
        dictItemService = (IDictItemService)SpringHelper.getBean("dictItem/dictItemService");
        DictItem dictItem = dictItemService.getDictItemById(Integer.parseInt(subId));
        EntityObject entityObj = new EntityObject();
        if(null != dictItem && !"".equals(dictItem)) {
            entityObj.setId(String.valueOf(dictItem.getId()));
            entityObj.setName(dictItem.getName());
        }
        return entityObj;
    }

    public List<EntityObject> listByParentId(String pId) throws Exception {
        return null;
    }

    public List<EntityObject> listByCondition(String condition) throws Exception {
        return null;
    }
}

package com.sungcor.baobiao.service.Impl;

import com.sungcor.baobiao.entity.EntityObject;
import com.sungcor.baobiao.entity.FMFormArea;
import com.sungcor.baobiao.entity.FMFormField;
import com.sungcor.baobiao.entity.UserInfoBean;
import com.sungcor.baobiao.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Service
public class OrdersServiceImpl implements IOrdersService {

    @Autowired
    private IFMFormFieldService fieldService;

    @Autowired
    private ISysOrderUserService sysOrderUserService;


    public Map<String, String> setTypeValue(List<FMFormArea> areas, Map<String, String> map, String flag) {
        try {
            Map<String, String> typeValue = new HashMap<String, String>();
            for (FMFormArea area : areas) {
                List<FMFormField> fieldList = fieldService.search(area.getId());
                for (FMFormField field : fieldList) {
                    if ("rpopwin".equals(field.getRenderType())) {
//                        IEntityObjectService objectService = new EntityObjectProxyService(field.getDataSource());
                        if(map.get(field.getName())!=null&&!map.get(field.getName()).equals("")){
                            EntityObject object=null;
                            String userId =map.get(field.getName());
                            if(flag.equals("order")&&field.getDataSource().equals("user")){
                                UserInfoBean userInfoBean = sysOrderUserService.getSysOrderUserById(Integer.parseInt(userId));
                                object = new EntityObject();
                                object.setId(String.valueOf(userInfoBean.getUserId()));
                                object.setName(userInfoBean.getUserName());
                                Map<String,Object> sysOrderUserMap = new LinkedHashMap<String,Object>();
                                map.put("email", userInfoBean.getEmail());
                                map.put("mobile", userInfoBean.getMobile());
                                map.put("phone",userInfoBean.getPhone());
                                object.setAttributeMap(sysOrderUserMap);
                            }else {
                                object = null;
                            }

                            if (null != object && !"".equals(object.getId())) {
                                typeValue.put(field.getName(), object.getName());
                            } else {
                                typeValue.put(field.getName(), map.get(field.getName()));
                            }
                        }
                    } else if ("cpopwin".equals(field.getRenderType())) {
                        String displayName = "";
                        if (null != map.get(field.getName()) && !"".equals(map.get(field.getName()))) {
                            String[] ids = map.get(field.getName()).split(",");
                            IEntityObjectService objectService = new EntityObjectProxyService(field.getDataSource());
                            for (String id : ids) {
                                EntityObject object = objectService.getObjById(id);
                                displayName += object.getName() + ",";
                            }
                            typeValue.put(field.getName(), displayName.substring(0, displayName.length() - 1));
                        }
                    } else if ("radio".equals(field.getRenderType())) {
                        IEntityObjectService objectService = new DSDictServiceImpl();
                        if(map.get(field.getName())!=null&&!map.get(field.getName()).equals("")){
                            EntityObject object = objectService.getObjBySubId(map.get(field.getName()));
                            if (null != object && object.getId()!=null && !"".equals(object.getId())) {
                                typeValue.put(field.getName(), object.getName());
                            } else {
                                typeValue.put(field.getName(), map.get(field.getName()));
                            }
                        }
                    } else if ("checkbox".equals(field.getRenderType())) {
                        String displayName = "";
                        if (null != map.get(field.getName()) && !"".equals(map.get(field.getName()))) {
                            String[] ids = map.get(field.getName()).split(",");
                            IEntityObjectService objectService = new DSDictServiceImpl();
                            for (String id : ids) {
                                EntityObject object = objectService.getObjBySubId(id);
                                displayName += object.getName() + ",";
                            }
                            typeValue.put(field.getName(), displayName.substring(0, displayName.length() - 1));
                        }
                    } else {
                        typeValue.put(field.getName(), map.get(field.getName()));
                    }
                }
            }
            return typeValue;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}

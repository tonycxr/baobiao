package com.sungcor.baobiao.service.Impl;

import com.sungcor.baobiao.entity.RoleView;
import com.sungcor.baobiao.service.IProcessStatusService;
import com.sungcor.baobiao.utils.Util;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.*;

@Service
public class ProcessStatusServiceImpl implements IProcessStatusService {
    @Override
    public Map getAll(String webappsPath) {
        Map status = new HashMap();
        Document document = null;
        try {
            SAXReader saxReader = new SAXReader();
            document = saxReader.read(new File(webappsPath+"/processStatus.xml"));//读取XML文件,获得document对象
            List listRowSet=document.selectNodes("//status");
            for(Iterator i = listRowSet.iterator(); i.hasNext();){
                Element ele=(Element)i.next();
                status.put(ele.attributeValue("value"),ele.attributeValue("name"));
                System.out.println("value=   "+ele.attributeValue("value"));
                System.out.println("name=   "+ele.attributeValue("name"));

            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return status;
    }

    public List<RoleView> getAllRoleView() {
        List<RoleView> roleViewList = new ArrayList<RoleView>();
        Document document = null;
        try {
            SAXReader saxReader = new SAXReader();
            document = saxReader.read(new File(Util.getRootPath()+"/conf/bpm"+"/roleView.xml"));//读取XML文件,获得document对象
            List listRowSet=document.selectNodes("//roleView");
            for(Iterator i=listRowSet.iterator();i.hasNext();){
                Element ele=(Element)i.next();
                RoleView roleView = new RoleView();
                roleView.setId(ele.attributeValue("id"));
                roleView.setName(ele.attributeValue("name"));
                roleView.setUrl(ele.attributeValue("url"));
                roleView.setIcon(ele.attributeValue("icon"));
                roleView.setPriority(ele.attributeValue("priority"));
                roleView.setDescription(ele.attributeValue("description"));
                roleView.setButton(ele.attributeValue("button"));
                //System.out.println("id=   "+ele.attributeValue("id"));
                //System.out.println("name=   "+ele.attributeValue("name"));
                roleViewList.add(roleView);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return roleViewList;
    }
    public RoleView getRoleView(String id) {
        Document document = null;
        RoleView roleView = new RoleView();
        try {
            SAXReader saxReader = new SAXReader();
            document = saxReader.read(new File(Util.getRootPath()+"/conf/bpm"+"/roleView.xml"));//读取XML文件,获得document对象
            List listRowSet=document.selectNodes("//roleView");

            for(Iterator i=listRowSet.iterator();i.hasNext();){
                Element ele=(Element)i.next();
                if(id.equals(ele.attributeValue("id"))){
                    roleView.setId(ele.attributeValue("id"));
                    roleView.setName(ele.attributeValue("name"));
                    roleView.setUrl(ele.attributeValue("url"));
                    roleView.setIcon(ele.attributeValue("icon"));
                    roleView.setPriority(ele.attributeValue("priority"));
                    roleView.setDescription(ele.attributeValue("description"));
                    roleView.setButton(ele.attributeValue("button"));
                    break;
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return roleView;
    }
}

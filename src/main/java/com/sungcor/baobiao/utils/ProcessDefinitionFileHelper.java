package com.sungcor.baobiao.utils;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.dom4j.Element;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ProcessDefinitionFileHelper {
    protected static Log log = LogFactory.getLog(ProcessDefinitionFileHelper.class);
    private static String qbpmProcessConfigRoot;
    private static Map<String, String> statusMap = new HashMap<String, String>();

    public static Map<String, String> getStatusMap() {
        return statusMap;
    }

    public static String getName(String v) {
        return statusMap.get(v);
    }

    public static String getNodeBusinessDefPath(String flowConfigPath) {
        return qbpmProcessConfigRoot + File.separator + "data" + File.separator + "bpm" + File.separator + flowConfigPath + File.separator + "businessDefinition.xml";
    }

    public static String getGpdFilePath(String flowConfigpath) {
        return qbpmProcessConfigRoot + File.separator + "data" + File.separator + "bpm" + File.separator + flowConfigpath + File.separator + "gpd.xml";
    }

    public static String getImagePath(String flowConfigpath) {
        return qbpmProcessConfigRoot + File.separator + "data" + File.separator + "bpm" + File.separator + flowConfigpath + File.separator + "processimage.jpg";
    }

    public static String getDefinitionPath(String flowConfigpath) {
        return qbpmProcessConfigRoot + File.separator + "data" + File.separator + "bpm" + File.separator + flowConfigpath + File.separator + "processdefinition.xml";
    }

    public static String getMsgDefinitionPath() {
        return qbpmProcessConfigRoot + File.separator + "conf" + File.separator + "bpm" + File.separator + "msgTempletList.xml";
    }


    public static String getOLAMsgDefinitionPath(){
        return qbpmProcessConfigRoot + File.separator+"conf"+File.separator+"bpm"+File.separator+"olaMsgTempletList.xml";
    }

//    public synchronized static void resetConfigRoot(){
//        PropertyUtil pu = PropertyUtil.newInstance();
//        qbpmProcessConfigRoot = pu.getMsg("BPMRepositoryPath");
//        try{
//            String stautsPath = qbpmProcessConfigRoot + "/processStatus.xml";
//            Dom4jHelper xp = new Dom4jHelper(new File(stautsPath));
//            List list = xp.selectNodeList("//processStatus/status");
//            for (Object aList : list) {
//                Element el = (Element)aList;
//                statusMap.put(el.attributeValue("value"),el.attributeValue("name"));
//            }
//        }catch (Exception e){
//            log.error("ProcessNodeStatus load Error:"+e.getMessage());
//        }
//    }

    public synchronized static void resetConfigRoot() {
        qbpmProcessConfigRoot = Util.getRootPath() + File.separator;
        try {
            String stautsPath = qbpmProcessConfigRoot + File.separator + "conf" + File.separator + "bpm" + File.separator + "processStatus.xml";
            Dom4jHelper xp = new Dom4jHelper(new File(stautsPath));
            List list = xp.selectNodeList("//processStatus/status");
            for (Object aList : list) {
                Element el = (Element) aList;
                statusMap.put(el.attributeValue("value"), el.attributeValue("name"));
            }
        } catch (Exception e) {
            log.error("ProcessNodeStatus load Error:" + e.getMessage());
        }
    }

    public static String getQBPMProcessConfigRoot() {
        return qbpmProcessConfigRoot;
    }

    static {
        resetConfigRoot();
    }
}

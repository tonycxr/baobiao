package com.sungcor.baobiao.utils;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.dom4j.DocumentException;
import org.dom4j.Element;

import java.io.File;
import java.util.regex.Pattern;

public class FormAttributeUtil {
    protected static Log log = LogFactory.getLog(FormAttributeUtil.class);

    private Dom4jHelper xPath;
    private Pattern pattern = Pattern.compile("\\s*\\{([a-z]+\\w*)([\\.]?[a-z]+\\w*)*\\}\\s*");
    private Pattern subPattern = Pattern.compile("([a-z]+\\w*)([\\.]?[a-z]+\\w*)*");

    /**
     *
     * @param fileName
     * @throws DocumentException
     */
    public FormAttributeUtil(String fileName) throws DocumentException {
        File file = new File(fileName);
        if(file.exists()){
            this.xPath = new Dom4jHelper(file);
        }
    }

    /**
     *
     * @param nodeName
     * @param subNode
     * @return
     */
    public boolean isContainNode(String nodeName,String subNode){
        boolean isContain = false;
        Element e = xPath.getRootElement();
        Element el = e.element(nodeName);
        if(null != el){
            Element els = el.element(subNode);
            if(null != els ){
                isContain = true;
            }
        }
        return isContain;
    }

    /**
     *
     * @param id
     * @return
     */
    public Object getObjByXml(String id){
        if(xPath.selectSingleNode("//entityObject[@id='"+id+"']")==null){
            return null;
        }
        String classPath = xPath.selectSingleNode("//entityObject[@id='"+id+"']").element("local").attributeValue("class");
        Object object = null;
        try {
            Class clz = null;
            if(classPath != null && !"".equalsIgnoreCase(classPath)){
                clz = Class.forName(classPath);
            }
            try {
                object = clz.newInstance();
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return object;
    }
}

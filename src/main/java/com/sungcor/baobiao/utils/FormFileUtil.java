package com.sungcor.baobiao.utils;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.dom4j.DocumentException;

import java.io.File;

public class FormFileUtil {
    protected static Log log = LogFactory.getLog(FormFileUtil.class);
    private static String formConfigPath = "";

    public synchronized static void loadCMDBCfg(){
        //表单配置文件地址
        formConfigPath = Util.getRootPath()+ File.separatorChar+"conf"+File.separatorChar+"form";
        String formPath = formConfigPath.concat("/").concat("form.cfg.xml");
        try {
            Dom4jHelper xp = new Dom4jHelper(new File(formPath));
        } catch (DocumentException e) {
            e.printStackTrace();
        }
    }

    /**
     *
     * @return
     */
    public static String getAppSource(){
        return formConfigPath.concat("/").concat("appSource.xml");
    }

    /**
     *
     * @return
     */
    public static  String getFormCfgFile(){
        return formConfigPath.concat("/").concat("form.cfg.xml");
    }

    static{
        loadCMDBCfg();
    }
}

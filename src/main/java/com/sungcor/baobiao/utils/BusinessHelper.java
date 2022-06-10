package com.sungcor.baobiao.utils;


import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.dom4j.DocumentException;
import org.dom4j.Element;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * User: FanLei
 * Date: 2012-04-27
 * Time: 10:03
 * Descript:
 */
public class BusinessHelper {

    protected static Log log = LogFactory.getLog(BusinessHelper.class);

    /**=========下面是构造节点业务对象用=========**/
    public final static int RUN_FLAG_1 = 1;//表示处于执行状态
    public final static int RUN_FLAG_0 = 0;//表示处于非执行状态

    public final static String START = "start";//启动（系统自动）
    public final static String DECISION = "decision";//决策
    public final static String SINGIN = "singin"; //签收
    public final static String REFUSE = "refuse"; //拒绝
    public final static String DEAL = "deal";      //处理
    public final static String SAVE = "save";       //保存
    public final static String AUDIT = "audit";   //审核(会签)
    public final static String CONFIRM = "confirm";//关闭
    public final static String RETAKE = "retake";  //取回
    public final static String TRUN = "trun";//转派
    public final static String SETASIDE = "setAside";  //搁置
    public final static String BACKRUN = "backRun";//转回处理
    public final static String REVIEW = "review";//阅处
    public final static String SENDBACK = "sendBack";//退回
    public final static String RELATEDCI = "relatedCI";//关联配置项
    public final static String TERMINATE = "terminate";//终止
    public final static String SUBPROCESS = "subProcess"; //子流程
    public final static String TIMESTRATEGIES = "TimeStrategies";//时间策略
    public final static String FRONTACTION = "frontAction";//前置动作
    public final static String NODEACTION = "nodeAction";//节点动作
    public final static String ENDACTION = "endAction";//后置动作
    public final static String UPGRADEPATH = "upgragePath";//流程路径升级
    public final static String UPGRADEPERSON = "upgragePerson";//流程人员升级
    public final static String ASIDEAUDIT = "asideaudit";   //搁置审核
    public final static String CANCLEASIDE = "cancleaside";//拒绝搁置请求
    public final static String ALLOWASIDE = "allowaside";//同意搁置请求
    public final static Map<String,Dom4jHelper> helperMap = new HashMap<String,Dom4jHelper>(); //缓存流程业务配置文件避免打开过多文件
    private Dom4jHelper xpath;

    public BusinessHelper(String bussinessFileName) throws DocumentException {

        this.xpath = BusinessHelper.helperMap.get(bussinessFileName);
        if (null == this.xpath){
            File file = new File(bussinessFileName);
            if(file.exists()){
                this.xpath = new Dom4jHelper(file);
                BusinessHelper.helperMap.put(bussinessFileName,this.xpath);
            }
        }

    }


    public boolean hasTerminate(String nodeName) throws Exception{
        Element el = xpath.selectSingleNode("//task-node[@name='"+nodeName+"' and @hasTerminate='true']");
        return el!=null;
    }

}

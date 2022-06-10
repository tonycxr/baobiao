package com.sungcor.baobiao.utils;


import com.sungcor.baobiao.STSMConstant;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Lic参数初始化类，直接从该类获取参数，减少IO操作.
 * 该类在运行过程中不会进行修改
 * User: yangy
 * Date: 14-5-21
 * Time: 上午11:18
 * To change this template use File | Settings | File Templates.
 */
public class LicenseControl {

    /**
     * 日志注入
     */
    private static final Log log = LogFactory.getLog(LicenseControl.class);

    /**
     * 并发用户项 如果开启则需要验证登录人数
     */
    private static boolean concurrentUser = Boolean.FALSE;

    private static int  concurrentUserNum;

    /**
     * LIC授权最大人数
     */
    private static int maxUser;



    /**
     * 注册人数限制 在“人员管理”模块进行注册用户数控制
     */
    private static boolean registeredUser  = Boolean.FALSE;

    private static int  registeredUserNum;
    /**
     * 问题模块菜单显示与控制
     * 1、“我的工作”-“待处理问题”
     *  2、“服务流程”-“问题管理”
     *  3、“统计报表”-“问题报表”
     * 二、对以下功能进行显示/隐藏控制
     * 1、“我的工作”-“服务跟踪”界面中的“类型”条件中的“问题”项
     * 2、“服务流程”-“工单综合查询”界面中的“类型”条件中的“问题”项
     * 3、“服务计划”-“表单管理”模块中各页面中的“服务类型”条件中的“问题”项
     * 4、“服务计划”-“流程管理”模块中各页面中的“服务类型”条件中的“问题”项
     * 5、“服务计划”-“服务产品管理”模块中各页面中的“服务类型”条件中的“问题”项
     * 6、在工单处理界面的“关联工单”标签页中的“转问题”按钮
     */
    private static boolean caseProblem = Boolean.TRUE;

    /**
     * 变更/发布模块
     * 一、对以下菜单进行显示/隐藏控制。
     1、“我的工作”-“待处理变更”
     2、“我的工作”-“待处理发布”
     3、“服务流程”-“变更管理”
     4、“服务流程”-“发布管理”
     5、“统计报表”-“变更报表”
     二、对以下功能进行显示/隐藏控制
     1、“我的工作”-“服务跟踪”界面中的“类型”条件中的“变更”和“发布”项
     2、“服务流程”-“工单综合查询”界面中的“类型”条件中的“变更”和“发布”项
     3、“服务计划”-“表单管理”模块中各页面中的“服务类型”条件中的“变更”和“发布”项
     4、“服务计划”-“流程管理”模块中各页面中的“服务类型”条件中的“变更”和“发布”项
     5、“服务计划”-“服务产品管理”模块中各页面中的“服务类型”条件中的“变更”和“发布”项
     6、在工单处理界面的“关联工单”标签页中的“转变更”按钮
     */
    private static boolean caseChange = Boolean.TRUE;

    /**
     * 服务级别管理
     * 一、对以下菜单进行显示/隐藏控制。
     1、“服务设计”-“UC管理”
     2、“服务设计”-“SLA管理”
     3、“服务设计”-“OLA管理”
     4、“服务设计”-“服务报告管理”
     5、“服务设计”-“服务时间管理”
     */
    private static boolean  SLM =  Boolean.TRUE;

    /**
     * 绩效管理
     * 一、对以下菜单进行显示/隐藏控制。
     1、“服务设计”-“IT价值体系视图”
     2、“统计报表”-“IT价值”
     3、“统计报表”-“服务支持态势”
     */
    private static boolean  performance =  Boolean.TRUE;

    /**
     * 对“巡检管理”菜单进行显示/隐藏控制。
     */
    private static boolean inspection = Boolean.TRUE;

    /**
     * 对“日常管理”-“值班管理”菜单进行显示/隐藏控制
     */
    private static boolean duty = Boolean.TRUE;

    /**
     * 对“日常管理”-“任务管理”菜单进行显示/隐藏控制。
     */
    private static boolean task = Boolean.TRUE;

    /**
     * 移动终端接入
     */
    private static boolean mobileTerminal = Boolean.TRUE;

    /**
     * 一、对“知识库”菜单进行显示/隐藏控制。
     *二、对以下功能进行显示/隐藏控制
     * 1、“工单处理”界面中知识关联的相关项；
     *2、“工单处理”界面中的“转知识”项；
     * 3、“工单查看”界面中的知识关联的相关项
     */
    private static boolean Knowledge = Boolean.TRUE;

    /**
     *
     * 服务计划”-“服务产品管理”模块中各页面中的“服务类型”条件中的“备件服务
     */
    private static boolean sparepartsManagement = Boolean.TRUE;

    /**
     *
     * 自定义模块授权
     */
    private static boolean defineMoudle = Boolean.TRUE;

    /**
     *
     * 自定义模块数量
     */
    private static String defineMoudleCount = "";

    /**
     * 静态代码块负责LIC处理工具类
     */
    static {
        try{
            initValue();
        }
        catch (Exception e){
            log.error("LicenseInit initValue is fail : "+e.getMessage());
            e.printStackTrace();
        }
    }
    /**
     * 初始化参数
     */
    private static void initValue() throws Exception {

        //首先加载LIC
        String licFilePath = Util.getRootPath() + File.separator + "server" + File.separator + "bin";
        int result = LicenseManager.checkLicense(licFilePath, LicenseManager.ITSM_PRODUCT_KEY);
        if (result != LicenseManager.VAILD_RESULT) {
            throw new Exception("license vaild failure " + result );
        }
        //初始化各个参数
        LicenseModule module = LicenseManager.getLicenseModule(LicenseManager.ITSM_PRODUCT_KEY, "ConcurrentUser");
        if(null != module){
            concurrentUser =  module.getModuleEnable();
            concurrentUserNum  =  module.getModuleCount();
        }
        module = LicenseManager.getLicenseModule(LicenseManager.ITSM_PRODUCT_KEY, "RegisteredUser");
        if(null != module) {
            registeredUser = module.getModuleEnable();
            registeredUserNum = module.getModuleCount();
        }
        module = LicenseManager.getLicenseModule(LicenseManager.ITSM_PRODUCT_KEY,"User");
        if(null != module){ maxUser = module.getModuleCount();}

        module = LicenseManager.getLicenseModule(LicenseManager.ITSM_PRODUCT_KEY,"CaseProblem");
        if(null != module){  caseProblem = module.getModuleEnable();}

        module = LicenseManager.getLicenseModule(LicenseManager.ITSM_PRODUCT_KEY,"CaseChange");
        if(null != module){caseChange = module.getModuleEnable();}

        module = LicenseManager.getLicenseModule(LicenseManager.ITSM_PRODUCT_KEY,"SLM");
        if(null != module){ SLM = module.getModuleEnable();}

        module = LicenseManager.getLicenseModule(LicenseManager.ITSM_PRODUCT_KEY,"Performance");
        if(null != module){ performance = module.getModuleEnable();}

        module = LicenseManager.getLicenseModule(LicenseManager.ITSM_PRODUCT_KEY,"Inspection");
        if(null != module){inspection =module.getModuleEnable();}

        module = LicenseManager.getLicenseModule(LicenseManager.ITSM_PRODUCT_KEY,"Duty");
        if(null != module){duty = module.getModuleEnable();}

        module = LicenseManager.getLicenseModule(LicenseManager.ITSM_PRODUCT_KEY,"Task");
        if(null != module){task = module.getModuleEnable();}

        module = LicenseManager.getLicenseModule(LicenseManager.ITSM_PRODUCT_KEY,"MobileTerminal");
        if(null != module){mobileTerminal = module.getModuleEnable();}

//        module = LicenseManager.getLicenseModule(LicenseManager.ITSM_PRODUCT_KEY,"Knowledge");
//        if(null != module){Knowledge = module.getModuleEnable();}
        Knowledge = Boolean.TRUE;//2015.7.30 需求修改 ，默认展示知识模块

        module = LicenseManager.getLicenseModule(LicenseManager.ITSM_PRODUCT_KEY,"SparepartsManagement");
        if(null != module){sparepartsManagement = module.getModuleEnable();}

        module = LicenseManager.getLicenseModule(LicenseManager.ITSM_PRODUCT_KEY,"DefineModule");
        if(null != module){
            defineMoudle = module.getModuleEnable();
            module = LicenseManager.getLicenseModule(LicenseManager.ITSM_PRODUCT_KEY,"MaxDefineModule");
            if(null != module){
                defineMoudleCount = String.valueOf(module.getModuleCount());
            }
        }

    }

    /**
     * 获取具有LIC权限的角色集合
     * @return
     */
    public static List<String> getRoleList(){
        List<String> roleList = new ArrayList<String>(STSMConstant.NUM_EIGHTEEN);
        if(!isKnowledge()){ roleList.add("5"); }
        if(!isCaseProblem()){ roleList.add("9"); }
        if(!isInspection()){
            roleList.add("22");
            roleList.add("23");
        }
        if(!isDuty()){
            roleList.add("24");
            roleList.add("25");
        }
        if(!isCaseChange()){
            roleList.add("8");
            roleList.add("10");
        }
        if(!isSparepartsManagement()){
            roleList.add("26");
            roleList.add("27");
        }
        return roleList;
    }

    public static boolean isConcurrentUser() {
        return concurrentUser;
    }

    public static int getMaxUser() {
        return maxUser;
    }

    public static boolean isRegisteredUser() {
        return registeredUser;
    }

    public static boolean isCaseProblem() {
        return caseProblem;
    }

    public static boolean isCaseChange() {
        return caseChange;
    }

    public static boolean isSLM() {
        return SLM;
    }

    public static boolean isPerformance() {
        return performance;
    }

    public static boolean isInspection() {
        return inspection;
    }

    public static boolean isDuty() {
        return duty;
    }

    public static boolean isTask() {
        return task;
    }

    public static boolean isMobileTerminal() {
        return mobileTerminal;
    }

    public static int getConcurrentUserNum() {
        return concurrentUserNum;
    }

    public static int getRegisteredUserNum() {
        return registeredUserNum;
    }

    public static boolean isKnowledge() {
        return Knowledge;
    }

    public static boolean isSparepartsManagement() {
        return sparepartsManagement;
    }

    public static boolean isDefineMoudle() {
        return defineMoudle;
    }

    public static String getDefineMoudleCount() {
        return defineMoudleCount;
    }

    public static void setDefineMoudleCount(String defineMoudleCount) {
        LicenseControl.defineMoudleCount = defineMoudleCount;
    }
}

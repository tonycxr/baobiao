package com.sungcor.baobiao.utils;

import com.sungcor.baobiao.STSMConstant;
import com.sungcor.baobiao.entity.LoginLogBean;
import com.sungcor.baobiao.entity.UserInfoBean;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class SessionHelper {
    public static final Map<String, HttpSession> onlineWebClients=new ConcurrentHashMap<String,HttpSession>();
    public static final Map<String,HttpSession> onlineMobClients=new ConcurrentHashMap<String,HttpSession>();
    private static final String USER_LOGIN_LOG = "user_login_log";
    public static final String USER_INFO = "user_info";
    public static final String  USER_LOGIN_TYPE="loginType";
    public static final int WEB_LOGIN=1;
    public static final int MOB_LOGIN=2;

//    private static OrganiseServiceImpl orgService = (OrganiseServiceImpl) STSMApplicationContext.getBean("system/organiseService");
//    private static UserService userService = (UserService) STSMApplicationContext.getBean("stsm/sys/UserService");
//    private static IRegionService regionService = (IRegionService) STSMApplicationContext.getBean("system/regionService");

    public static void setUserLoginLogInfo(HttpSession session, LoginLogBean loginLogBean) {
        if(null == getUserLoginLogInfo(session)){
            session.setAttribute(USER_LOGIN_LOG, loginLogBean);
        }
    }

    public static LoginLogBean getUserLoginLogInfo(HttpSession session) {
        return (LoginLogBean) session.getAttribute(USER_LOGIN_LOG);
    }

    /**
     * 操作将改变登录的用户信息，如果用户信息已经存在，则不设置。
     *
     * @param session
     * @param userInfo 设置为{@link com.mainsteam.tmcm.stsm.sys.bean.UserInfoBean}
     */
//    public static void setUserInfo(HttpSession session, Object userInfo) {
//        String userName = ((UserInfoBean)userInfo).getLoginName();
//        UserInfoBean userInfoBean = (UserInfoBean)userInfo;
//        List<Integer> ids = orgService.getOrganiseTreeIds(userInfoBean.getOrgarnizationId());
////                orgService.getOrganiseTreeIds(userInfoBean.getOrgarnizationId());
////        if (userInfoBean.getOrgarnizationId() == -1) {
////            ids.add(-1);
////        }
//        List<HashMap> listMap = userService.listUserOrg(String.valueOf(userInfoBean.getUserId()));//数据域查询
//        if(listMap!=null&&listMap.size()>0){
//            for(int i=0;i<listMap.size();i++){
//                ids.add(Integer.parseInt(listMap.get(i).get("ORGANISE_ID").toString()));
//            }
//        }
//        userInfoBean.setOrgIds(ids);
//        if(null == getUserInfo(session)) {
//            session.setAttribute(USER_INFO, userInfoBean);
//            loginLog(session);
//        }
//        List regionIds=regionService.getAllRegionId(userInfoBean).size()==0?null:regionService.getAllRegionId(userInfoBean);
//        userInfoBean.setRegionIds(regionIds);
//        String uAndLName=userInfoBean.getUserName()+"["+userInfoBean.getLoginName()+"]";
//        userInfoBean.setUserNameAndLoginName(uAndLName);
//
//    }

    /**
     * 返回用户信息
     *
     * @param session
     * @return
     */
    public static Object getUserInfo(HttpSession session) {
        return session.getAttribute(USER_INFO);
    }

    public static boolean userIsLogin(HttpSession session) {
        return null != session.getAttribute(USER_INFO);
    }

    public static String getCreatorStr(HttpSession session) {
        UserInfoBean  loginUser =  (UserInfoBean) getUserInfo(session);
        return loginUser.getUserName()+"["+loginUser.getLoginName()+"]" ;
    }

    /**
     * 设置为手机登陆
     * @param session  当前会话
     */
    public static void setMobLogin(HttpSession session){
        session.setAttribute(USER_LOGIN_TYPE,MOB_LOGIN);
    }

    /**
     * 设置为web登陆
     * @param session    当前会话
     */
    public static void setWebLogin(HttpSession session){
        session.setAttribute(USER_LOGIN_TYPE,WEB_LOGIN);
    }

    /**
     * 是否为手机登陆
     * @param session  当前会话
     * @return  true:手机登陆,false:不是手机登陆
     */
    public static boolean isMobLogin(HttpSession session){
        return session.getAttribute(USER_LOGIN_TYPE)==null?false:MOB_LOGIN==(Integer)session.getAttribute(USER_LOGIN_TYPE);
    }

    /**
     * 是否为web登陆
     * @param session  当前会话
     * @return   true:是web登陆,false:不是web登陆
     */
    public static boolean isWebLogin(HttpSession session){
        return session.getAttribute(USER_LOGIN_TYPE)==null?false:WEB_LOGIN==(Integer)session.getAttribute(USER_LOGIN_TYPE);
    }

    /**
     * 返回手机客户端在线数
     * @return
     */
    public static int getMobOnlineCount(){
        return onlineMobClients.size();
    }

    /**
     * 返回web客户端在线数
     * @return
     */
    public static int getWebOnlineCount(){
        return onlineWebClients.size();
    }

    /**
     *  记录登录日志
     * @param
     */
//    private static void loginLog(HttpSession session){
//        try {
//            AuthenticationService authenticationService= (AuthenticationService) STSMApplicationContext.getBean(STSMConstant.SPRING_BEAN_NAME_AUTHENTICATION_SERVICE);
//            LoginLogBean loginLogBean = new LoginLogBean();
//            HttpServletRequest request = ServletActionContext.getRequest();
//            String user = SessionHelper.getCreatorStr(session);
//            loginLogBean.setUser(user);
//            loginLogBean.setLoginTime(new Date());
//            loginLogBean.setIp(getIpAddr(request));
//            authenticationService.insertLoginLog(loginLogBean);
//            setUserLoginLogInfo(session,loginLogBean);
//            UserInfoBean userInfoBean  = (UserInfoBean)session.getAttribute(USER_INFO);
//            userInfoBean.setLoginTime(DateUtil.fmtDate(loginLogBean.getLoginTime(), STSMConstant.STR_EMPTY));
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
    private static String getIpAddr(HttpServletRequest request) {
        String ip = request.getHeader("x-forwarded-for");
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        return ip;
    }

}

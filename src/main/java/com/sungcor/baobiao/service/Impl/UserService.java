package com.sungcor.baobiao.service.Impl;

import com.sungcor.baobiao.entity.Organise;
import com.sungcor.baobiao.entity.User;
import com.sungcor.baobiao.entity.UserInfoBean;
import com.sungcor.baobiao.mapper.FunctionMapper;
import com.sungcor.baobiao.mapper.UserMapper;
import com.sungcor.baobiao.service.IOrganiseService;
import com.sungcor.baobiao.utils.DateUtil;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.*;


@Service
public class UserService{
    boolean isDebug = false;


    /**
     * 注入业务LOG类 记录Log日志
     */
    protected static Log log = LogFactory.getLog(UserService.class);

    @Autowired
    private UserMapper userMapper;
//    private UserInfoDao userInfoDao;

    @Autowired
    private FunctionMapper funcDao;


    @Autowired
    private IOrganiseService organiseService;
    /**
     * @用途 检查用户权限
     * @param userid
     * @param code
     * @return
     */
    public boolean checkPrivilege(int userid , String code){
        Map parm = new HashMap();
        parm.put("userId", userid);
        parm.put("code", code);
        java.util.List<Map> fList = funcDao.getUserFunctionsByCode(parm);

        if(fList.size() > 0 ) return true;
        else return false;

    }

    /**
     * @用途 取用户可以管理的所有单位
     * @param userid
     * @return
     */
    public java.util.List<String> getUserOrgs(int userid){

        java.util.List<UserInfoBean> userInfo = userMapper.findByID(userid);
        if(userInfo.size() > 0){

            // 用户对象
            UserInfoBean userBean = userInfo.get(0);
            List<String> list = new ArrayList<String>();
            List<Integer> integerList = new ArrayList<Integer>();
            integerList = organiseService.getOrganiseTreeIds(userBean.getOrganizationId());
            for(Integer i : integerList){
                list.add(String.valueOf(i));
            }
            return  list;
        }
        return null;
    }


    /**
     * @用途 处理组织
     * @param source
     * @param target
     * @param parent
     */
    private void parseOrg( java.util.List<java.util.HashMap> source, java.util.List<String> target , int parent  ){
        for( java.util.HashMap org:source ){

            int id = new Integer(org.get("id").toString()).intValue();

            // 处理直接下级节点
            Object objPID = org.get("pId");
            if(objPID ==null){
                // 如果没有指定上级节点，则认为是根节点
                objPID = "-1";
            }
            int pId = Integer.parseInt( objPID.toString() );

            if(pId == parent){
                // 为下级单位，加入到List中，处理下级单位
                target.add( String.valueOf(id) );

                // 递归加入下级单位（当前节点的下级节点）
                parseOrg( source, target , id);

                // 如果是直接下级，则不管是否管理单位下级了
                continue;
            }

            // 处理管理单位下级节点
            Object objHAID = org.get("haId");
            if(objHAID != null){
                int haId = Integer.parseInt( objHAID.toString() );

                if(haId == parent){
                    if(!target.contains(String.valueOf(id))){
                        // 为下级单位，加入到List中，处理下级单位
                        target.add( String.valueOf(id) );

                        // 递归加入下级单位（当前节点的下级节点）
                        parseOrg( source, target , id);
                    }


                }

            }


        }

    }




    public String updateUserApp(java.util.List<java.util.HashMap<String, String>> app , int userid){

        int appCnt = app.size();

        String refStr = "";


        for(int i =0 ; i < appCnt ; i++){
            java.util.HashMap appItm = app.get(i);
            String address=(String)appItm.get("address");
            String appid = (String)appItm.get("appid");
            String appName=  (String)appItm.get("appName");
            String type   = (String)appItm.get("type");

            java.util.HashMap parm = new java.util.HashMap();
            parm.put("appid", Integer.parseInt(appid));
            parm.put("userid",userid);

            if(type.equals("add")){
                userMapper.addUserApp(parm);
            }else if(type.equals("delete")){
                userMapper.deleteUserApp(parm);
            }
        }

        return refStr;
    }

    /* (non-Javadoc)
     * @see com.mainsteam.tmcm.stsm.sys.service.impl.IUserService#getUserApp(java.util.HashMap)
     */

    public java.util.List<java.util.HashMap> getUserApp(java.util.HashMap parm){
        return userMapper.getUserApps(parm);
    }


    public String resetUserPwd(int userid, String pwd) {
        return null;
    }

    /* (non-Javadoc)
     * @see com.mainsteam.tmcm.stsm.sys.service.impl.IUserService#resetUserPwd(int, java.lang.String)
     */
//
//    public String resetUserPwd(int userid , String pwd){
//
//        java.util.HashMap parm = new java.util.HashMap();
//        parm.put("userid",userid);
//        parm.put("pwd",pwd);
//
//        userMapper.updatePwd(parm);
//        // 2016-05-18 ww 修改用户密码，更新缓存
//        IMemcache<UserInfoBean> cacheL= MemCacheFactory.getRemoteMemCache(UserInfoBean.class);
//        IMemcache<String> cacheLPwd= MemCacheFactory.getRemoteMemCache(String.class);
//        cacheL.delete("user" + userid);
//        HashMap<String, Object> hashMap = new HashMap<String, Object>(3);
//        hashMap.put("userId",userid);
//        UserInfoBean userInfoBean =  userInfoDao.getSignUserInfo(hashMap);
//        cacheL.delete("userLoginName" + userInfoBean.getLoginName());
//        cacheLPwd.delete("casuser" + userInfoBean.getLoginName());
//        cacheL.set("userLoginName" + userInfoBean.getLoginName(),userInfoBean);
//        cacheL.set("user" + userInfoBean.getUserId(),userInfoBean);
//        cacheLPwd.set("casuser" + userInfoBean.getLoginName(),userInfoBean.getPwd());
//
//        String ref = sync(userid);
//
//        return ref;
//    }

    /* (non-Javadoc)
     * @see com.mainsteam.tmcm.stsm.sys.service.impl.IUserService#updateUserArea(java.util.List, int)
     */

    public void updateUserArea(java.util.List<java.util.HashMap<String, String>> skill , int userid){

        int skillCnt = skill.size();
        for(int i =0 ; i < skillCnt ; i++){
            java.util.HashMap skillItm = skill.get(i);
            String type   = (String)skillItm.get("type");
            java.util.HashMap parm = new java.util.HashMap();
            parm.put("userid",userid);
            String areaid = (String)skillItm.get("areaid");
            if(type.equals("add")){
                parm.put("areaid", Integer.parseInt(areaid));
                userMapper.addUserArea(parm);
            }else if(type.equals("delete")){ //2014.12.26 update 此处逻辑不正确，删除时默认会删除所有，添加区域id进行删除
                parm.put("areaid",areaid);
                userMapper.deleteUserArea(parm);
            }
        }

    }

    /* (non-Javadoc)
     * @see com.mainsteam.tmcm.stsm.sys.service.impl.IUserService#getUserArea(java.lang.Integer)
     */

    public java.util.List<java.util.Map> getUserArea(Integer userid){

        java.util.Map parm = new java.util.HashMap();
        parm.put("userid", userid);

        return userMapper.getUserArea(parm);

    }

    /* (non-Javadoc)
     * @see com.mainsteam.tmcm.stsm.sys.service.impl.IUserService#getUserSkills(java.lang.Integer[])
     */

    public java.util.List<java.util.HashMap> getUserSkills(Integer[] userid){

        return userMapper.getUserSkills(userid);

    }

    /* (non-Javadoc)
     * @see com.mainsteam.tmcm.stsm.sys.service.impl.IUserService#updateUserSkill(java.util.List, int)
     */

    public void updateUserSkill(java.util.List<java.util.HashMap<String, String>> skill , int userid){

        int skillCnt = skill.size();
        for(int i =0 ; i < skillCnt ; i++){
            java.util.HashMap skillItm = skill.get(i);

            String skillId = (String)skillItm.get("skillid");
            String type   = (String)skillItm.get("type");

            java.util.HashMap parm = new java.util.HashMap();
            parm.put("skillid", Integer.parseInt(skillId));
            parm.put("userid",userid);

            if(type.equals("add")){
                userMapper.addUserSkill(parm);
            }else if(type.equals("delete")){
                userMapper.delUserSkill(parm);
            }
        }

    }

    /* (non-Javadoc)
     * @see com.mainsteam.tmcm.stsm.sys.service.impl.IUserService#updateUserRole(java.util.List, int)
     */

    public void updateUserRole(java.util.List<java.util.HashMap<String, String>> role , int userid){
        int roleCnt = role.size();
        for(int i =0 ; i < roleCnt ; i++){
            java.util.HashMap roleItm = role.get(i);

            String roleId = (String)roleItm.get("roleid");
            String type   = (String)roleItm.get("type");

            java.util.HashMap parm = new java.util.HashMap();
            parm.put("roleid", Integer.parseInt(roleId));
            parm.put("userid",userid);

            if(type.equals("add")){
                userMapper.addUserRole(parm);
            }else if(type.equals("delete")){
                userMapper.delUserRole(parm);
            }
        }

    }


    public void updateUserRole(String roles , int userid){
        String[] roleArr=roles.split(",");
        HashMap map=new HashMap();
        map.put("userid",userid);
        userMapper.delAllUserRole(map);
        if(!"".equals(roleArr[0])){
            for(int i=0;i<roleArr.length;i++){
                map.put("roleid",roleArr[i]);
                userMapper.addUserRole(map);
            }
        }
    }


    public UserInfoBean findUserByID(int userid) {
        return null;
    }


    public void mergeRole2User(List<HashMap<String, String>> params, int roleId) {
        int userCnt = params.size();
        for(int i =0 ; i < userCnt ; i++){
            java.util.HashMap roleItm = params.get(i);
            String userId = (String)roleItm.get("userId");
            String type   = (String)roleItm.get("type");
            java.util.HashMap parm = new java.util.HashMap();
            parm.put("userid", Integer.parseInt(userId));
            parm.put("roleid",roleId);
            if(type.equals("add")){
                userMapper.addUserRole(parm);
            }else if(type.equals("delete")){
                userMapper.delUserRole(parm);
            }
        }
    }


    public void updateUser(UserInfoBean parms) {

    }

    /* (non-Javadoc)
     * @see com.mainsteam.tmcm.stsm.sys.service.impl.IUserService#findUserByID(int)
     */
//
//    public UserInfoBean findUserByID(int userid){
//        UserInfoBean userInfoBean = null;
//        IMemcache<UserInfoBean> cacheL= MemCacheFactory.getRemoteMemCache(UserInfoBean.class);
//        if(cacheL.get("user"+userid)!=null){
//            return cacheL.get("user"+userid);
//        }else{
//           /* HashMap<String, Object> hashMap = new HashMap<String, Object>(3);
//            hashMap.put("userId",userid);
//            UserInfoBean userInfoBean = userInfoDao.getSignUserInfo(hashMap);
//            */
//            List<UserInfoBean> users = userMapper.findByID(userid);
//
//            if(users.size()== 1){
//                userInfoBean=users.get(0);
//            }
//            cacheL.set("user"+userid,userInfoBean);
//            return userInfoBean;
//        }
//
//      /*  List<UserInfoBean> users = userMapper.findByID(userid);
//
//        if(users.size()== 1) return users.get(0);*/
//
//        //  return null;
//    }

    /* (non-Javadoc)
     * @see com.mainsteam.tmcm.stsm.sys.service.impl.IUserService#saveUserInfo(com.mainsteam.tmcm.stsm.sys.bean.UserInfoBean, com.mainsteam.tmcm.stsm.sys.bean.UserInfoBean)
     */

    public synchronized Map<String, String> saveUserInfo(UserInfoBean userInfo , UserInfoBean curUser)throws Exception{

        String flag = "0";
        String msg = "";
        String userid = "";
        if(userInfo.getUserId()==null || "".equals(userInfo.getUserId())){
            userInfo.setUserId(null);
        }
        java.util.List<UserInfoBean> sameNameUser = userMapper.findByLoginID(userInfo.getLoginName());

        java.util.List<UserInfoBean> sameCodeUser = userMapper.findByCode(userInfo);


        if(null !=userInfo.getUserCode() && sameCodeUser.size() > 0){
            flag = "-1";
            msg= "stsm.sys.user.manager.usermgt.save.err.existsCode";
        }else{
            // 根据是否有用户ID，判断新增还是修改
            if(userInfo.getUserId() == null || userInfo.getUserId().equals("")){

                System.out.println("====Insert User===="+System.currentTimeMillis());

                // 新增
                boolean isError = false;

                if(sameNameUser.size() > 0){
                    flag = "-1";
                    msg= "stsm.sys.user.manager.usermgt.save.err.exists";

                    isError = true;
                }

                if(!isError){

                    userInfo.setCreateUser(curUser.getUserName() + "[" + curUser.getUserId() + "]");
                    userInfo.setCreateTime(new Date());
                    userMapper.insertUser(userInfo) ;

                    java.util.List<UserInfoBean> saveObj = userMapper.findByLoginID(userInfo.getLoginName());

                    userid = saveObj.get(0).getUserId();
                    //userInfo.setUserId(userid);
                }
//                if(Util.getServiceflag().toLowerCase().equals("true")){
//                    //新增用户信息成功以后邮件短信通知该用户
//                    //邮件
//
//                    if (null != userInfo.getEmail() && !userInfo.getEmail().isEmpty()){
//                        String mailsubject = "STSM为您分配系统登录帐号！";
//                        String mailcontent ="<br/>"+userInfo.getUserName()+"&nbsp;&nbsp;您好！\n" +
//                                "<br/>STSM系统为您分派了登录帐号：\n" +
//                                "<br/>&nbsp;&nbsp;登  录ID："+userInfo.getLoginName()+"\n" +
//                                "<br/>&nbsp;&nbsp;初始密码：111111\n"+
//                                "<br/>&nbsp;&nbsp;请您尽快登录系统修改，有问题请联系管理员进行处理!";
////                        MailSystem.sendMail(userInfo.getEmail(), "", mailsubject, new StringBuilder(mailcontent), userid, "");
//                    }
//                    String smsContent =userInfo.getUserName()+",您好!STSM系统为您分派了登录帐号："+userInfo.getLoginName()+";初始密码：111111;请尽快登录系统修改，有问题请联系管理员进行处理!" ;
//                    //短信
//                    if (null != userInfo.getMobile() && !userInfo.getMobile().isEmpty()){
////                        SmsSystem.sendSms(userInfo.getMobile(), new StringBuilder(smsContent), userid, "");
//                    }
//                }
            }else{

                System.out.println("====Update User ID:====" + userInfo.getUserId());

                // 修改
                boolean isError = false;

                if(sameNameUser.size() > 1){
                    flag = "-1";
                    msg= "stsm.sys.user.manager.usermgt.save.err.exists";

                    isError = true;
                }else if(sameNameUser.size() == 1){
                    UserInfoBean sameUserItm = sameNameUser.get(0);
                    if(!sameUserItm.getUserId().equals(userInfo.getUserId())){
                        flag = "-1";
                        msg= "stsm.sys.user.manager.usermgt.save.err.exists";

                        isError = true;
                    }
                }

                if(!isError){

                    //System.out.println("Update User Info , user Mobile is :" + userInfo.getMobile());

                    userInfo.setModifyUser(curUser.getUserName() + "[" + curUser.getUserId() + "]");
                    userid = userInfo.getUserId();
                    updateUser(userInfo);

						/*String ref = sync(new Integer(userInfo.getUserId()));

						msg = ref;*/
                }
            }
        }
        java.util.Map<String, String> refMap = new java.util.HashMap<String, String>();

        refMap.put("flag", flag);
        refMap.put("msg", msg);
        refMap.put("userid", userid);

        return refMap;
    }

    /* (non-Javadoc)
     * @see com.mainsteam.tmcm.stsm.sys.service.impl.IUserService#queryList(java.util.HashMap)
     */

    public java.util.List<java.util.HashMap> queryList(java.util.HashMap parms){
        return userMapper.queryUserList(parms);
    }


    public List<HashMap> queryUserList(HashMap parms) {
        return userMapper.queryAllUserList(parms);
    }

    /* (non-Javadoc)
     * @see com.mainsteam.tmcm.stsm.sys.service.impl.IUserService#queryUserCnt(java.util.HashMap)
     */

    public int queryUserCnt(java.util.HashMap parms){

        java.util.List<java.util.HashMap> ref = userMapper.getUserCnt(parms);

        if(ref.size() == 0){
            return -1;
        }

        return  Integer.parseInt(ref.get(0).get("usercnt").toString());

    }



    /**
     * @用途 同步一个用户的所有第三方应用
     * @param userid
     * @return
     */
    public String sync(Integer userid){
        if(isDebug) return "";

        String refStr = "";

        java.util.HashMap userAppParm = new java.util.HashMap();
        userAppParm.put("userid", userid);

        java.util.List<java.util.HashMap> apps = userMapper.getUserApps(userAppParm);

        System.out.println("userAppCnt :" + apps.size());

        for( java.util.HashMap itm : apps ){

            // 忽略掉没有选择的应用
            Object status = itm.get("status");
            if(status == null || "".equals(String.valueOf(status))){
                continue;
            }

            String appName = String.valueOf(itm.get("name"));
            String appAddress = String.valueOf(itm.get("baseInfoIfUrl"));
            String operateType = "UPDATE";

            /*refStr += sync(userid , appName , appAddress , operateType);*/

        }

        return refStr;
    }

    /**
     * @用途 向应用中进行同步
     * @param userid - 用户ID
     * @param address - 应用地址
     * @param operateType - 操作类型
     * @return
     */
    private String sync(Integer userid , String appName , String address , String operateType){
        if(isDebug) return null;
        //System.out.println("userid:" + userid + "\taddress:" + address + "\toperateType:" + operateType);

        // 取用户信息
        java.util.List<UserInfoBean> userInfo = userMapper.findByID(userid);
        UserInfoBean user = userInfo.get(0);

        // 构造同步对象
        User syncUser = new User();
        syncUser.setActive(user.getActive());
        syncUser.setAvailable(user.getAvailable());
        syncUser.setBirthday(DateUtil.parse(user.getBirthday(), "yyyy-MM-dd HH:mm:ss"));
        syncUser.setEmail(user.getEmail());
        syncUser.setId(user.getUserId());
        syncUser.setLoginid(user.getLoginName());
        syncUser.setMobile(user.getMobile());
        syncUser.setName(user.getUserName());
        syncUser.setOperatetype(operateType);
        syncUser.setOrganizationid(String.valueOf(user.getOrganizationId()));
        syncUser.setPassword(user.getPwd().toLowerCase());
        syncUser.setPhone(user.getPhone());
        syncUser.setSex(user.getSex());
        syncUser.setUsercode(user.getUserCode());

        // 调用同步方法
//        try{
//            List<User> appSystems= new java.util.ArrayList<User>();
//            appSystems.add(syncUser);
//            JaxWsProxyFactoryBean factory = new JaxWsProxyFactoryBean();
//            factory.setServiceClass(IBaseInfoServiceBus.class);
//            factory.setAddress(address);
//            IBaseInfoServiceBus service = (IBaseInfoServiceBus) factory.create();
//            service.setUser(JsonUtil.toJson(appSystems));
//        }catch(Exception e){
//            e.printStackTrace();
//            String refMsg = STSMApplicationContext.getApplicationContext().getMessage("stsm.sys.user.manager.usermgt.app.sync.exception", new String[]{user.getUserName() , appName}, null);
//            return refMsg;
//        }
        return null;
    }


    public Map checkDelete(String ids) {
        String ids1[] = ids.split(",");
        Integer[] ids2 = new Integer[ids1.length];
        for (int i = 0; i < ids1.length; i++) {
            ids2[i] = Integer.parseInt(ids1[i]);
        }
        List<HashMap> hashMapList = this.userMapper.checkDelete(ids2);
        Map retMap = new HashMap();
        retMap.put("userB", hashMapList);
        return retMap;
    }


    public Map checkRegionDelete(String ids) {
        String ids1[] = ids.split(",");
        Integer[] ids2 = new Integer[ids1.length];
        for (int i = 0; i < ids1.length; i++) {
            ids2[i] = Integer.parseInt(ids1[i]);
        }
        List<HashMap> hashMapList = this.userMapper.checkRegionDelete(ids2);
        Map retMap = new HashMap();
        retMap.put("userB", hashMapList);
        return retMap;
    }


    public List<UserInfoBean> queryAllUser(HashMap params) {
        return userMapper.queryAllUser(params);
    }


    public List<UserInfoBean> findByLoginID(String userName) {
        return userMapper.findByLoginID(userName);
    }


    public List<UserInfoBean> getUserByRole(Map parameter) {
        return userMapper.getUserByRole(parameter);  //To change body of implemented methods use File | Settings | File Templates.
    }


    public int findAvailableUser(UserInfoBean parameter) {
        return userMapper.findByCode(parameter).size();  //To change body of implemented methods use File | Settings | File Templates.
    }

//
//    public void updateUser(UserInfoBean parms) {
//        userMapper.updateUser(parms);//To change body of implemented methods use File | Settings | File Templates.
//        IMemcache<UserInfoBean> cacheL= MemCacheFactory.getRemoteMemCache(UserInfoBean.class);
//        IMemcache<String> cacheLPwd= MemCacheFactory.getRemoteMemCache(String.class);
//        cacheL.delete("user" + parms.getUserId());
//        UserInfoBean userInfoBean =  findUserByID(Integer.parseInt(parms.getUserId()));
//        cacheL.delete("userLoginName" + userInfoBean.getLoginName());
//        cacheLPwd.delete("casuser" + userInfoBean.getLoginName());
//
//    }


    public void addUserSkill(HashMap parm) {
        userMapper.addUserSkill(parm);
    }


    public List<HashMap> getcheckUserSkills(String userID) {
        return userMapper.getcheckUserSkills(userID);
    }


    public void delUserSkill(HashMap params) {
        userMapper.delUserSkill(params);
    }


    public void deleteUser(String userId) {

    }

    public List<HashMap> listUserOrg(String id){
        return  userMapper.listUserOrg(id);
    }

//    public void deleteUser(String userId){
//        userMapper.updateDeleteFlag(userId);
//        IMemcache<UserInfoBean> cacheL= MemCacheFactory.getRemoteMemCache(UserInfoBean.class);
//        cacheL.delete("user" + userId);
//        UserInfoBean userInfoBean =  findUserByID(Integer.parseInt(userId));
//        cacheL.delete("userLoginName" + userInfoBean.getLoginName());
//    }

    public List<UserInfoBean> getAllUserBean(){
        List list=userMapper.getAllUserBean();
        return list;
    }

    public List<UserInfoBean> getUserByRegion(HashMap map){
        List<UserInfoBean> list=userMapper.getUserByRegion(map);
        for(UserInfoBean userInfoBean:list){
            Organise organise=organiseService.getOrganiseByID(userInfoBean.getOrganizationId());
            userInfoBean.setOrganizationName(organise.getName());
        }
        return list;
    }

    public List<UserInfoBean> getUserForRegion(HashMap map){
        List<UserInfoBean> list=userMapper.getUserForRegion(map);
        for(UserInfoBean userInfoBean:list){
            Organise organise=organiseService.getOrganiseByID(userInfoBean.getOrganizationId());
            userInfoBean.setOrganizationName(organise.getName());
        }
        return list;
    }

    public List<UserInfoBean> findByUserName(String userName){
        List<UserInfoBean> list=userMapper.findByUserName(userName);
        return list;
    }
}

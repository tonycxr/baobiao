package com.sungcor.baobiao.entity;

import com.sungcor.baobiao.utils.DateUtil;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Data
public class UserInfoBean implements Serializable {
    private static final String SUPER_ADMIN_ID = "-1";

    private String userId;
    private String userName;
    private String loginName;
    private String pwd;
    private String email;
    private String mobile;
    private String phone;
    private String active; //是否在职
    private String available; //是否启用
    private String nickName;
    private String face;
    private String portrait;
    private String createUser;
    private Date createTime;
    private String modifyUser;
    private Date modifyTime;

    private String userCode;
    private String sex;
    private java.util.Date birthday;
    private int organizationId;
    private String organizationName;
    private String isBoss;
    private String position;
    private String loginTime;
    private String remark;//用户描述
    private String photoPath;//头像路径
    private int areaId;//区域ID
    private String areaName;
    private String logId;
    private String logTitle;//日志标题
    private String logMsg;// 日志信息

    private String regionId;

    private List<String> userFunctions;
    private List<Integer> orgIds;
    private List<Integer> regionIds;
    private String userNameAndLoginName;

    private String wechatId;
    private String rtxId;
    private Integer deleteFlag;

    public static User userinfo2user(UserInfoBean userInfoBean) {
        User user = new User();
        user.setLoginid(userInfoBean.getLoginName());
        user.setName(userInfoBean.userName);
        user.setEmail(userInfoBean.getEmail());
        user.setMobile(userInfoBean.getMobile());
        user.setUsercode(userInfoBean.getUserCode());
        user.setActive(userInfoBean.getActive());
        user.setBirthday(DateUtil.parse(userInfoBean.getBirthday(),
                "yyyy-MM-dd HH:mm:ss"));
        user.setOrganizationid(String.valueOf(userInfoBean.getOrganizationId()));
        user.setPassword(userInfoBean.getPwd());
        user.setPhone(userInfoBean.getPhone());
        user.setSex(userInfoBean.getSex());
        user.setAvailable(userInfoBean.getAvailable());
        user.setRemark(userInfoBean.getRemark());
        user.setPhotoPath(userInfoBean.getPhotoPath());
        user.setAreaId(String.valueOf(userInfoBean.getAreaId()));
        return user;
    }

    public static UserInfoBean user2userinfo(User user) {
        UserInfoBean userInfoBean = new UserInfoBean();
        userInfoBean.setUserName(user.getName());

        userInfoBean.setUserCode(user.getUsercode());

        userInfoBean.setLoginName(user.getLoginid());
        userInfoBean.setActive(user.getActive());
        userInfoBean.setAvailable(user.getAvailable());
        userInfoBean.setBirthday(user.getBirthday() == null
                || user.getBirthday().isEmpty() ? new Date() : DateUtil
                .getTime(user.getBirthday(), "yyyy-MM-dd HH:mm:ss"));
        userInfoBean.setEmail(user.getEmail());
        userInfoBean.setMobile(user.getMobile());
        userInfoBean.setPhone(user.getPhone());
        userInfoBean.setOrganizationId(user.getOrganizationid() == null
                || user.getOrganizationid().isEmpty() ? -1 : Integer
                .valueOf(user.getOrganizationid()));
        userInfoBean.setPwd(user.getPassword()!=null?user.getPassword().toUpperCase():null);
        userInfoBean.setSex(user.getSex());
        user.setRemark(userInfoBean.getRemark());
        user.setPhotoPath(userInfoBean.getPhotoPath());
        user.setAreaId(String.valueOf(userInfoBean.getAreaId()));
        return userInfoBean;
    }
}

package com.sungcor.baobiao.service;

import com.sungcor.baobiao.entity.UserInfoBean;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public interface IUserInfoService {
    public UserInfoBean getUserInfoByLoginName(String loginName);

    public boolean changeUserPassword(String loginName, String newPassword);

    public boolean updateUserSkin(String userId, String skin);

    public List<Object> getUsersFuzzy(HashMap<String, Object> paarms);
    public UserInfoBean getSignUserInfo(String userId );

    public  UserInfoBean  getUserPwd(Map map);
}

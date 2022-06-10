package com.sungcor.baobiao.service;

import com.sungcor.baobiao.entity.UserInfoBean;

import java.util.Map;

public interface ISysOrderUserService {

    /**
     * 添加工单外部申请人
     * @param params
     * @return
     */
    public int addSysOrderUser(Map params);

    /**
     * 更新工单外部申请人
     * @param params
     * @return
     */
    public int updateSysOrderUser(Map params);

    /**
     *根据id查询工单外部申请人信息
     * @param userId
     * @return
     */
    public UserInfoBean getSysOrderUserById(int userId);
}


package com.sungcor.baobiao.service.Impl;

import com.sungcor.baobiao.entity.UserInfoBean;
import com.sungcor.baobiao.service.IQBPMStaffService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class QBPMStaffServiceImpl implements IQBPMStaffService {

    @Autowired
    private UserService userService;
    @Override
    public UserInfoBean getStaffById(Integer userId) {
        UserInfoBean   ret =  userService.findUserByID(userId);
        return ret;
    }
}

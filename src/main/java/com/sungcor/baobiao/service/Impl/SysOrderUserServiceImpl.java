package com.sungcor.baobiao.service.Impl;

import com.sungcor.baobiao.entity.UserInfoBean;
import com.sungcor.baobiao.mapper.SysOrderUserMapper;
import com.sungcor.baobiao.service.ISysOrderUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;


@Service
public class SysOrderUserServiceImpl implements ISysOrderUserService {

    @Autowired
    private SysOrderUserMapper sysOrderUserMapper;


    public int addSysOrderUser(Map params) {
        return sysOrderUserMapper.addSysOrderUser(params);  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public int updateSysOrderUser(Map params) {
        return sysOrderUserMapper.updateSysOrderUser(params);  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public UserInfoBean getSysOrderUserById(int userId) {
        return sysOrderUserMapper.getSysOrderUserById(userId);
    }
}

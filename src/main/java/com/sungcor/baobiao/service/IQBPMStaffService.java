package com.sungcor.baobiao.service;

import com.sungcor.baobiao.entity.NodeHistory;
import com.sungcor.baobiao.entity.ProcessInstance;
import com.sungcor.baobiao.entity.UserInfoBean;

import java.util.List;
import java.util.Map;
import java.util.Set;

public interface IQBPMStaffService {

    UserInfoBean getStaffById(Integer userId);

}

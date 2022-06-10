package com.sungcor.baobiao.service;

import com.sungcor.baobiao.entity.RoleView;

import java.util.List;
import java.util.Map;

public interface IProcessStatusService {

    public Map getAll(String webappsPath);
    public List<RoleView> getAllRoleView();
    public RoleView getRoleView(String id);

}

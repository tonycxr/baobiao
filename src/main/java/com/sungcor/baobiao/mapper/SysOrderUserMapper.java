package com.sungcor.baobiao.mapper;

import com.sungcor.baobiao.entity.UserInfoBean;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.util.Map;

@Mapper
@Component
@Repository
public interface SysOrderUserMapper {
    int addSysOrderUser(Map params);

    int updateSysOrderUser(Map params);

    UserInfoBean getSysOrderUserById(int userId);
    /**
     * 按照用户来查询信息
     * @param userQuery
     * @return
     */
    UserInfoBean getSysOrderUser(Map userQuery);
}

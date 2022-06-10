package com.sungcor.baobiao.mapper;


import com.sungcor.baobiao.entity.UserInfoBean;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Mapper
@Component
@Repository
public interface UserMapper {
    /**
     * @用途 根据用户代码取用户信息Bean
     * @param parm
     * @return
     */
    public List<UserInfoBean> findByCode(UserInfoBean parm);

    /**
     * @用途 删除指定用户的第三方应用
     * @param parm
     */
    public void deleteUserApp(HashMap parm);

    /**
     * 添加用户第三方应用
     * @param parm
     */
    public void addUserApp(HashMap parm);

    /**
     * @用途 取用户的App
     * @param parm
     * @return
     */
    public List<HashMap> getUserApps(HashMap parm);

    /**
     * @用途 重置指定用户的密码
     * @param parm
     */
    public void updatePwd(HashMap parm);

    /**
     * @用途 删除指定用户的区域
     * @param parm
     */
    public void deleteUserArea(HashMap parm);

    /**
     * 添加用户区域
     * @param parm
     */
    public void addUserArea(HashMap parm);

    /**
     * @用途 删除用户区域
     * @param parm
     */
    public void delUserArea(HashMap parm);

    /**
     * @用途 取用户的区域
     * @param parm
     * @return
     */
    public List<Map> getUserArea(Map parm);

    /**
     * @用途 取用户的技能
     * @param parm
     * @return
     */
    public List<HashMap> getUserSkills(Integer[] parm);

    /**
     * @用途 删除用户的技能
     * @param parm
     */
    public void delUserSkill(HashMap parm);

    /**
     * @用途 添加用户技能
     * @param parm
     */
    public void addUserSkill(HashMap parm);

    /**
     * @用途 删除用户的角色
     * @param parm
     */
    public void delUserRole(HashMap parm);

    public void delAllUserRole(HashMap parm);

    /**
     * @用途 添加用户角色
     * @param parm
     */
    public void addUserRole(HashMap parm);

    /**
     * @用途 用户信息
     * @param parms - 用户信息
     * @return int - 用户ID
     */
    public  int insertUser(UserInfoBean parms);

    /**
     * @用途 修改用户信息
     * @param parms - 用户信息
     */
    public void updateUser(UserInfoBean parms);

    /**
     * @用途 根据ID取用户信息
     * @param userid - 用户ID
     * @return
     */
    public List<UserInfoBean> findByID(int userid);

    /**
     * @用途 根据用户名取用户信息
     * @param userName - 用户姓名
     * @return java.util.List - 同名用户信息
     */
    public List<UserInfoBean> findByLoginID(String userName);

    /**
     * @用途  根据用户ID取用户所能管理的组织信息
     * @param parms - Map (eg:  userid：1）
     * @return
     */
    public List<HashMap> queryOrg(HashMap parms);

    /**
     * @用途 根据条件去用户信息列表
     * @param parms - 查询条件
     * @return
     */
    public List<HashMap> queryUserList(HashMap parms);
    /**
     * @用途 查询所有用户信息列表
     * @param parms - 查询条件
     * @return
     */
    public List<HashMap> queryAllUserList(HashMap parms);

    /**
     * @用途 查询所有用户信息列表
     * @return
     */
    public List<HashMap> getAllUser();

    public List<UserInfoBean> getAllUserBean();

    /**
     * @用途 根据条件去用户信息列表记录数
     * @param parms - 查询条件
     * @return
     */
    public List<HashMap> getUserCnt(HashMap parms);

    /**
     *  根据用户删除角色
     * @param param
     */
    public void deleteRoles(HashMap param);

    public List<HashMap> checkDelete(Integer[] item);

    public List<HashMap> checkRegionDelete(Integer[] item);

    public List<UserInfoBean> queryAllUser(HashMap params);

    /**
     * 查询对应角色用户信息
     * @param parameter
     * @return
     */
    public List<UserInfoBean> getUserByRole(Map parameter);

    public void deleteUserOrg(Map parameter);

    public List<HashMap> listUserOrg(String userID);

    public void insertUserOrg(Map parameter);

    public List<HashMap> listOrg(String userID);

    public  List<UserInfoBean> GetEamilById(HashMap map);//通过id查询邮件

    public List<UserInfoBean> GetSmsById(HashMap map);//通过id查询电话

    public  List<HashMap> getcheckUserSkills(String userID);

    public void updateDeleteFlag(String userId);

    public void updateDeleteFlagLing(String userId);

    public List<UserInfoBean> getUserByRegion(HashMap map);

    public List<UserInfoBean> getUserForRegion(HashMap map);

    public List<UserInfoBean> findByUserName(String userName);

    public List<UserInfoBean> selectUserByDeleteFlag(String userId);

}

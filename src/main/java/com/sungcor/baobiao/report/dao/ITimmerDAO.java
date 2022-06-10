package com.sungcor.baobiao.report.dao;

import com.sungcor.baobiao.report.bean.TimmerVO;

import java.util.Map;

public interface ITimmerDAO {

    /**
     * @用途 增加Job
     * @param parm
     */
    public void insertJob(TimmerVO parm);


    /**
     * @用途 修改Job
     * @param parm
     */
    public void updateJob(TimmerVO parm);


    /**
     * @用途 删除Job
     * @param parm
     */
    public void deleteJob(TimmerVO parm);

    /**
     * @用途 查询Job，如果参数未提供，则查询全部
     * @param parm
     * @return
     */
    public java.util.List<TimmerVO> queryJob(TimmerVO parm);

    public java.util.List<TimmerVO> queryJob1(TimmerVO parm);

    /**
     * @用途 根据开始字符串模糊匹配查询Job
     * @param parm
     * @return
     */
    public java.util.List<TimmerVO> queryJobByCodeStartFuzzy(TimmerVO parm);
    /*暂停或停止定时器*/
    public void  updateByid(Map map);

}

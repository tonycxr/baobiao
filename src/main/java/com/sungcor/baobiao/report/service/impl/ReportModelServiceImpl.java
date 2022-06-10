package com.sungcor.baobiao.report.service.impl;

import com.sungcor.baobiao.mapper.FunctionMapper;
import com.sungcor.baobiao.report.mapper.ReportModelMapper;
import com.sungcor.baobiao.report.mapper.ReportQuerySettingMapper;
import com.sungcor.baobiao.report.bean.ReportChapterBean;
import com.sungcor.baobiao.report.bean.ReportModelBean;
import com.sungcor.baobiao.report.bean.ReportTaskBean;
import com.sungcor.baobiao.report.dao.*;
import com.sungcor.baobiao.report.mapper.ReportTaskMapper;
import com.sungcor.baobiao.report.mapper.ReportTypeAttrMapper;
import com.sungcor.baobiao.report.service.IReportModelService;
import com.sungcor.baobiao.report.service.IReportQuerySettingService;
import com.sungcor.baobiao.report.service.IReportTypeAttrService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 * Date: 16-5-23
 * Time: 下午3:52
 * To change this template use File | Settings | File Templates.
 */
@Transactional
@Service
public class ReportModelServiceImpl implements IReportModelService {

    @Autowired
    private ReportModelMapper reportModelMapper;

    @Autowired
    private ReportTaskMapper reportTaskMapper;

    @Autowired
    private FunctionMapper functionMapper;

    @Autowired
    private ReportTypeAttrMapper reportTypeAttrMapper;

    @Autowired
    public ReportQuerySettingMapper reportQuerySettingMapper;

    @Autowired
    private IReportTypeAttrService reportTypeAttrService;

    @Autowired
    private IReportQuerySettingService reportQuerySettingService;

    @Override
    public List<ReportModelBean> findListByMap(HashMap map) {
        return reportModelMapper.findListByMap(map);  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public Integer findListCountByMap(HashMap map) {
        return reportModelMapper.findListCountByMap(map);  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public Integer updateStatus(HashMap map) {
        if (map.get("status").equals("1")) {
            //设置成有效
            //增加菜单权限的增加
            functionMapper.insertFunctionByModel(Integer.parseInt(map.get("id").toString()));
        } else {
            //设置成无效
            //删除菜单权限
            functionMapper.deleteFunctionByModel(Integer.parseInt(map.get("id").toString()));
        }
        return reportModelMapper.updateStatus(map);  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public Integer insertModel(ReportModelBean reportModelBean) {
        int flag = reportModelMapper.insertModel(reportModelBean);  //To change body of implemented methods use File | Settings | File Templates.
        reportModelBean.setCode("customReport_reportModel_" + reportModelBean.getId());
        reportModelMapper.updateModelCode(reportModelBean);
        if ("1".equals(reportModelBean.getStatus())) {
            //当前为启用才执行
            //增加菜单权限的增加
            functionMapper.insertFunctionByModel(reportModelBean.getId());
        }
        return flag > 0 ? reportModelBean.getId() : null;
    }

    @Override
    public Integer copyModel(String id) {
        //保存主体
        ReportModelBean r = findBeanById(id);
        r.setName(r.getName() + "_副本");
        Integer newModelId = insertModel(r);
        r.setId(newModelId);
        r.setName(r.getName() + "("+newModelId+")");
        r.setShowChart(r.getShowChart());
        r.setShowTable(r.getShowTable());
        updateModel(r);
        //复制内容设置
        reportTypeAttrService.copyContext(newModelId,Integer.parseInt(id),r.getTypeId());
        //查询设置复制
        reportQuerySettingService.copyQuerySetting(newModelId, id);

        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void updateModelTime(HashMap map) {
        reportModelMapper.updateModelTime(map);
    }

    @Override
    public void updateModel(ReportModelBean reportModelBean) {
        ReportModelBean oldBean = reportModelMapper.findBeanById(String.valueOf(reportModelBean.getId()));
        reportModelMapper.updateModel(reportModelBean);
        String newStatus = reportModelBean.getStatus();
        if(!newStatus.equals(oldBean.getStatus())){
//        functionMapper.deleteFunctionByModel(reportModelBean.getId());
            if ("1".equals(reportModelBean.getStatus())) {
                //当前为启用才执行
                //增加菜单权限的增加
                functionMapper.insertFunctionByModel(reportModelBean.getId());
            } else {
                //禁用时，删除菜单权限
                functionMapper.deleteFunctionByModel(reportModelBean.getId());
            }
        }else if(!reportModelBean.getName().equals(oldBean.getName())){
            //名字不相等，需要更新名字
            functionMapper.deleteFunctionByModel(reportModelBean.getId());
            if("1".equals(reportModelBean.getStatus())){
                functionMapper.insertFunctionByModel(reportModelBean.getId());
            }
        }
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public List<Map<String,String>> deleteModel(String[] ids) {
        List<Map<String,String>> nlist = new ArrayList<Map<String,String>>();
        List<String> idsList = new ArrayList<String>();
        List<String> ids2List = new ArrayList<String>();
        List<ReportTaskBean> tasklist= reportTaskMapper.findListByModelIds(ids);
        Collections.addAll(idsList,ids);
        for(ReportTaskBean taskBean:tasklist){
            if(idsList.contains(String.valueOf(taskBean.getModelId()))){
                idsList.remove(String.valueOf(taskBean.getModelId()));
                Map<String,String> m = new HashMap<String, String>();
                m.put("modelId",String.valueOf(taskBean.getModelId()));
                m.put("type","task");
                nlist.add(m);
            }
        }
        if(idsList.size()>0){
            String[] ts =new String[idsList.size()];
            idsList.toArray(ts);
            List<ReportChapterBean> list = reportTypeAttrMapper.findChapterCByModelIds(ts);
            Collections.addAll(ids2List,ts);
            for(ReportChapterBean chapterBean:list){
                if(ids2List.contains(String.valueOf(chapterBean.getModelId()))){
                    ids2List.remove(String.valueOf(chapterBean.getModelId()));
                    Map<String,String> m = new HashMap<String, String>();
                    m.put("modelId",String.valueOf(chapterBean.getModelId()));
                    m.put("type","chapter");
                    nlist.add(m);
                }
            }
        }
        if(ids2List.size()>0){
            String[] array =new String[ids2List.size()];
            ids2List.toArray(array);
            reportModelMapper.deleteModel(array);
            //删除内容设置
            reportTypeAttrMapper.deteteStatDimByModelIds(array);
            reportTypeAttrMapper.deteteIndexByModelIds(array);
            reportTypeAttrMapper.deteteDimByModelIds(array);
            reportTypeAttrMapper.deleteTreeDimensionByModelIds(array);
            reportTypeAttrMapper.deleteCpChildByModelIds(array);
            reportTypeAttrMapper.deleteChapterByModelIds(array);
            //查询条件删除
            reportQuerySettingMapper.deleteAllField(array);
            reportQuerySettingMapper.deleteAllDateField(array);
            //删除菜单权限
            functionMapper.deleteReportModelFunction(ids2List);
        }
        //To change body of implemented methods use File | Settings | File Templates.
        return nlist;
    }

    @Override
    public ReportModelBean findBeanById(String modelId) {
        return reportModelMapper.findBeanById(modelId);  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void updatePermissions(String roles, String modelId) {

        String[] roleArr = roles.split("\\|");
        int roleCnt = roleArr.length;
        for (int i = 0; i < roleCnt; i++) {
            String roleItmStr = roleArr[i];
            String[] roleItmArr = roleItmStr.split("-");
            String roleId = roleItmArr[0];
            String type = roleItmArr[1];
            HashMap parm = new HashMap();
            parm.put("roleId", Integer.parseInt(roleId));
            parm.put("modelId", modelId);

            if (type.equals("add")) {
                int num = reportModelMapper.findFunction(parm);
                if(num==0){
                    //当前报表分组中还没有其他报表权限，所以要add一把
                    reportModelMapper.addGroupFunction(parm);
                }
                reportModelMapper.addFunction(parm);
            } else if (type.equals("delete")) {
                reportModelMapper.delFunction(parm);
                int num = reportModelMapper.findDeleteFunction(parm);
                if(num==0){
                    //没有报表模板了，则要删
                    reportModelMapper.deleteGroupFunction(parm);
                }
            }
        }
    }

    @Override
    public List<ReportModelBean> findReportModelList() {
        return reportModelMapper.findReportModelList();
    }

    @Override
    public List<ReportModelBean> findListByPara(HashMap map) {
        return reportModelMapper.findListByPara(map);
    }

    @Override
    public List<ReportModelBean> findModelByIds(List<String> ids){
        return reportModelMapper.findModelByIds(ids);
    }

    @Override
    public List<ReportModelBean> findByName(String name)
    {
        return reportModelMapper.findByName(name);
    }
}

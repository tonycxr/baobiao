package com.sungcor.baobiao.controller;

import com.sungcor.baobiao.report.service.*;
import com.sungcor.baobiao.report.bean.*;

import org.springframework.web.bind.annotation.RestController;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


import java.io.File;
import java.io.FileInputStream;
import java.io.OutputStream;

import java.util.zip.ZipInputStream;

/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 * Date: 16-5-27
 * Time: 下午4:29
 * To change this template use File | Settings | File Templates.
 */
@RestController
public class CustomReportBrowseController {

    private IReportBrowseService reportBrowseService;

    private IReportTaskService reportTaskService;

    private IReportQuerySettingService reportQuerySettingService;

    private IReportGroupService reportGroupService;
    private List<ReportTypeBean> reportTypeList;
    private List<ReportGroupBean> reportGroupList;
    private List customTaskType;
    private String keyWorld;
    private String reportType;
    private String reportGroup;
    private String taskType;
    private String selectIds;
    private String[] ids;

//    public int getResultSize() {
//        UserInfoBean user = (UserInfoBean) SessionHelper.getUserInfo(request.getSession());
//        HashMap map = new HashMap();
//        map.put("keyWorld", "".equals(keyWorld) ? null : keyWorld);
//        map.put("reportType", "".equals(reportType) ? null : reportType);
//        map.put("reportGroup", "".equals(reportGroup) ? null : reportGroup);
//        map.put("taskType", "".equals(taskType) ? null : taskType);
//        map.put("create_user",user.getUserId());
//        return reportBrowseService.findListCountByMap(map);
//    }

//    public List listResults(int from, int length) {
//        UserInfoBean user = (UserInfoBean) SessionHelper.getUserInfo(request.getSession());
//        HashMap map = new HashMap();
//        map.put("keyWorld", "".equals(keyWorld) ? null : keyWorld);
//        map.put("reportType", "".equals(reportType) ? null : reportType);
//        map.put("reportGroup", "".equals(reportGroup) ? null : reportGroup);
//        map.put("taskType", "".equals(taskType) ? null : taskType);
//        map.put("beginRow", from);
//        map.put("pageRowCnt", length);
//        map.put("create_user",user.getUserId());
//        return reportBrowseService.findListByMap(map);
//    }

//    public String list() {
//        return this.getGrid();
//    }

    public String manage() {
        reportTypeList = reportGroupService.findReportTypeList();
        reportGroupList = reportGroupService.findReportGroupList();
        customTaskType=reportQuerySettingService.getSelectValue("32");
        return "manage";
    }

    public String delete() {
        reportBrowseService.delete(ids);
        return null;
    }

//    public String export(){
//        HttpServletResponse response = ServletActionContext.getResponse();
//        ZipCompress com = new CommonZipCompress();
//        ArrayList<File> filesToAdd = new ArrayList<File>();
//        String des="";
//        try{
//            if (selectIds != null && selectIds.length() > 0) {
//                String [] ids = selectIds.split(",");
//                for(String id:ids){
//                    HashMap params=new HashMap();
//                    params.put("id",id);
//                    List<ReportTaskFileBean> instanceList=reportBrowseService.search(params);
//                    if(instanceList!=null&&instanceList.size()>0){
//                        des=instanceList.get(0).getReportDes();
//                        String desName=des+ File.separator+instanceList.get(0).getReportName();
//                        filesToAdd.add(new File(desName));
//                    }
//                }
//
//                // 创建ZIP文件设置压缩方法并添加文件到压缩包中
//                String file=des+ File.separator+"compFiles"+System.currentTimeMillis()+".zip";
//                com.addFilesDeflateComp(file, filesToAdd);
//                response.setContentType("application/octet-stream");
////                        //确保下载的文件名显示中文不出现乱码
//                response.setHeader("Content-Disposition","attachment;filename="+new String("服务报告.zip".getBytes("gb2312"), "ISO8859-1" ));
//
//                ZipInputStream in = new ZipInputStream(new FileInputStream(file));
//                OutputStream zos = response.getOutputStream();
//
//
//                FileInputStream fis = new FileInputStream(file);
//                byte[] buffer = new byte[1024];
//                int r = 0;
//                while ((r = fis.read(buffer)) != -1) {
//                    zos.write(buffer, 0, r);
//                }
//                zos.flush();
//                in.close();
//                zos.close();
//                response.setStatus(response.SC_OK);
//                response.flushBuffer();
//
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return null;
//    }
    public String getKeyWorld() {
        return keyWorld;
    }

    public void setKeyWorld(String keyWorld) {
        this.keyWorld = keyWorld;
    }

    public String getReportType() {
        return reportType;
    }

    public void setReportType(String reportType) {
        this.reportType = reportType;
    }

    public String getReportGroup() {
        return reportGroup;
    }

    public void setReportGroup(String reportGroup) {
        this.reportGroup = reportGroup;
    }

    public String getTaskType() {
        return taskType;
    }

    public void setTaskType(String taskType) {
        this.taskType = taskType;
    }

    public List<ReportTypeBean> getReportTypeList() {
        return reportTypeList;
    }

    public void setReportTypeList(List<ReportTypeBean> reportTypeList) {
        this.reportTypeList = reportTypeList;
    }

    public List<ReportGroupBean> getReportGroupList() {
        return reportGroupList;
    }

    public void setReportGroupList(List<ReportGroupBean> reportGroupList) {
        this.reportGroupList = reportGroupList;
    }

    public List getCustomTaskType() {
        return customTaskType;
    }

    public void setCustomTaskType(List customTaskType) {
        this.customTaskType = customTaskType;
    }

    public String getSelectIds() {
        return selectIds;
    }

    public void setSelectIds(String selectIds) {
        this.selectIds = selectIds;
    }

    public String[] getIds() {
        return ids;
    }

    public void setIds(String[] ids) {
        this.ids = ids;
    }
}

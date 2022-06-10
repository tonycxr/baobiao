package com.sungcor.baobiao.controller;

import com.sungcor.baobiao.entity.Result;
import com.sungcor.baobiao.report.service.*;
import com.sungcor.baobiao.report.bean.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.List;
import com.sungcor.baobiao.report.util.ServiceResult;

import javax.xml.ws.Service;

/**
 * Created by lenovo on 2016/5/23.
 */

@RestController
@RequestMapping("/customgroup")
public class CustomReportGroupController
{

    @Autowired
    private IReportGroupService reportGroupService;

    @GetMapping("/getResultSize")
    public Map getResultSize()
            //获取报表分组的总数量
    {
        List<ReportGroupBean> list = null;
        list = reportGroupService.findReportGroupList();
        Map map = new HashMap();
        map.put("Total",list.size());
        return map;
    }


    @PostMapping("/add")
    public Result add(@RequestBody ReportGroupBean reportGroupBean){
        Map serviceResult = new HashMap();
        if(reportGroupBean.getId()!=0)// 修改
            {
            reportGroupService.updateReportGroup(reportGroupBean);
            return Result.ok("修改成功");
        }else
            {
            reportGroupService.addReportGroup(reportGroupBean);
            return Result.ok("添加成功");
        }
    }

    @PostMapping("/delete")
    public Result delete(@RequestBody Map map){
        String msg = "";
        List<String> idList = (List<String>) map.get("ids");
        String[] ids = idList.toArray(new String[]{});
        List<Map> list = reportGroupService.findModelNumByGroupId(ids);
        String[] new_ids = {};
        List<String> nlist = new ArrayList<String>();
        for(int i=0;i<ids.length;i++){
            boolean flag = true;
            for(Map element:list){
                if(ids[i].equals(element.get("id").toString())){
                    flag =false;
                    break;
                }
            }
            if(flag){
                nlist.add(ids[i]);
            }
        }
        new_ids = nlist.toArray(new_ids);
        if(new_ids.length>0){
            reportGroupService.deleteReportGroupById(new_ids);
            msg = "操作结果,删除成功";
        }
        if(list.size()>0){
            for(int i=0;i<list.size();i++){
                msg = list.get(i).get("name").toString();
                if(i<list.size()-1){
                    msg = msg+"，";
                }
            }
            msg = msg+"分组已被引用！";
        }
        return Result.ok(msg);
    }

    @PostMapping("/showInfo")
    public ReportGroupBean showInfo(@RequestBody Map map){
        Integer id = Integer.parseInt(map.get("groupId").toString());
        ReportGroupBean reportGroupBean = reportGroupService.findReportGroupById(id);
        return reportGroupBean;
    }

}

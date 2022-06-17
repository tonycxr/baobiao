package com.sungcor.baobiao.controller;

import com.sungcor.baobiao.entity.UserInfoBean;
import com.sungcor.baobiao.report.service.*;
import com.sungcor.baobiao.report.bean.*;
import com.sungcor.baobiao.utils.SessionHelper;
import org.apache.commons.lang.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.sungcor.baobiao.report.util.ServiceResult;
/**
 * Created by lenovo on 2016/8/16.
 */

@RestController
@RequestMapping("/customindex")
public class CustomReportIndexController
{

    private IReportCustomIndexService customIndexService;

    private ServiceResult serviceResult;
    private IReportGroupService reportGroupService;
    private ReportCustomIndexBean customIndex;
    private List<ReportTypeBean> reportTypeList;

    private String keyWorld;
    private String[] ids;

    public int getResultSize()
    {
        HashMap<String,String> map = new HashMap<String,String>();
        return customIndexService.queryCusIndexCount(map);
    }

    public List listResults(int from, int length)
    {
        HashMap<String,String> map = new HashMap<String,String>();
        map.put("name","".equals(keyWorld) ? null : keyWorld);
        map.put("beginRow", from+"");
        map.put("pageRowCnt", length+"");
        return customIndexService.queryCusIndex(map);
    }

    public String manage() {
        return "manage";
    }

    public String edit(){
        reportTypeList = reportGroupService.findReportTypeList();
        if(customIndex!=null){
            customIndex = customIndexService.getById(customIndex.getId());
        }
        return "edit";
    }


    @PostMapping("/addOrUpdate")
    public String addOrUpdate(Map<String ,Object> map ){
        UserInfoBean user = (UserInfoBean) map.get("UserInfo");
        if(customIndex.getId()==0){
            customIndex.setCreateUser(user.getUserId());
            customIndexService.addCusIndex(customIndex);
        }else{
            customIndex.setModifyUser(user.getUserId());
            customIndexService.updateCusIndex(customIndex);
        }
        return serviceResult.returnServiceResult();
    }

    public String deleteIndex(){
        List<String> list= customIndexService.deleteCusIndex(ids);
        if(list.size()>0){
            serviceResult.setOperatorResult(false);
            String result = StringUtils.join(list,",");
            serviceResult.setReturnCode("指标"+result+"正在被使用");
        }else{
            serviceResult.setOperatorResult(true);
        }

        return serviceResult.returnServiceResult();
    }

    public List<ReportTypeBean> getReportTypeList()
    {
        return reportTypeList;
    }

    public ReportCustomIndexBean getCustomIndex()
    {
        return customIndex;
    }

    public void setCustomIndex(ReportCustomIndexBean customIndex)
    {
        this.customIndex = customIndex;
    }

    public void setKeyWorld(String keyWorld)
    {
        this.keyWorld = keyWorld;
    }

    public void setIds(String[] ids)
    {
        this.ids = ids;
    }
}

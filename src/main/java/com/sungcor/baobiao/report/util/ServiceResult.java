package com.sungcor.baobiao.report.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.List;
import java.util.Map;

@Service
public class ServiceResult {
    private boolean operatorResult=true;
    private Object data;
    private String returnCode;
    private String returnJsonObj;
    private Map<String, List<String>> filedErrors;
    private ServiceResult serviceResult;

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public Map<String, List<String>> getFiledErrors() {
        return filedErrors;
    }
    public void setFiledErrors(Map<String, List<String>> filedErrors) {
        this.filedErrors = filedErrors;
    }
    public boolean isOperatorResult() {
        return operatorResult;
    }
    public void setOperatorResult(boolean operatorResult) {
        this.operatorResult = operatorResult;
    }
    public String getReturnCode() {
        return returnCode;
    }
    public boolean getOperatorResult(){
        return this.operatorResult;
    }
    public void setReturnCode(String returnCode) {
        this.returnCode = returnCode;
    }

    public String getReturnJsonObj() {
        return returnJsonObj;
    }

    public void setReturnJsonObj(String returnJsonObj) {
        this.returnJsonObj = returnJsonObj;
    }

    public String returnJsonObject(Object response) {

        try {
            String array = JSON.toJSONString(response);
//            log.debug(array.toString());
//            InputStream inputStream = new ByteArrayInputStream(array.toString().getBytes("UTF-8"));
            return array;
        } catch (Exception e) {
            e.printStackTrace();
            return "NONE";
        }

    }

    public String returnServiceResult(){

        return this.returnJsonObject(serviceResult);

    }

}

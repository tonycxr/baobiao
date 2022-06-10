package com.sungcor.baobiao.utils;

import com.sungcor.baobiao.STSMConstant;
import com.sungcor.baobiao.entity.Area;
import com.sungcor.baobiao.service.IRegionService;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Data
public class Util {

    private static String enableLDAP;
    private static String dbType;
    private static String rootpath;
    private static String signinscale;
    private static String signoutscale;
    private static String noticebandid;
    private static String noticeassortment;
    private static String noticetarget;
    private static String appname;
    private static String appid;
    private static String doorserviceaddr;
    private static String complainFlag;
    private static String slmFlag;
    private static String overStockTime;

    /*无锡信息办移植属性*/
    private static String ITSQM_FLAG;
    private static String ITSQM_WS_URL;

    private static String SMS_SEND;//短信发送方式  目前分为接口与短信猫方式
    private static String serviceflag;//多服务商配置
    private static String HTTP_INFO; //HTTP接口配置值
    /**
     * 获取系统配置服务产品ids集合
     */
    private static String PRODUCT_IDS;
    /**
     * 外部工单配置创建人
     */
    private static String ORDER_CREATOR;

    /**
     * 短信猫 短信服务IP，端口
     */
    private static String port;

    private static String host;

    public static String getRootPath(){
        if (rootpath != null && !rootpath.equals("")) {
            while(rootpath.indexOf("%20") != -1) {
                rootpath = rootpath.replace("%20"," ");
            }
        }

        return rootpath;
    }


    public static String getORDER_CREATOR() {
        if(StringUtils.isBlank(ORDER_CREATOR)){
            return STSMConstant.STR_ONE_;//如果为空 则默认传-1
        }
        return ORDER_CREATOR;
    }

    /**
     * 获取异常错误信息
     * @param e
     * @return
     */
    public static String getExceptionMessage(Exception e){
        StringBuffer message = new StringBuffer(STSMConstant.NUM_FIVE_HUNDRED);
        StackTraceElement stackTrace= e.getStackTrace()[STSMConstant.NUM_ZERO];
        if(e instanceof NullPointerException){ //空指针无法通过getMessage获取异常信息，需要手工拼装
            message.append("Exception ： NullPointerException \n");
        }else{
            message.equals(e.getMessage());
        }
        message.append(" ClassName ：".concat(stackTrace.getClassName().concat("\n")) );
        message.append(" MethodName ：".concat(stackTrace.getMethodName().concat("\n")));
        message.append(" LineNumber ："+stackTrace.getLineNumber());
        return message.toString();
    }

    public static byte[] toByte(String data){

        int length = 0;
        String version ="0001";
        byte[] dataByte = new byte[0];
        try {
            dataByte = data.getBytes("GBK");
            length = dataByte.length;
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        byte []dataLen= intToBytes(length); //数据长度
        byte [] versionByte = version.getBytes();
        ByteBuffer returnBuffer=ByteBuffer.allocate(8+dataByte.length);

        returnBuffer.put(versionByte);
        returnBuffer.put(dataLen);
        returnBuffer.put(dataByte);

        return returnBuffer.array();
    }

    public static byte[] intToBytes(int num) {
        byte[] b = new byte[4];
        for (int i = 0; i < 4; i++) {
            b[i] = (byte) (num >>> (24 - i * 8));
        }
        return b;
    }

    public static List<Area> toAreaList(String userId){
        IRegionService regionService = (IRegionService) SpringHelper.getBean("system/regionService");
        List<Area> areaList = new ArrayList<Area>();
        HashMap areaMap = new HashMap();
        areaMap.put("userId",userId);
        areaList = regionService.getAllRegionArea(areaMap);//用户数据域关联的区域
        if(null ==areaList || areaList.size() == 0){
            areaList = new ArrayList<Area>();
            Area area = new Area();
            area.setId(-1);
            areaList.add(0,area);
        }
        return areaList;
    }

    public static String toAreaString(String userId){
        List<Area> areaList = toAreaList(userId);
        String areaIds = "";
        for(int i=0;i<areaList.size();i++){
            areaIds += areaList.get(i).getId();
            if(i<areaList.size()-1){
                areaIds+=",";
            }
        }
        return areaIds;
    }



    public static Object mapToObject(Map<String, Object> map, Class<?> beanClass) throws Exception {
        if (map == null)
            return null;
        Object obj = beanClass.newInstance();
        Field[] fields = obj.getClass().getDeclaredFields();
        for (Field field : fields) {
            int mod = field.getModifiers();
            if(Modifier.isStatic(mod) || Modifier.isFinal(mod)){
                continue;
            }
            field.setAccessible(true);
            field.set(obj, map.get(field.getName()));
        }
        return obj;
    }
}

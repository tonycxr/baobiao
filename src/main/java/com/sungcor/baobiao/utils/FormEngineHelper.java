package com.sungcor.baobiao.utils;

import com.sungcor.baobiao.entity.FormAccessControl;

import javax.servlet.http.HttpServletRequest;

/**
 * User: wangwei
 * Date: 2012-04-28
 * Time: 15:15
 */
public class FormEngineHelper {

    public static String getBusinsCondition(FormAccessControl formControl) {
        StringBuffer sb = new StringBuffer();
        if (null != formControl) {
            if (null != formControl.getBusinessDef1() && !"".equals(formControl.getBusinessDef1())) {
                sb.append(" AND BUSINESSDEF1='" + formControl.getBusinessDef1() + "'");
            }
            if (null != formControl.getBusinessDef2() && !"".equals(formControl.getBusinessDef2())) {
                sb.append(" AND BUSINESSDEF2='" + formControl.getBusinessDef2() + "'");
            }
            if (null != formControl.getBusinessDef3() && !"".equals(formControl.getBusinessDef3())) {
                sb.append(" AND BUSINESSDEF3='" + formControl.getBusinessDef3() + "'");
            }
            if (null != formControl.getBusinessDef4() && !"".equals(formControl.getBusinessDef4())) {
                sb.append(" AND BUSINESSDEF4='" + formControl.getBusinessDef4() + "'");
            }
            if (null != formControl.getBusinessDef5() && !"".equals(formControl.getBusinessDef5())) {
                sb.append(" AND BUSINESSDEF5='" + formControl.getBusinessDef5() + "'");
            }
            if (null != formControl.getBusinessDef6() && !"".equals(formControl.getBusinessDef6())) {
                sb.append(" AND BUSINESSDEF6='" + formControl.getBusinessDef6() + "'");
            }
            if (null != formControl.getBusinessDef7() && !"".equals(formControl.getBusinessDef7())) {
                sb.append(" AND BUSINESSDEF7='" + formControl.getBusinessDef7() + "'");
            }
            if (null != formControl.getBusinessDef8() && !"".equals(formControl.getBusinessDef8())) {
                sb.append(" AND BUSINESSDEF8='" + formControl.getBusinessDef8() + "'");
            }
            if (null != formControl.getBusinessDef9() && !"".equals(formControl.getBusinessDef9())) {
                sb.append(" AND BUSINESSDEF9='" + formControl.getBusinessDef9() + "'");
            }
            if (null != formControl.getBusinessDef10() && !"".equals(formControl.getBusinessDef10())) {
                sb.append(" AND BUSINESSDEF10='" + formControl.getBusinessDef10() + "'");
            }
        }
        return sb.toString();
    }


    public static FormAccessControl getFormAccessControlByRequest(HttpServletRequest request) {
        FormAccessControl formControl = new FormAccessControl();
        if(null != request.getParameter("businessDef1") && !"".equals(request.getParameter("businessDef1"))) {
            formControl.setBusinessDef1(request.getParameter("businessDef1"));
        }else if(null != request.getAttribute("businessDef1")&& !"".equals(request.getAttribute("businessDef1"))){
            formControl.setBusinessDef1(String.valueOf(request.getAttribute("businessDef1")));
        }
        if(null != request.getParameter("businessDef2") && !"".equals(request.getParameter("businessDef2"))) {
            formControl.setBusinessDef2(request.getParameter("businessDef2"));
        }else if(null != request.getAttribute("businessDef2")&& !"".equals(request.getAttribute("businessDef2"))){
            formControl.setBusinessDef2(String.valueOf(request.getAttribute("businessDef2")));
        }
        if(null != request.getParameter("businessDef3") && !"".equals(request.getParameter("businessDef3"))) {
            formControl.setBusinessDef3(request.getParameter("businessDef3"));
        }else if(null != request.getAttribute("businessDef3")&& !"".equals(request.getAttribute("businessDef3"))){
            formControl.setBusinessDef3(String.valueOf(request.getAttribute("businessDef3")));
        }
        if(null != request.getParameter("businessDef4") && !"".equals(request.getParameter("businessDef4"))) {
            formControl.setBusinessDef4(request.getParameter("businessDef4"));
        }else if(null != request.getAttribute("businessDef4")&& !"".equals(request.getAttribute("businessDef4"))){
            formControl.setBusinessDef4(String.valueOf(request.getAttribute("businessDef4")));
        }
        if(null != request.getParameter("businessDef5") && !"".equals(request.getParameter("businessDef5"))) {
            formControl.setBusinessDef5(request.getParameter("businessDef5"));
        }else if(null != request.getAttribute("businessDef5")&& !"".equals(request.getAttribute("businessDef5"))){
            formControl.setBusinessDef5(String.valueOf(request.getAttribute("businessDef5")));
        }
        if(null != request.getParameter("businessDef6") && !"".equals(request.getParameter("businessDef6"))) {
            formControl.setBusinessDef6(request.getParameter("businessDef6"));
        } else if(null != request.getAttribute("businessDef6")&& !"".equals(request.getAttribute("businessDef6"))){
            formControl.setBusinessDef6(String.valueOf(request.getAttribute("businessDef6")));
        }
        if(null != request.getParameter("businessDef7") && !"".equals(request.getParameter("businessDef7"))) {
            formControl.setBusinessDef7(request.getParameter("businessDef7"));
        }else if(null != request.getAttribute("businessDef7")&& !"".equals(request.getAttribute("businessDef7"))){
            formControl.setBusinessDef7(String.valueOf(request.getAttribute("businessDef7")));
        }
        if(null != request.getParameter("businessDef8") && !"".equals(request.getParameter("businessDef8"))) {
            formControl.setBusinessDef8(request.getParameter("businessDef8"));
        }else if(null != request.getAttribute("businessDef8")&& !"".equals(request.getAttribute("businessDef8"))){
            formControl.setBusinessDef1(String.valueOf(request.getAttribute("businessDef8")));
        }
        if(null != request.getParameter("businessDef9") && !"".equals(request.getParameter("businessDef9"))) {
            formControl.setBusinessDef9(request.getParameter("businessDef9"));
        }else if(null != request.getAttribute("businessDef9")&& !"".equals(request.getAttribute("businessDef9"))){
            formControl.setBusinessDef9(String.valueOf(request.getAttribute("businessDef9")));
        }
        if(null != request.getParameter("businessDef10") && !"".equals(request.getParameter("businessDef10"))) {
            formControl.setBusinessDef10(request.getParameter("businessDef10"));
        }else if(null != request.getAttribute("businessDef10")&& !"".equals(request.getAttribute("businessDef10"))){
            formControl.setBusinessDef10(String.valueOf(request.getAttribute("businessDef10")));
        }
        return formControl;
    }
}

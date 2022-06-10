package com.sungcor.baobiao.utils;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Service;

@Service("SpringHelper")
public class SpringHelper implements ApplicationContextAware {

    private static ApplicationContext applicationContext;

    public static Object getBean(String bean){
        //修改此种方式进行对象获取，当使用webservice等方法进行对象获取时 ServletContext 未初始化会抛出异常
        //ApplicationContext ac = WebApplicationContextUtils.getRequiredWebApplicationContext(ServletActionContext.getServletContext());
        Object o = applicationContext.getBean(bean);
        return o;
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }
}

package com.sungcor.baobiao;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.core.io.Resource;
import org.springframework.web.context.support.XmlWebApplicationContext;

import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * STSM 服务的 Spring ApplicationContext，
 * <li>服务的所有bean 都可以通过该类获得</li>
 * <li>可以加载properties 及 保存 properties</li>
 * <li>可以获取由spring 加载管理的 properties 的键值</li>
 * <br>
 * <br>
 * @author wangming
 *
 */
public class STSMApplicationContext implements ApplicationContextAware {

    private static final Log logger = LogFactory.getLog(STSMApplicationContext.class);
//    private static final List<SpringApplicationListener> springApplicationListeners = new CopyOnWriteArrayList<SpringApplicationListener>();
    private static XmlWebApplicationContext applicationContext;

    public STSMApplicationContext() {
        // TODO Auto-generated constructor stub
    }

    public static ApplicationContext getApplicationContext() {
        return applicationContext;
    }

    public static Object getBean(String name) {
        return applicationContext.getBean(name);
    }

    /**
     * 加载 Properties
     * @param locationPattern
     * @return
     */
    public static Properties loadProperties(String locationPattern){
        InputStream inputStream=null;
        Properties properties=null;
        try {
            Resource resource = STSMApplicationContext.applicationContext.getResources(locationPattern)[0];
            inputStream=resource.getInputStream();
            properties = new Properties();
            properties.load(inputStream);
        } catch (IOException e) {
            if(logger.isErrorEnabled()){
                logger.error("Load properties failure!", e);
            }
        } finally {
            if(inputStream!=null){
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return properties;
    }

    /**
     * 保存 Properties
     * @param locationPattern
     * @param properties
     * @return
     */
    public static boolean storeProperties(String locationPattern, Properties  properties){
        BufferedWriter bufferedWriter=null;
        boolean status=false;
        try {
            Resource resource = STSMApplicationContext.applicationContext.getResources(locationPattern)[0];

//            FileWriter fileWriter = new FileWriter(resource.getFile());
//            bufferedWriter=new BufferedWriter(fileWriter);

            FileOutputStream fos = new FileOutputStream(resource.getFile());
            properties.store(fos, locationPattern);   //此处替换写出方式
            status=true;
        } catch (IOException e) {
            if(logger.isErrorEnabled()){
                logger.error("Save properties failure!", e);
            }
            status=false;
        }  finally {
            if(bufferedWriter!=null){
                try {
                    bufferedWriter.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return status;
    }

    public static String getProperty(String key, String def){
        String pro = getProperty(key);
        if(null == pro){
            return def;
        }
        return pro;
    }

    public static String getProperty(String key){
        String annotion = new StringBuilder("${").append(key).append("}").toString();
        try{
            String propertValue = applicationContext.getMessage(key,null,null);  //获取国际化内容
            if(propertValue.equals(key)){ //如不是国际化内容，获取资源文件内容
                propertValue = applicationContext.getBeanFactory().resolveEmbeddedValue(annotion);
            }
            return propertValue;
        }catch (Exception e) {
            return null;
        }
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext)
            throws BeansException {
        if(null == STSMApplicationContext.applicationContext){
            logger.info("STSMApplicationContext is init!");
            STSMApplicationContext.applicationContext = (XmlWebApplicationContext) applicationContext;
//            registerTask();
//            fireApplicationContextInit();
        }
    }

//    /**
//     * 由于Task使用了spring 容器bean所以在这里注册
//     */
//    private void registerTask() {
//        STSMSendTaskManager.registerTask(new DeleteSendMessageTask());
//        SmsSystem.getOneInstance().reload();
//        //STSMSendTaskManager.registerTask(new MailSendTask());
//        //STSMSendTaskManager.registerTask(new SmsSendTask());
//        STSMSendTaskManager.registerTask(new UpdateFTRIndexTask());
//    }
//
//    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//
//    /*
//     *  Spring context 变化监听
//     */
//
//    public static void addSpringApplicationListener(SpringApplicationListener springApplicationListener){
//        springApplicationListeners.add(springApplicationListener);
//    }
//
//    public static void removeSpringApplicationListener(SpringApplicationListener springApplicationListener){
//        springApplicationListeners.remove(springApplicationListener);
//    }
//
//    public static void fireApplicationContextInit() {
//        for(SpringApplicationListener springApplicationListener : springApplicationListeners){
//            springApplicationListener.springApplicationContextInit();
//        }
//    }
//
//    public static void fireApplicationContextDestroyed() {
//        for(SpringApplicationListener springApplicationListener : springApplicationListeners){
//            springApplicationListener.springApplicationContextdDestroyed();
//        }
//    }
}

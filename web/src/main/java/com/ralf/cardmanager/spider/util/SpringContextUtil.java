/**
 * @Project:console
 * @Time:2014-4-4 上午8:21:17
 * @Author:Quan Chen
 * @Description:
 */
package com.ralf.cardmanager.spider.util;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Lazy;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;


/**
 * @author Quan Chen
 * @Todo
 */
@Component
@Lazy(value = false)
@Slf4j
public class SpringContextUtil implements ApplicationContextAware {
    private static ApplicationContext context;

    @Autowired
    Environment env;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext)
            throws BeansException {
        log.info("i am in application context");
        this.context = applicationContext;

    }

    public static <T> Object getBean(String name) {
        try {
            return getBean1(name, null);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;
    }

    public <T> T getBean(String name, Class<T> requiredType) {
        try {
            return getBean1(name, requiredType);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;
    }

    public static <T> T getBean(Class<T> requiredType) {

        try {
            return getBean1(null, requiredType);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;
    }

    public <T> T getBean(String name, Class<T> requiredType, Object... args) {

        return (T) context.getBean(name, args);
    }

    /**
     * @throws Exception
     * @throws BeansException
     * @Description:@param name
     * @Description:@param requiredType
     * @Description:@return
     */
    private static <T> T getBean1(String name, Class<T> requiredType) throws BeansException, Exception {
        if (name == null) {
            return getContext().getBean(requiredType);
        }
        if (requiredType == null) {
            return (T) getContext().getBean(name);
        }
        return getContext().getBean(name, requiredType);

    }

    public static ApplicationContext getContext() throws Exception {
        if (context != null) {
            return context;
        }
        throw new Exception("the spring isn't inited");
    }

    public String getBootProperties(String key) {
        return env.getProperty(key);
    }

}

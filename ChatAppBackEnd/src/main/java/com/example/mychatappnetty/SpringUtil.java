package com.example.mychatappnetty;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

/**
 * @author tan3
 * @ClassName SpringUtil.java
 * @Description Please enter description here
 * @createTime 2020 -  01 - 23 - 16 : 14
 */
public class SpringUtil implements ApplicationContextAware {

    private static ApplicationContext applicationContext;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        if (SpringUtil.applicationContext == null) {
            SpringUtil.applicationContext = applicationContext;
        }
    }

    // Get applicationContext
    public static ApplicationContext getApplicationContext() {
        return applicationContext;
    }

    // Get Bean by name
    public static Object getBean(String name) {
        return getApplicationContext().getBean(name);
    }

    // Get Bean by class
    public static <T> T getBean(Class<T> clazz) {
        return getApplicationContext().getBean(clazz);
    }

    // get bean by name and class
    public static <T> T getBean(String name, Class<T> clazz) {
        return getApplicationContext().getBean(name, clazz);
    }

}


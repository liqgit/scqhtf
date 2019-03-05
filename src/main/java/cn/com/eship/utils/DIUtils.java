package cn.com.eship.utils;

import cn.com.eship.service.OIEHtmlService;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

public class DIUtils implements ApplicationContextAware {
    private ApplicationContext applicationContext;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    public OIEHtmlService getOieHtmlService() {
        return (OIEHtmlService) applicationContext.getBean("oieHtmlService");
    }
}
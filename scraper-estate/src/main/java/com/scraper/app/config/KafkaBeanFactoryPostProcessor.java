package com.scraper.app.config;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.core.Ordered;

public class KafkaBeanFactoryPostProcessor implements BeanFactoryPostProcessor, Ordered{
    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
        System.out.println(beanFactory.toString());
    }

    @Override
    public int getOrder() {
        return 0;
    }
}

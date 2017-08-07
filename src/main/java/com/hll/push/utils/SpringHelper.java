package com.hll.push.utils;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

/**
 * Author: huangll
 * Written on 17/8/4.
 */
@Component
public class SpringHelper implements ApplicationContextAware {

  private static ApplicationContext applicationContext;

  @Override
  public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
    SpringHelper.applicationContext = applicationContext;
  }

  @SuppressWarnings("unchecked")
  public static <T> T getBean(String beanName) {
    return (T) SpringHelper.applicationContext.getBean(beanName);
  }
}

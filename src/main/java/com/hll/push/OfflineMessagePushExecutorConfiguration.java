package com.hll.push;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Author: huangll
 * Written on 17/8/4.
 */
@Configuration
public class OfflineMessagePushExecutorConfiguration {

  @Bean(name = "offlineMessagePushExecutor")
  public ExecutorService threadPoolExecutorFactoryBean() {
    ExecutorService singleThreadExecutor = Executors.newSingleThreadExecutor();
    return singleThreadExecutor;
  }
}
